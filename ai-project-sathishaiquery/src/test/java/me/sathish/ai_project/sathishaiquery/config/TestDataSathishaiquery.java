package me.sathish.ai_project.sathishaiquery.config;

import me.sathish.ai_project.base.user_info.UserInfoRepository;
import me.sathish.ai_project.sathishaiquery.lookup_user_questions.LookupUserQuestions;
import me.sathish.ai_project.sathishaiquery.lookup_user_questions.LookupUserQuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class TestDataSathishaiquery {

    @Autowired
    public LookupUserQuestionsRepository lookupUserQuestionsRepository;

    @Autowired
    public UserInfoRepository userInfoRepository;

    public void clearAll() {
        lookupUserQuestionsRepository.deleteAll();
    }

    public void lookupUserQuestions() {
        final LookupUserQuestions lookupUserQuestions = new LookupUserQuestions();
        lookupUserQuestions.setId((long)1100);
        lookupUserQuestions.setQuestionAsked("Quis nostrud exerci.");
        lookupUserQuestionsRepository.save(lookupUserQuestions);
        final LookupUserQuestions lookupUserQuestions1 = new LookupUserQuestions();
        lookupUserQuestions1.setId((long)1101);
        lookupUserQuestions1.setQuestionAsked("Commodo consequat.");
        lookupUserQuestionsRepository.save(lookupUserQuestions1);
    }

}
