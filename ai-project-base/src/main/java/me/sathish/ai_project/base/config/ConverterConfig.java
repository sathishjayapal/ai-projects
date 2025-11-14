package me.sathish.ai_project.base.config;

import me.sathish.ai_project.base.file.FileData;
import me.sathish.ai_project.base.file.FileDataService;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class ConverterConfig implements WebMvcConfigurer {

    private final FileDataService fileDataService;

    public ConverterConfig(final FileDataService fileDataService) {
        this.fileDataService = fileDataService;
    }

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        registry.addConverter(new Converter<MultipartFile, FileData>() {

            @Override
            public FileData convert(final MultipartFile upload) {
                return fileDataService.saveUpload(upload);
            }

        });
    }

}
