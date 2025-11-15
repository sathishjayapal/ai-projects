package me.sathish.ai_project.sathishaiquery.lookup_user_questions;

import jakarta.validation.Valid;
import java.util.Map;
import me.sathish.ai_project.base.model.SimpleValue;
import me.sathish.ai_project.base.security.UserRoles;
import me.sathish.ai_project.base.user_info.UserInfoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/lookupUserQuestionss", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAnyAuthority('" + UserRoles.ADMIN + "', '" + UserRoles.USER_QUESTIONS_ROLE + "', '" + UserRoles.RAG_USER_ROLE + "')")
public class LookupUserQuestionsResource {

    private final LookupUserQuestionsService lookupUserQuestionsService;
    private final LookupUserQuestionsAssembler lookupUserQuestionsAssembler;
    private final PagedResourcesAssembler<LookupUserQuestionsDTO> pagedResourcesAssembler;
    private final UserInfoService userInfoService;

    public LookupUserQuestionsResource(final LookupUserQuestionsService lookupUserQuestionsService,
            final LookupUserQuestionsAssembler lookupUserQuestionsAssembler,
            final PagedResourcesAssembler<LookupUserQuestionsDTO> pagedResourcesAssembler,
            final UserInfoService userInfoService) {
        this.lookupUserQuestionsService = lookupUserQuestionsService;
        this.lookupUserQuestionsAssembler = lookupUserQuestionsAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.userInfoService = userInfoService;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<LookupUserQuestionsDTO>>> getAllLookupUserQuestionss(
            @RequestParam(name = "filter", required = false) final String filter,
            @SortDefault(sort = "id") @PageableDefault(size = 20) final Pageable pageable) {
        final Page<LookupUserQuestionsDTO> lookupUserQuestionsDTOs = lookupUserQuestionsService.findAll(filter, pageable);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(lookupUserQuestionsDTOs, lookupUserQuestionsAssembler));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<LookupUserQuestionsDTO>> getLookupUserQuestions(
            @PathVariable(name = "id") final Long id) {
        final LookupUserQuestionsDTO lookupUserQuestionsDTO = lookupUserQuestionsService.get(id);
        return ResponseEntity.ok(lookupUserQuestionsAssembler.toModel(lookupUserQuestionsDTO));
    }

    @PostMapping
    public ResponseEntity<EntityModel<SimpleValue<Long>>> createLookupUserQuestions(
            @RequestBody @Valid final LookupUserQuestionsDTO lookupUserQuestionsDTO) {
        final Long createdId = lookupUserQuestionsService.create(lookupUserQuestionsDTO);
        return new ResponseEntity<>(lookupUserQuestionsAssembler.toSimpleModel(createdId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<SimpleValue<Long>>> updateLookupUserQuestions(
            @PathVariable(name = "id") final Long id,
            @RequestBody @Valid final LookupUserQuestionsDTO lookupUserQuestionsDTO) {
        lookupUserQuestionsService.update(id, lookupUserQuestionsDTO);
        return ResponseEntity.ok(lookupUserQuestionsAssembler.toSimpleModel(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLookupUserQuestions(
            @PathVariable(name = "id") final Long id) {
        lookupUserQuestionsService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/lookupusernameValues")
    public ResponseEntity<Map<Long, String>> getLookupusernameValues() {
        return ResponseEntity.ok(userInfoService.getUserInfoValues());
    }

}
