package me.sathish.sathishaidashboard.base.file;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface FileContentRepository extends MongoRepository<FileContent, String> {
}
