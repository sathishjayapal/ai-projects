package me.sathish.ai_project.base.user_info;

import me.sathish.ai_project.base.config.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class UserInfoListener extends AbstractMongoEventListener<UserInfo> {

    private final PrimarySequenceService primarySequenceService;

    public UserInfoListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<UserInfo> event) {
        if (event.getSource().getUserid() == null) {
            event.getSource().setUserid(primarySequenceService.getNextValue());
        }
    }

}
