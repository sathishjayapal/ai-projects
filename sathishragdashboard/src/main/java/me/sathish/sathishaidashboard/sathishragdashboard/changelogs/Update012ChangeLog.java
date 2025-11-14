package me.sathish.sathishaidashboard.sathishragdashboard.changelogs;

import com.mongodb.client.model.Filters;
import io.mongock.api.annotations.BeforeExecution;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackBeforeExecution;
import io.mongock.api.annotations.RollbackExecution;
import java.util.List;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;


@ChangeUnit(
        id = "update-sathishragdashboard-251114-0742",
        order = "012",
        author = "bootify"
)
public class Update012ChangeLog {

    @BeforeExecution
    public void beforeExecution(final MongoTemplate mongoTemplate) {
        final Document docDBDataSchema = mongoTemplate.executeCommand(
                new Document("listCollections", "1")
                    .append("filter", Filters.all("name", "docDBData")))
                    .get("cursor", Document.class)
                    .getList("firstBatch", Document.class).get(0)
                    .get("options", Document.class)
                    .get("validator", Document.class)
                    .get("$jsonSchema", Document.class);
        final Document docDBDataProperties = docDBDataSchema.get("properties", Document.class);
        docDBDataProperties.put("dateCreated", new Document("type", "object")
                .append("required", List.of("dateTime", "offset")));
        docDBDataProperties.put("lastUpdated", new Document("type", "object")
                .append("required", List.of("dateTime", "offset")));
        mongoTemplate.executeCommand(
                new Document("collMod", "docDBData")
                    .append("validator", new Document("$jsonSchema", docDBDataSchema)));
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
