package me.sathish.ai_project.base.user_info;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserInfoRepository extends MongoRepository<UserInfo, Long> {

    UserInfo findByUsernameIgnoreCase(String username);

    boolean existsByUsernameIgnoreCase(String username);

}
