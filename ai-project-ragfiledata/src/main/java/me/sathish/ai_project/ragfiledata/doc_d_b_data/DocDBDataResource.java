package me.sathish.ai_project.ragfiledata.doc_d_b_data;

import jakarta.validation.Valid;
import me.sathish.ai_project.base.file.FileDataService;
import me.sathish.ai_project.base.model.SimpleValue;
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
@RequestMapping(value = "/api/docDBDatas", produces = MediaType.APPLICATION_JSON_VALUE)
public class DocDBDataResource {

    private final DocDBDataService docDBDataService;
    private final DocDBDataAssembler docDBDataAssembler;
    private final PagedResourcesAssembler<DocDBDataDTO> pagedResourcesAssembler;
    private final FileDataService fileDataService;

    public DocDBDataResource(final DocDBDataService docDBDataService,
            final DocDBDataAssembler docDBDataAssembler,
            final PagedResourcesAssembler<DocDBDataDTO> pagedResourcesAssembler,
            final FileDataService fileDataService) {
        this.docDBDataService = docDBDataService;
        this.docDBDataAssembler = docDBDataAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.fileDataService = fileDataService;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<DocDBDataDTO>>> getAllDocDBDatas(
            @RequestParam(name = "filter", required = false) final String filter,
            @SortDefault(sort = "id") @PageableDefault(size = 20) final Pageable pageable) {
        final Page<DocDBDataDTO> docDBDataDTOs = docDBDataService.findAll(filter, pageable);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(docDBDataDTOs, docDBDataAssembler));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<DocDBDataDTO>> getDocDBData(
            @PathVariable(name = "id") final Long id) {
        final DocDBDataDTO docDBDataDTO = docDBDataService.get(id);
        return ResponseEntity.ok(docDBDataAssembler.toModel(docDBDataDTO));
    }

    @PostMapping
    public ResponseEntity<EntityModel<SimpleValue<Long>>> createDocDBData(
            @RequestBody @Valid final DocDBDataDTO docDBDataDTO) {
        final Long createdId = docDBDataService.create(docDBDataDTO);
        return new ResponseEntity<>(docDBDataAssembler.toSimpleModel(createdId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<SimpleValue<Long>>> updateDocDBData(
            @PathVariable(name = "id") final Long id,
            @RequestBody @Valid final DocDBDataDTO docDBDataDTO) {
        docDBDataService.update(id, docDBDataDTO);
        return ResponseEntity.ok(docDBDataAssembler.toSimpleModel(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocDBData(@PathVariable(name = "id") final Long id) {
        docDBDataService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/fileName/{filename}")
    public ResponseEntity<byte[]> downloadFileName(@PathVariable(name = "id") final Long id) {
        final DocDBDataDTO docDBDataDTO = docDBDataService.get(id);
        return fileDataService.provideDownload(docDBDataDTO.getFileName());
    }

}
