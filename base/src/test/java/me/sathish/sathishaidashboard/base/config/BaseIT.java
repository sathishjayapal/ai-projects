package me.sathish.sathishaidashboard.base.config;

import io.restassured.RestAssured;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import me.sathish.sathishaidashboard.base.SathishaidashboardApplication;
import me.sathish.sathishaidashboard.base.file.FileContentRepository;
import me.sathish.sathishaidashboard.base.file.FileDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StreamUtils;
import org.testcontainers.containers.MongoDBContainer;


/**
 * Abstract base class to be extended by every IT test. Starts the Spring Boot context with a
 * Datasource connected to the Testcontainers Docker instance. The instance is reused for all tests,
 * with all data wiped out before each test.
 */
@SpringBootTest(
        classes = SathishaidashboardApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("it")
public abstract class BaseIT {

    @ServiceConnection
    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:8.0.13");

    static {
        mongoDBContainer.withReuse(true)
                .start();
    }

    @LocalServerPort
    public int serverPort;

    @Autowired
    public TestData testData;

    @Autowired
    public FileContentRepository fileContentRepository;

    @Value("classpath:testFile.txt")
    public Resource testFile;

    @PostConstruct
    public void initRestAssured() {
        RestAssured.port = serverPort;
        RestAssured.urlEncodingEnabled = false;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @SneakyThrows
    public String readResource(final String resourceName) {
        return StreamUtils.copyToString(getClass().getResourceAsStream(resourceName), StandardCharsets.UTF_8);
    }

    @SneakyThrows
    public void prepareUpload(final String uid, final String fileName) {
        final File uploadFile = new File(FileDataService.UPLOAD_DIRECTORY + "/" + uid + "/" + fileName);
        uploadFile.getParentFile().mkdirs();
        uploadFile.createNewFile();
        FileCopyUtils.copy(testFile.getContentAsByteArray(), uploadFile);
    }

}
