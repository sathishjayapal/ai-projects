package me.sathish.ai_project.base.config;

import me.sathish.ai_project.base.file.FileContentRepository;
import me.sathish.ai_project.base.user_info.UserInfo;
import me.sathish.ai_project.base.user_info.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class TestData {

    @Autowired
    public UserInfoRepository userInfoRepository;

    @Autowired
    public FileContentRepository fileContentRepository;

    public void clearAll() {
        userInfoRepository.deleteAll();
        fileContentRepository.deleteAll();
    }

    public void userInfo() {
        final UserInfo userInfo = new UserInfo();
        userInfo.setUserid((long)1200);
        userInfo.setUsername("admin");
        userInfo.setPassword("{bcrypt}$2a$10$FMzmOkkfbApEWxS.4XzCKOR7EbbiwzkPEyGgYh6uQiPxurkpzRMa6");
        userInfoRepository.save(userInfo);
        final UserInfo userInfo1 = new UserInfo();
        userInfo1.setUserid((long)1201);
        userInfo1.setUsername("userQuestionsRole");
        userInfo1.setPassword("{bcrypt}$2a$10$FMzmOkkfbApEWxS.4XzCKOR7EbbiwzkPEyGgYh6uQiPxurkpzRMa6");
        userInfoRepository.save(userInfo1);
        final UserInfo userInfo2 = new UserInfo();
        userInfo2.setUserid((long)1202);
        userInfo2.setUsername("ragUserRole");
        userInfo2.setPassword("{bcrypt}$2a$10$FMzmOkkfbApEWxS.4XzCKOR7EbbiwzkPEyGgYh6uQiPxurkpzRMa6");
        userInfoRepository.save(userInfo2);
        final UserInfo userInfo3 = new UserInfo();
        userInfo3.setUserid((long)1203);
        userInfo3.setUsername("any");
        userInfo3.setPassword("{bcrypt}$2a$10$FMzmOkkfbApEWxS.4XzCKOR7EbbiwzkPEyGgYh6uQiPxurkpzRMa6");
        userInfoRepository.save(userInfo3);
    }

}
