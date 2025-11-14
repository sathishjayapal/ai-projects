package me.sathish.ai_project.sathishaiquery.lookup_user_questions;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface LookupUserQuestionsRepository extends MongoRepository<LookupUserQuestions, Long> {

    Page<LookupUserQuestions> findAllById(Long id, Pageable pageable);

}
