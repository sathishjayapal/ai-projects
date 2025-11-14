package me.sathish.sathishaidashboard.base.changelogs;

import com.mongodb.client.model.IndexOptions;
import io.mongock.api.annotations.BeforeExecution;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackBeforeExecution;
import io.mongock.api.annotations.RollbackExecution;
import org.bson.Document;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.schema.JsonSchemaProperty;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema;
import org.springframework.data.mongodb.core.validation.Validator;


@ChangeUnit(id = "init-collections", order = "001", author = "bootify")
public class InitCollectionsChangeLog {

    @BeforeExecution
    public void beforeExecution(final MongoTemplate mongoTemplate) {
        mongoTemplate.createCollection("docDBData", CollectionOptions.empty()
                .validator(Validator.schema(MongoJsonSchema.builder()
                .required("name")
                .properties(
                        JsonSchemaProperty.int64("id"),
                        JsonSchemaProperty.string("name")).build())));
        mongoTemplate.createCollection("fileContent", CollectionOptions.empty()
                .validator(Validator.schema(MongoJsonSchema.builder()
                .required("content")
                .properties(
                        JsonSchemaProperty.string("uid")).build())));
        mongoTemplate.getCollection("docDBData")
                .createIndex(new Document("name", 1), new IndexOptions().name("name").unique(true));
    }

    @RollbackBeforeExecution
    public void rollbackBeforeExecution(final MongoTemplate mongoTemplate) {
    }

    @Execution
    public void execution(final MongoTemplate mongoTemplate) {
    }

    @RollbackExecution
    public void rollbackExecution(final MongoTemplate mongoTemplate) {
    }

}
