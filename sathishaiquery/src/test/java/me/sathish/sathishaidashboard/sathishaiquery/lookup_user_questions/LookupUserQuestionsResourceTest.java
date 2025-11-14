package me.sathish.sathishaidashboard.sathishaiquery.lookup_user_questions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import me.sathish.sathishaidashboard.base.config.BaseIT;
import me.sathish.sathishaidashboard.sathishaiquery.config.TestDataSathishaiquery;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;


public class LookupUserQuestionsResourceTest extends BaseIT {

    @Autowired
    public TestDataSathishaiquery testDataSathishaiquery;

    @Autowired
    public LookupUserQuestionsRepository lookupUserQuestionsRepository;

    @BeforeEach
    public void beforeEach() {
        testDataSathishaiquery.clearAll();
        testData.clearAll();
    }

    @Test
    void getAllLookupUserQuestionss_success() {
        testDataSathishaiquery.lookupUserQuestions();

        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/lookupUserQuestionss")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(2))
                    .body("_embedded.lookupUserQuestionsDTOList.get(0).id", Matchers.equalTo(1100))
                    .body("_links.self.href", Matchers.endsWith("/api/lookupUserQuestionss?page=0&size=20&sort=id,asc"));
    }

    @Test
    void getAllLookupUserQuestionss_filtered() {
        testDataSathishaiquery.lookupUserQuestions();

        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/lookupUserQuestionss?filter=1101")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(1))
                    .body("_embedded.lookupUserQuestionsDTOList.get(0).id", Matchers.equalTo(1101));
    }

    @Test
    void getLookupUserQuestions_success() {
        testDataSathishaiquery.lookupUserQuestions();

        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/lookupUserQuestionss/1100")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("questionAsked", Matchers.equalTo("Quis nostrud exerci."))
                    .body("_links.self.href", Matchers.endsWith("/api/lookupUserQuestionss/1100"));
    }

    @Test
    void getLookupUserQuestions_notFound() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/lookupUserQuestionss/1766")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createLookupUserQuestions_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/lookupUserQuestionsDTORequest.json"))
                .when()
                    .post("/api/lookupUserQuestionss")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, lookupUserQuestionsRepository.count());
    }

    @Test
    void updateLookupUserQuestions_success() {
        testDataSathishaiquery.lookupUserQuestions();

        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/lookupUserQuestionsDTORequest.json"))
                .when()
                    .put("/api/lookupUserQuestionss/1100")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("_links.self.href", Matchers.endsWith("/api/lookupUserQuestionss/1100"));
        assertEquals("At vero eos.", lookupUserQuestionsRepository.findById(((long)1100)).orElseThrow().getQuestionAsked());
        assertEquals(2, lookupUserQuestionsRepository.count());
    }

    @Test
    void deleteLookupUserQuestions_success() {
        testDataSathishaiquery.lookupUserQuestions();

        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/lookupUserQuestionss/1100")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, lookupUserQuestionsRepository.count());
    }

}
