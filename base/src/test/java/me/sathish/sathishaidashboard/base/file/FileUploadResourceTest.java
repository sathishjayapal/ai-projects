package me.sathish.sathishaidashboard.base.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import me.sathish.sathishaidashboard.base.config.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;


public class FileUploadResourceTest extends BaseIT {

    @Test
    public void fileUpload_success() throws Exception {
        final FileData result = RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.MULTIPART)
                    .multiPart(testFile.getFile())
                .when()
                    .post("/fileUpload")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .extract()
                    .as(FileData.class);
        final File uploadFile = new File(FileDataService.UPLOAD_DIRECTORY + "/" + result.getUid() + "/" + result.getFileName());
        assertTrue(uploadFile.exists());
        assertEquals(testFile.getContentAsString(StandardCharsets.UTF_8), Files.readString(uploadFile.toPath()));
    }

}
