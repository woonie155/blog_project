package jeawoon.blogproject.api;

import jeawoon.blogproject.config.auth.PrincipalDetail;
import jeawoon.blogproject.dto.*;
import jeawoon.blogproject.entity.User;
import jeawoon.blogproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    //회원등록
    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> joinAPI(@RequestBody @Validated JoinRequestDto dto){
        userService.user_join(dto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    //회원수정
    @PutMapping("/api/user/update")
    public ResponseDto<Integer> update(@RequestBody @Validated UpdateUserDto dto) {
        userService.user_update(dto, dto.getId());

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getLoginId(), dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
    //oauth회원수정
    @PutMapping("/api/oauth_user/update")
    public ResponseDto<Integer> oauth_update(@RequestBody @Validated UpdateOAuthUserDto dto, @AuthenticationPrincipal PrincipalDetail principal) {
        userService.oauth_user_update(dto, principal.getUser().getId());

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getLoginId(), dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }


    //모든회원 조회
    @GetMapping("/aa")
    public SearchAllUser_ResponseDto search_user(){
        List<User> users = userService.findUsers();

        List<UserDto> collect = users.stream()
                .map(u -> new UserDto(u.getLoginId(), u.getUsername(), u.getNickname(), u.getEmail()))
                .collect(Collectors.toList());
        return new SearchAllUser_ResponseDto(collect.size(), collect);
    }

}

