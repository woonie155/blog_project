package jeawoon.blogproject.service;

import jeawoon.blogproject.dto.JoinRequestDto;
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
    public void user_join(JoinRequestDto dto){
        dto.setPassword(encoder.encode(dto.getPassword()));
        dto.setRole(RoleType.USER);
        User user = dto.toEntity();
        userRepository.save(user);
    }


    @Transactional
    public void user_update(User user) {
        User updateUser = userRepository.findById(user.getId()).orElseThrow(() -> {
            return new IllegalArgumentException("회원 찾기 에러");
        });

        //OAuth 사용자는 비번,이메일 변경 불가
        if(updateUser.getProvider()==null || updateUser.getProvider().equals("")){
            updateUser.setPassword(encoder.encode(user.getPassword()));
            updateUser.setEmail(user.getEmail());
        }
    }

    @Transactional //카카오 로그인시, 가입자 미가입자 구분
    public void kakao_login_findByLoginId(JoinRequestDto dto) {
        userRepository.findByLoginId(dto.getLoginId()).orElseGet(() -> {
            dto.setPassword(encoder.encode(dto.getPassword()));
            User user = dto.toEntity();
            userRepository.save(user);
            return null;
        });
    }

}
