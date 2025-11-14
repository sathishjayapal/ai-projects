package me.sathish.ai_project.ragfiledata.doc_d_b_data;

import me.sathish.ai_project.base.file.FileDataService;
import me.sathish.ai_project.base.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class DocDBDataService {

    private final DocDBDataRepository docDBDataRepository;
    private final FileDataService fileDataService;

    public DocDBDataService(final DocDBDataRepository docDBDataRepository,
            final FileDataService fileDataService) {
        this.docDBDataRepository = docDBDataRepository;
        this.fileDataService = fileDataService;
    }

    public Page<DocDBDataDTO> findAll(final String filter, final Pageable pageable) {
        Page<DocDBData> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = docDBDataRepository.findAllById(longFilter, pageable);
        } else {
            page = docDBDataRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent()
                .stream()
                .map(docDBData -> mapToDTO(docDBData, new DocDBDataDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }

    public DocDBDataDTO get(final Long id) {
        return docDBDataRepository.findById(id)
                .map(docDBData -> mapToDTO(docDBData, new DocDBDataDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DocDBDataDTO docDBDataDTO) {
        final DocDBData docDBData = new DocDBData();
        mapToEntity(docDBDataDTO, docDBData);
        fileDataService.persistUpload(docDBData.getFileName());
        return docDBDataRepository.save(docDBData).getId();
    }

    public void update(final Long id, final DocDBDataDTO docDBDataDTO) {
        final DocDBData docDBData = docDBDataRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        fileDataService.handleUpdate(docDBData.getFileName(), docDBDataDTO.getFileName());
        mapToEntity(docDBDataDTO, docDBData);
        docDBDataRepository.save(docDBData);
    }

    public void delete(final Long id) {
        final DocDBData docDBData = docDBDataRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        fileDataService.removeFileContent(docDBData.getFileName());
        docDBDataRepository.delete(docDBData);
    }

    private DocDBDataDTO mapToDTO(final DocDBData docDBData, final DocDBDataDTO docDBDataDTO) {
        docDBDataDTO.setId(docDBData.getId());
        docDBDataDTO.setName(docDBData.getName());
        docDBDataDTO.setFileName(docDBData.getFileName());
        return docDBDataDTO;
    }

    private DocDBData mapToEntity(final DocDBDataDTO docDBDataDTO, final DocDBData docDBData) {
        docDBData.setName(docDBDataDTO.getName());
        docDBData.setFileName(docDBDataDTO.getFileName());
        return docDBData;
    }

    public boolean nameExists(final String name) {
        return docDBDataRepository.existsByNameIgnoreCase(name);
    }

}
