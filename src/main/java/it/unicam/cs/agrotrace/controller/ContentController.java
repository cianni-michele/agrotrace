package it.unicam.cs.agrotrace.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unicam.cs.agrotrace.exception.UnauthorizedOperationException;
import it.unicam.cs.agrotrace.rest.request.ValidationRequest;
import it.unicam.cs.agrotrace.service.VerificationService;
import it.unicam.cs.agrotrace.service.notification.NotificationService;
import it.unicam.cs.agrotrace.shared.model.content.Content;
import it.unicam.cs.agrotrace.shared.model.content.ValidationStatus;
import it.unicam.cs.agrotrace.shared.model.user.admin.Curator;
import it.unicam.cs.agrotrace.service.ContentService;
import it.unicam.cs.agrotrace.rest.view.ContentView;
import it.unicam.cs.agrotrace.shared.model.verification.Verification;
import it.unicam.cs.agrotrace.util.mapper.content.ContentMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(
        name = "Content Management",
        description = "API related to content management, including retrieval and validation."
)
@RestController
@RequestMapping("/api/content")
public class ContentController {

    private final ContentService contentService;

    private final VerificationService verificationService;

    private final NotificationService notificationService;

    private final ContentMapper contentMapper;

    public ContentController(ContentService contentService,
                             VerificationService verificationService,
                             NotificationService notificationService,
                             ContentMapper contentMapper) {
        this.contentService = contentService;
        this.verificationService = verificationService;
        this.notificationService = notificationService;
        this.contentMapper = contentMapper;
    }

    @Operation(
            summary = "Get content by ID",
            description = "Retrieves a specific content by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Content retrieved successfully",
                    content = {@io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ContentView.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Content not found",
                    content = {@io.swagger.v3.oas.annotations.media.
                            Content(schema = @Schema(hidden = true)
                    )}
            )}
    )
    @GetMapping("/{contentId}")
    public ResponseEntity<ContentView> getContent(
            @Parameter(
                    description = "The ID of the content to retrieve",
                    required = true,
                    schema = @Schema(type = "string", format = "uuid"),
                    example = "123e4567-e89b-12d3-a456-426614174000"
            )
            @PathVariable UUID contentId
    ) {
        Content model = contentService.findById(contentId);

        ContentView view = contentMapper.viewFromModel(model);

        return ResponseEntity.ok(view);
    }

    @Operation(
            summary = "Get all contents",
            description = "Retrieves all contents, optionally filtered by validation status."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Contents retrieved successfully",
                    content = {@io.swagger.v3.oas.annotations.media.
                            Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ContentView.class))
                    )}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No contents found",
                    content = {@io.swagger.v3.oas.annotations.media.
                            Content(schema = @Schema(hidden = true)
                    )}
            )
    })
    @GetMapping
    public ResponseEntity<List<ContentView>> getContents(
            @Parameter(
                    description = "Optional filter contents by validation status",
                    schema = @Schema(implementation = ValidationStatus.class),
                    examples = {
                            @ExampleObject(
                                    name = "Pending filter",
                                    value = "PENDING",
                                    description = "Filter contents that are pending validation"
                            ),
                            @ExampleObject(
                                    name = "Rejected filter",
                                    value = "REJECTED",
                                    description = "Filter contents that have been rejected"
                            ),
                            @ExampleObject(
                                    name = "Approved filter",
                                    value = "APPROVED",
                                    description = "Filter contents that have been approved"
                            )
                    }
            )
            @RequestParam(required = false, name = "status") String statusFilter
    ) {
        ValidationStatus status = ValidationStatus.fromString(statusFilter);

        List<ContentView> contentViews = contentService.findAll(status).stream()
                .map(contentMapper::viewFromModel)
                .toList();

        return contentViews.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(contentViews);
    }

    @Operation(
            summary = "Validate content",
            description = "Allows a curator to validate content with a specific validation status."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Content validated successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not authenticated",
                    content = {@io.swagger.v3.oas.annotations.media.
                            Content(schema = @Schema(hidden = true)
                    )}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Unauthorized operation, only curators can validate content",
                    content = {@io.swagger.v3.oas.annotations.media.
                            Content(schema = @Schema(hidden = true)
                    )}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Content not found",
                    content = {@io.swagger.v3.oas.annotations.media.
                            Content(schema = @Schema(hidden = true)
                    )}
            )
    })
    @PreAuthorize("hasRole('CURATOR')")
    @PutMapping("/{contentId}/validate")
    public ResponseEntity<Void> validateContent(
            Authentication auth,
            @Parameter(
                    description = "The ID of the content to validate",
                    required = true,
                    schema = @Schema(type = "string", format = "uuid"),
                    example = "123e4567-e89b-12d3-a456-426614174000"
            )
            @PathVariable UUID contentId,
            @Parameter(
                    description = "Validation request containing the validation code and optional comments",
                    required = true,
                    schema = @Schema(implementation = ValidationRequest.class),
                    example = """
                            {
                                "validationCode": "APPROVED",
                                "comments": "Content is valid and approved."
                            }
                            """
            )
            @Valid @RequestBody ValidationRequest request
    ) {

        if (!(auth.getPrincipal() instanceof Curator curator)) {
            throw new UnauthorizedOperationException();
        }

        Content content = contentService.findById(contentId);

        ValidationStatus newStatus = ValidationStatus.valueOf(request.getValidationCode());

        curator.validate(content, newStatus);

        contentService.save(content);

        Verification verification = verificationService.createVerification(curator.getId(), contentId, request.getComments());

        notificationService.notifyAuthor(verification);

        return ResponseEntity.noContent().build();
    }
}
