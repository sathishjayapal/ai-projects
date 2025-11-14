package me.sathish.sathishaidashboard.sathishaiquery.changelogs;

import io.mongock.api.annotations.BeforeExecution;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackBeforeExecution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.schema.JsonSchemaProperty;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema;
import org.springframework.data.mongodb.core.validation.Validator;


@ChangeUnit(
        id = "update-sathishaiquery-251114-0741",
        order = "011",
        author = "bootify"
)
public class Update011ChangeLog {

    @BeforeExecution
    public void beforeExecution(final MongoTemplate mongoTemplate) {
        mongoTemplate.createCollection("lookupUserQuestions", CollectionOptions.empty()
                .validator(Validator.schema(MongoJsonSchema.builder()
                .required("dateCreated", "lastUpdated")
                .properties(
                        JsonSchemaProperty.int64("id"),
                        JsonSchemaProperty.string("questionAsked"),
                        JsonSchemaProperty.object("dateCreated")
                                .required("dateTime", "offset"),
                        JsonSchemaProperty.object("lastUpdated")
                                .required("dateTime", "offset")).build())));
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
