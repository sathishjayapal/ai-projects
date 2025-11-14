package me.sathish.sathishaidashboard.base.config;

import java.io.File;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.FileTemplateResolver;


/**
 * Load Thymeleaf files from the file system during development, without any caching.
 */
@Configuration
@Profile("local")
public class LocalDevConfig {

    @SneakyThrows
    public LocalDevConfig(final TemplateEngine templateEngine) {
        final ClassPathResource applicationYml = new ClassPathResource("application.yml");
        if (applicationYml.isFile()) {
            File sourceRoot = applicationYml.getFile().getParentFile();
            while (sourceRoot.listFiles((dir, name) -> name.equals("mvnw")).length != 1) {
                sourceRoot = sourceRoot.getParentFile();
            }
            int order = 0;
            for (final String module : List.of("sathishaidashboard-base", "sathishaidashboard-sathishaiquery", "sathishaidashboard-ragfiledata", "sathishaidashboard-web")) {
                final FileTemplateResolver fileTemplateResolver = new FileTemplateResolver();
                fileTemplateResolver.setPrefix(sourceRoot.getPath() + "/" + module + "/src/main/resources/templates/");
                fileTemplateResolver.setSuffix(".html");
                fileTemplateResolver.setCacheable(false);
                fileTemplateResolver.setCharacterEncoding("UTF-8");
                fileTemplateResolver.setCheckExistence(true);
                fileTemplateResolver.setOrder(++order);
                if (order == 1) {
                    templateEngine.setTemplateResolver(fileTemplateResolver);
                } else {
                    templateEngine.addTemplateResolver(fileTemplateResolver);
                }
            }
        }
    }

}
