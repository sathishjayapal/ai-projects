package me.sathish.ai_project.base.file;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface FileContentRepository extends MongoRepository<FileContent, String> {
}
