package me.sathish.sathishaidashboard.sathishragdashboard.doc_d_b_data;

import me.sathish.sathishaidashboard.base.config.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class DocDBDataListener extends AbstractMongoEventListener<DocDBData> {

    private final PrimarySequenceService primarySequenceService;

    public DocDBDataListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<DocDBData> event) {
        if (event.getSource().getId() == null) {
            event.getSource().setId(primarySequenceService.getNextValue());
        }
    }

}
