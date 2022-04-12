package jeawoon.blogproject.controller.api;

import jeawoon.blogproject.Config.auth.PrincipalDetail;
import jeawoon.blogproject.dto.ResponseDto;
import jeawoon.blogproject.entity.User;
import jeawoon.blogproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> joinAPI(@RequestBody User user){
        userService.user_join(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
    //post : auth/loginProc => 스프링 시큐리티 이용


    @PutMapping("/api/user/update")
    public ResponseDto<Integer> update(@RequestBody User user, @AuthenticationPrincipal PrincipalDetail principal) {
        userService.user_update(user);
        //-> 세션 업데이트도 필요
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

}

