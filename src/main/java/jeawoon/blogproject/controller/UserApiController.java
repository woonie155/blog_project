package jeawoon.blogproject.controller;

import jeawoon.blogproject.dto.ResponseDto;
import jeawoon.blogproject.entity.RoleType;
import jeawoon.blogproject.entity.User;
import jeawoon.blogproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> joinAPI(@RequestBody User user){
        userService.user_join(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    //post : auth/loginProc => 스프링 시큐리티 이용

}

