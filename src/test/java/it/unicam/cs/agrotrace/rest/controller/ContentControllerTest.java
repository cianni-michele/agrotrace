package it.unicam.cs.agrotrace.rest.controller;

import it.unicam.cs.agrotrace.config.TestSecurityConfig;
import it.unicam.cs.agrotrace.controller.ContentController;
import it.unicam.cs.agrotrace.exception.ContentNotFoundException;
import it.unicam.cs.agrotrace.security.context.support.WithCuratorUser;
import it.unicam.cs.agrotrace.service.ContentService;
import it.unicam.cs.agrotrace.service.VerificationService;
import it.unicam.cs.agrotrace.shared.model.content.Content;
import it.unicam.cs.agrotrace.shared.model.content.ValidationStatus;
import it.unicam.cs.agrotrace.util.mapper.user.author.AuthorMapper;
import it.unicam.cs.agrotrace.util.mapper.content.ContentMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static it.unicam.cs.agrotrace.shared.model.content.ValidationStatus.*;
import static it.unicam.cs.agrotrace.shared.model.content.ValidationStatus.PENDING;
import static it.unicam.cs.agrotrace.util.AuthorTestUtils.*;
import static it.unicam.cs.agrotrace.util.ContentTestUtils.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContentController.class)
@ExtendWith(SpringExtension.class)
@Import({
        TestSecurityConfig.class,
        ContentMapper.class,
        AuthorMapper.class
})
class ContentControllerTest {

    private static final Content TEST_PROCESS_CONTENT = buildTestProcessContent(UUID.randomUUID(), TEST_TRANSFORMER_ID, PENDING);
    private static final Content TEST_PRODUCT_MODEL = buildTestProductModel(UUID.randomUUID(), TEST_PRODUCER_ID, REJECTED);
    private static final Content TEST_BUNDLE_CONTENT = buildTestBundleContent(UUID.randomUUID(), TEST_DISTRIBUTOR_ID, APPROVED);
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private VerificationService verificationService;

    @MockitoBean
    private ContentService contentService;

    @Nested
    class ValidateContentTests {

        @Test
        @WithMockUser(roles = "USER")
        void shouldReturnStatusForbidden_whenUserIsNotCurator() throws Exception {
            var request = put("/api/content/" + TEST_CONTENT_ID + "/validate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                                 { "comments": "Need to be corrected", "validationCode": "NEED_CORRECTION" }
                            """);

            mvc.perform(request).andExpect(status().isForbidden());

            verify(contentService, never()).save(any(Content.class));
            verify(verificationService, never()).createVerification(any(Long.class), any(UUID.class), anyString());
        }

        @Test
        @WithCuratorUser
        void shouldReturnStatusNotFound_whenContentDoesNotExist() throws Exception {
            UUID contentId = TEST_CONTENT_ID;

            when(contentService.findById(contentId)).thenThrow(new ContentNotFoundException(contentId));

            var request = put("/api/content/" + contentId + "/validate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                                 { "comments": "Need to be corrected", "validationCode": "NEED_CORRECTION" }
                            """);

            mvc.perform(request).andExpect(status().isNotFound());

            verify(contentService, never()).save(any(Content.class));
            verify(verificationService, never()).createVerification(any(Long.class), any(UUID.class), anyString());
        }

