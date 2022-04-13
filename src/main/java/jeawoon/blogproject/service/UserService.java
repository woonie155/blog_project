package jeawoon.blogproject.service;

import jeawoon.blogproject.entity.RoleType;
import jeawoon.blogproject.entity.User;
import jeawoon.blogproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public void user_join(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(RoleType.USER);
        userRepository.save(user);
    }


    @Transactional
    public void user_update(User user) {
        User updateUser = userRepository.findById(user.getId()).orElseThrow(() -> {
            return new IllegalArgumentException("회원 찾기 에러");
        });

        //OAuth 사용자는 비번,이메일 변경 불가
        if(updateUser.getOauth()==null || updateUser.getOauth().equals("")){
            updateUser.setPassword(encoder.encode(user.getPassword()));
            updateUser.setEmail(user.getEmail());
        }
    }

    @Transactional(readOnly = true) //카카오 로그인시, 가입자 미가입자 구분
    public User kakao_login_findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseGet(() -> {
            return new User();
        });
        return user;
    }

}
