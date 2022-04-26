package jeawoon.blogproject.Config.auth;

import jeawoon.blogproject.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


//시큐리티 내부 세션저장소이용 위한, 타입변경용클래스
@Data
public class PrincipalDetail implements UserDetails, OAuth2User {

    private User user;
    private Map<String, Object> attributes;

    public PrincipalDetail(User user) { //일반 로그인 시,
        this.user = user;
    }
    public PrincipalDetail(User user, Map<String, Object> attributes) { //OAuth 로그인 시,
        this.user = user;
        this.attributes = attributes;
    }

    @Override //해시적용된
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLoginId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; //계정 만료안됨
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; //계정 안잠김
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  //비번 만료안됨
    }

    @Override
    public boolean isEnabled() {
        return true;  //계정 활성화중 - 휴면계정관련
    }

    @Override //접근권한 계층 저장
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(()->{ return "ROLE_" + user.getRole();});
        return collectors;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return String.valueOf(user.getId());
    }
}