        @Test
        @WithCuratorUser
        void shouldReturnStatusNoContent_whenApproved() throws Exception {
            UUID contentId = TEST_CONTENT_ID;
            Content content = mock(Content.class);

            when(contentService.findById(contentId)).thenReturn(content);
            when(content.getValidationStatus()).thenReturn(PENDING);
            when(contentService.save(content)).thenReturn(content);

            var request = put("/api/content/" + contentId + "/validate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                                 { "comments": "Approved content", "validationCode": "APPROVED" }
                            """);

            mvc.perform(request).andExpect(status().isNoContent());

            verify(contentService).save(content);
            verify(verificationService).createVerification(any(Long.class), any(UUID.class), anyString());
        }


        @Test
        @WithCuratorUser
        void shouldReturnStatusNoContent_whenRejected() throws Exception {
            UUID contentId = TEST_CONTENT_ID;
            Content content = mock(Content.class);

            when(contentService.findById(contentId)).thenReturn(content);
            when(content.getValidationStatus()).thenReturn(PENDING);

            var request = put("/api/content/" + contentId + "/validate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                                 { "comments": "Rejected content", "validationCode": "REJECTED" }
                            """);

            mvc.perform(request).andExpect(status().isNoContent());

            verify(contentService).save(content);
            verify(verificationService).createVerification(any(Long.class), any(UUID.class), anyString());
        }
    }


    @Nested
    class GetContentTests {

        @Test
        @WithMockUser
        void getContent_shouldReturnStatusNotFound() throws Exception {
            UUID contentId = TEST_CONTENT_ID;

            when(contentService.findById(contentId)).thenThrow(new ContentNotFoundException(contentId));

            mvc.perform(get("/api/content/" + contentId))
                    .andExpect(status().isNotFound());
        }

        @MethodSource("provideContent")
        @ParameterizedTest
        @WithMockUser
        void getContent_shouldReturnCorrectView(Content content) throws Exception {
            UUID contentId = TEST_CONTENT_ID;

            when(contentService.findById(contentId)).thenReturn(content);

            mvc.perform(get("/api/content/" + contentId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(content.getId().toString())))
                    .andExpect(jsonPath("$.authorId", is(content.getAuthor().getId().intValue())))
                    .andExpect(jsonPath("$.title", is(content.getTitle())))
                    .andExpect(jsonPath("$.description", is(content.getDescription())))
                    .andExpect(jsonPath("$.validationStatus", is(content.getValidationStatus().toString())));
        }

        private static Stream<Content> provideContent() {
            return Stream.of(
                    TEST_PROCESS_CONTENT,
                    TEST_PRODUCT_MODEL,
                    TEST_BUNDLE_CONTENT
            );
        }
    }

    @Nested
    class GetContentsTests {

        @Test
        @WithMockUser
        void getContents_shouldReturnStatusNoContent_whenNoContentsFound() throws Exception {
            when(contentService.findAll(null)).thenReturn(Collections.emptyList());

            mvc.perform(get("/api/content")).andExpect(status().isNoContent());
        }

        @Test
        @WithMockUser
        void getContents_shouldReturnAllContents_whenNoStatusFilter() throws Exception {
            List<Content> contents = List.of(
                    TEST_PROCESS_CONTENT,
                    TEST_PRODUCT_MODEL,
                    TEST_BUNDLE_CONTENT
            );

            when(contentService.findAll(null)).thenReturn(contents);

            mvc.perform(get("/api/content"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(contents.size())))
                    .andExpect(jsonPath("$[0].id", is(contents.get(0).getId().toString())))
                    .andExpect(jsonPath("$[1].id", is(contents.get(1).getId().toString())))
                    .andExpect(jsonPath("$[2].id", is(contents.get(2).getId().toString())));
        }

        @MethodSource("provideStatusAndExpectedContent")
        @ParameterizedTest
        @WithMockUser
        void getContents_shouldReturnContents_whenFilteredByStatus(ValidationStatus status, Content expectedContent) throws Exception {
            when(contentService.findAll(status)).thenReturn(List.of(expectedContent));

            var request = get("/api/content").param("status", status.toString());

            mvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].id", is(expectedContent.getId().toString())));
        }

        private static Stream<Arguments> provideStatusAndExpectedContent() {
            return Stream.of(
                    Arguments.of(PENDING, TEST_PROCESS_CONTENT),
                    Arguments.of(REJECTED, TEST_PRODUCT_MODEL),
                    Arguments.of(APPROVED, TEST_BUNDLE_CONTENT)
            );
        }
    }
}

