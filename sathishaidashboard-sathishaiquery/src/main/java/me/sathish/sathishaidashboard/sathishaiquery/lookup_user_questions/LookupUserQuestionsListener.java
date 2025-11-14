package me.sathish.sathishaidashboard.sathishaiquery.lookup_user_questions;

import me.sathish.sathishaidashboard.base.config.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class LookupUserQuestionsListener extends AbstractMongoEventListener<LookupUserQuestions> {

    private final PrimarySequenceService primarySequenceService;

    public LookupUserQuestionsListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<LookupUserQuestions> event) {
        if (event.getSource().getId() == null) {
            event.getSource().setId(primarySequenceService.getNextValue());
        }
    }

}
