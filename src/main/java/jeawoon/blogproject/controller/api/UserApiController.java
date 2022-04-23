package jeawoon.blogproject.controller.api;

import jeawoon.blogproject.Config.auth.PrincipalDetail;
import jeawoon.blogproject.dto.JoinRequestDto;
import jeawoon.blogproject.dto.ResponseDto;
import jeawoon.blogproject.entity.User;
import jeawoon.blogproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
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
    public ResponseDto<Integer> joinAPI(@Validated @RequestBody JoinRequestDto dto){
        userService.user_join(dto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
    //post : auth/loginProc => 스프링 시큐리티 이용


    @PutMapping("/api/user/update")
    public ResponseDto<Integer> update(@RequestBody User user) {
        userService.user_update(user);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

}

