package me.sathish.ai_project.base.config;

import me.sathish.ai_project.base.file.FileContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class TestData {

    @Autowired
    public FileContentRepository fileContentRepository;

    public void clearAll() {
        fileContentRepository.deleteAll();
    }

}
