package me.sathish.ai_project.base.changelogs;

import com.mongodb.client.model.Filters;
import io.mongock.api.annotations.BeforeExecution;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackBeforeExecution;
import io.mongock.api.annotations.RollbackExecution;
import java.util.List;
import org.bson.Document;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.schema.JsonSchemaProperty;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema;
import org.springframework.data.mongodb.core.validation.Validator;


@ChangeUnit(id = "update-251114-0740", order = "010", author = "bootify")
public class Update010ChangeLog {

    @BeforeExecution
    public void beforeExecution(final MongoTemplate mongoTemplate) {
        mongoTemplate.createCollection("userInfo", CollectionOptions.empty()
                .validator(Validator.schema(MongoJsonSchema.builder()
                .required("username", "password", "dateCreated", "lastUpdated")
                .properties(
                        JsonSchemaProperty.int64("userid"),
                        JsonSchemaProperty.string("username"),
                        JsonSchemaProperty.string("password"),
                        JsonSchemaProperty.object("dateCreated")
                                .required("dateTime", "offset"),
                        JsonSchemaProperty.object("lastUpdated")
                                .required("dateTime", "offset")).build())));
        final Document fileContentSchema = mongoTemplate.executeCommand(
                new Document("listCollections", "1")
                    .append("filter", Filters.all("name", "fileContent")))
                    .get("cursor", Document.class)
                    .getList("firstBatch", Document.class).get(0)
                    .get("options", Document.class)
                    .get("validator", Document.class)
                    .get("$jsonSchema", Document.class);
        final Document fileContentProperties = fileContentSchema.get("properties", Document.class);
        fileContentProperties.put("dateCreated", new Document("type", "object")
                .append("required", List.of("dateTime", "offset")));
        fileContentProperties.put("lastUpdated", new Document("type", "object")
                .append("required", List.of("dateTime", "offset")));
        mongoTemplate.executeCommand(
                new Document("collMod", "fileContent")
                    .append("validator", new Document("$jsonSchema", fileContentSchema)));
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
