package jeawoon.blogproject.Config.auth;


import jeawoon.blogproject.entity.User;
import jeawoon.blogproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PrincipalService implements UserDetailsService {

    private final UserRepository userRepository;


    //로그인요청올시, 유저 네임으로 먼저 거르기. (form- name필드와 매칭)
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User principal = userRepository.findByLoginId(loginId)
                .orElseThrow(()->{
                    return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : "+loginId);
                });
        return new PrincipalDetail(principal); // UserDetails 타입으로 변경(저장) 후 반환
    }
}
