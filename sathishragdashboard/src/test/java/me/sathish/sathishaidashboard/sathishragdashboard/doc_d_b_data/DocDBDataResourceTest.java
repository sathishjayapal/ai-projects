package me.sathish.sathishaidashboard.sathishragdashboard.doc_d_b_data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import me.sathish.sathishaidashboard.base.config.BaseIT;
import me.sathish.sathishaidashboard.base.file.FileContent;
import me.sathish.sathishaidashboard.sathishragdashboard.config.TestDataSathishragdashboard;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;


public class DocDBDataResourceTest extends BaseIT {

    @Autowired
    public TestDataSathishragdashboard testDataSathishragdashboard;

    @Autowired
    public DocDBDataRepository docDBDataRepository;

    @BeforeEach
    public void beforeEach() {
        testDataSathishragdashboard.clearAll();
        testData.clearAll();
    }

    @Test
    void getAllDocDBDatas_success() {
        testDataSathishragdashboard.docDBData();

        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/docDBDatas")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(2))
                    .body("_embedded.docDBDataDTOList.get(0).id", Matchers.equalTo(1000))
                    .body("_links.self.href", Matchers.endsWith("/api/docDBDatas?page=0&size=20&sort=id,asc"));
    }

    @Test
    void getAllDocDBDatas_filtered() {
        testDataSathishragdashboard.docDBData();

        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/docDBDatas?filter=1001")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(1))
                    .body("_embedded.docDBDataDTOList.get(0).id", Matchers.equalTo(1001));
    }

    @Test
    void getDocDBData_success() {
        testDataSathishragdashboard.docDBData();

        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/docDBDatas/1000")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("name", Matchers.equalTo("Zed diam voluptua."))
                    .body("_links.self.href", Matchers.endsWith("/api/docDBDatas/1000"));
    }

    @Test
    void getDocDBData_notFound() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/docDBDatas/1666")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createDocDBData_success() throws Exception {
        prepareUpload("f4063e27-5d50-3f65-abf1-b02d926f19a4", "testFile.pdf");
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/docDBDataDTORequest.json"))
                .when()
                    .post("/api/docDBDatas")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        final FileContent fileContent = fileContentRepository.findById("f4063e27-5d50-3f65-abf1-b02d926f19a4")
                .orElseThrow();
        assertEquals("test file content\n", new String(fileContent.getContent()));
        assertEquals(1, docDBDataRepository.count());
    }

    @Test
    void createDocDBData_missingField() {
        prepareUpload("f4063e27-5d50-3f65-abf1-b02d926f19a4", "testFile.pdf");
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/docDBDataDTORequest_missingField.json"))
                .when()
                    .post("/api/docDBDatas")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("name"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    void updateDocDBData_success() {
        testDataSathishragdashboard.docDBData();

        prepareUpload("f4063e27-5d50-3f65-abf1-b02d926f19a4", "testFile.pdf");
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/docDBDataDTORequest.json"))
                .when()
                    .put("/api/docDBDatas/1000")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("_links.self.href", Matchers.endsWith("/api/docDBDatas/1000"));
        assertEquals("Duis autem vel.", docDBDataRepository.findById(((long)1000)).orElseThrow().getName());
        assertEquals(2, docDBDataRepository.count());
    }

    @Test
    void deleteDocDBData_success() {
        testDataSathishragdashboard.docDBData();

        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/docDBDatas/1000")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, docDBDataRepository.count());
    }

}
