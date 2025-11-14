package me.sathish.ai_project.sathishaiquery.lookup_user_questions;

import me.sathish.ai_project.base.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class LookupUserQuestionsService {

    private final LookupUserQuestionsRepository lookupUserQuestionsRepository;

    public LookupUserQuestionsService(
            final LookupUserQuestionsRepository lookupUserQuestionsRepository) {
        this.lookupUserQuestionsRepository = lookupUserQuestionsRepository;
    }

    public Page<LookupUserQuestionsDTO> findAll(final String filter, final Pageable pageable) {
        Page<LookupUserQuestions> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = lookupUserQuestionsRepository.findAllById(longFilter, pageable);
        } else {
            page = lookupUserQuestionsRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent()
                .stream()
                .map(lookupUserQuestions -> mapToDTO(lookupUserQuestions, new LookupUserQuestionsDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }

    public LookupUserQuestionsDTO get(final Long id) {
        return lookupUserQuestionsRepository.findById(id)
                .map(lookupUserQuestions -> mapToDTO(lookupUserQuestions, new LookupUserQuestionsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LookupUserQuestionsDTO lookupUserQuestionsDTO) {
        final LookupUserQuestions lookupUserQuestions = new LookupUserQuestions();
        mapToEntity(lookupUserQuestionsDTO, lookupUserQuestions);
        return lookupUserQuestionsRepository.save(lookupUserQuestions).getId();
    }

    public void update(final Long id, final LookupUserQuestionsDTO lookupUserQuestionsDTO) {
        final LookupUserQuestions lookupUserQuestions = lookupUserQuestionsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(lookupUserQuestionsDTO, lookupUserQuestions);
        lookupUserQuestionsRepository.save(lookupUserQuestions);
    }

    public void delete(final Long id) {
        final LookupUserQuestions lookupUserQuestions = lookupUserQuestionsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        lookupUserQuestionsRepository.delete(lookupUserQuestions);
    }

    private LookupUserQuestionsDTO mapToDTO(final LookupUserQuestions lookupUserQuestions,
            final LookupUserQuestionsDTO lookupUserQuestionsDTO) {
        lookupUserQuestionsDTO.setId(lookupUserQuestions.getId());
        lookupUserQuestionsDTO.setQuestionAsked(lookupUserQuestions.getQuestionAsked());
        return lookupUserQuestionsDTO;
    }

    private LookupUserQuestions mapToEntity(final LookupUserQuestionsDTO lookupUserQuestionsDTO,
            final LookupUserQuestions lookupUserQuestions) {
        lookupUserQuestions.setQuestionAsked(lookupUserQuestionsDTO.getQuestionAsked());
        return lookupUserQuestions;
    }

}
