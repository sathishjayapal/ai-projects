package me.sathish.ai_project.base.security;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import me.sathish.ai_project.base.user_info.UserInfo;
import me.sathish.ai_project.base.user_info.UserInfoRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class AiprojectsecurityUserDetailsService implements UserDetailsService {

    private final UserInfoRepository userInfoRepository;

    public AiprojectsecurityUserDetailsService(final UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public AiprojectsecurityUserDetails loadUserByUsername(final String username) {
        final UserInfo userInfo = userInfoRepository.findByUsernameIgnoreCase(username);
        if (userInfo == null) {
            log.warn("user not found: {}", username);
            throw new UsernameNotFoundException("User " + username + " not found");
        }
        final String role = "userQuestionsRole".equals(username) ? UserRoles.USER_QUESTIONS_ROLE : 
                ("ragUserRole".equals(username) ? UserRoles.RAG_USER_ROLE : 
                ("any".equals(username) ? UserRoles.ANY : UserRoles.ADMIN));
        final List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
        return new AiprojectsecurityUserDetails(userInfo.getUserid(), username, userInfo.getPassword(), authorities);
    }

}
