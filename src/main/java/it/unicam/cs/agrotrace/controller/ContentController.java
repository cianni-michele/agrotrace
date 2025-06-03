package it.unicam.cs.agrotrace.controller;

import it.unicam.cs.agrotrace.exception.UnauthorizedOperationException;
import it.unicam.cs.agrotrace.rest.request.ValidationRequest;
import it.unicam.cs.agrotrace.service.VerificationService;
import it.unicam.cs.agrotrace.shared.model.content.Content;
import it.unicam.cs.agrotrace.shared.model.content.ValidationStatus;
import it.unicam.cs.agrotrace.shared.model.user.admin.Curator;
import it.unicam.cs.agrotrace.service.ContentService;
import it.unicam.cs.agrotrace.rest.view.ContentView;
import it.unicam.cs.agrotrace.util.mapper.content.ContentMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/content")
public class ContentController {

    private final ContentService contentService;

    private final VerificationService verificationService;

    private final ContentMapper contentMapper;

    public ContentController(ContentService contentService, VerificationService verificationService, ContentMapper contentMapper) {
        this.contentService = contentService;
        this.verificationService = verificationService;
        this.contentMapper = contentMapper;
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<ContentView> getContent(@PathVariable UUID contentId) {
        Content model = contentService.findById(contentId);

        ContentView view = contentMapper.viewFromModel(model);

        return ResponseEntity.ok(view);
    }

    @GetMapping
    public ResponseEntity<List<ContentView>> getContents(@RequestParam(required = false, name = "status") String statusFilter) {
        ValidationStatus status = Optional.ofNullable(statusFilter)
                .map(ValidationStatus::valueOf)
                .orElse(null);

        List<ContentView> contentViews = contentService.findAll(status).stream()
                .map(contentMapper::viewFromModel)
                .toList();

        return contentViews.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(contentViews);
    }

    @PreAuthorize("hasRole('CURATOR')")
    @PutMapping("/{contentId}/validate")
    public ResponseEntity<Void> validateContent(Authentication auth,
                                                @PathVariable UUID contentId,
                                                @Valid @RequestBody ValidationRequest request) {

        if (!(auth.getPrincipal() instanceof Curator curator)) {
            throw new UnauthorizedOperationException();
        }

        Content content = contentService.findById(contentId);
        ValidationStatus status = ValidationStatus.valueOf(request.getValidationCode());
        curator.validate(content, status);
        contentService.save(content);
        verificationService.createVerification(curator.getId(), contentId, request.getComments());

        return ResponseEntity.noContent().build();
    }
}
