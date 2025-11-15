package me.sathish.ai_project.base.security;

import java.util.Collection;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


/**
 * Extension of Spring Security User class to store additional data.
 */
@Getter
public class AiprojectsecurityUserDetails extends User {

    private final Long userid;

    public AiprojectsecurityUserDetails(final Long userid, final String username, final String hash,
            final Collection<? extends GrantedAuthority> authorities) {
        super(username, hash, authorities);
        this.userid = userid;
    }

}
