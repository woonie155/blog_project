package jeawoon.blogproject.Config.auth;

import jeawoon.blogproject.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


//시큐리티 내부 세션저장소이용 위한, 타입변경용클래스
public class PrincipalDetail implements UserDetails{

    private User user;
    public PrincipalDetail(User user){
        this.user=user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
        return true;  //계정 활성화중
    }

    @Override //접근권한 계층 저장
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(()->{ return "ROLE_" + user.getRole();});
        return collectors;
    }
}

