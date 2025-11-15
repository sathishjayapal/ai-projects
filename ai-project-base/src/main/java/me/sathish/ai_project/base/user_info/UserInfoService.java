package me.sathish.ai_project.base.user_info;

import java.util.Map;
import me.sathish.ai_project.base.util.CustomCollectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;

    public UserInfoService(final UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public Map<Long, String> getUserInfoValues() {
        return userInfoRepository.findAll(Sort.by("userid"))
                .stream()
                .collect(CustomCollectors.toSortedMap(UserInfo::getUserid, UserInfo::getUsername));
    }

}
