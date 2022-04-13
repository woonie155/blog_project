package jeawoon.blogproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jeawoon.blogproject.Config.auth.PrincipalDetail;
import jeawoon.blogproject.entity.KakaoProfile;
import jeawoon.blogproject.entity.OAuthToken;
import jeawoon.blogproject.entity.RoleType;
import jeawoon.blogproject.entity.User;
import jeawoon.blogproject.service.BoardService;
import jeawoon.blogproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BoardService boardService;
    private final AuthenticationManager authenticationManager;

    @Value("${jw.key}")
    private String jwKey;

    @GetMapping({"", "/"})
    public String main(Model model, @PageableDefault(size=3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        model.addAttribute("boards", boardService.post_list(pageable));
        return "home";
    }
    @GetMapping("/auth/join")
    public String joinForm(){
        return "user/joinForm";
    }
    @GetMapping("/auth/login")
    public String loginForm(){
        return "user/loginForm";
    }

    @GetMapping("/user/detail") //업데이트
    public String detail(Model model, @AuthenticationPrincipal PrincipalDetail principal){
        model.addAttribute("principal", principal.getUser());
        return "user/detailForm";
    }

    @GetMapping("/auth/kakao/callback") //인증 성공 시 받는 주소
    public String KaKaoCallBack(String code){//파라미터로 코드를 받음.
        String client_id = "3be8c1c8253cc21d8691029eccbe6b75";
        String redirect_uri = "http://localhost:8080/auth/kakao/callback";

        RestTemplate rt = new RestTemplate(); //Http메시지 생성하는 라이브러리 (post http요청위해)

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", client_id);
        params.add("redirect_uri", redirect_uri);
        params.add("code", code);

        //헤더와 바디를 exchange들어갈 타입으로 변환: HttpEntity
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        //Http요청하여 응답받기
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );
        System.out.println("로그인 인증으로 토큰 받기\n= " + response.getBody());


        //토큰으로 정보가져오기
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;

        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        RestTemplate rt2 = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // 헤더+바디를 exchange들어갈 타입으로 변환: HttpEntity
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);

        //Http요청하여 응답받기
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );
        System.out.println("토큰받아 회원정보 받기\n= " + response2.getBody());

        //객체로 변환
        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("카카오 아이디(번호) : " + kakaoProfile.getId());
        System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());
        //회원으로 저장할 것(중복 고려)
        System.out.println("블로그서버 유저네임 : " + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
        System.out.println("블로그서버 이메일 : " + kakaoProfile.getKakao_account().getEmail());
        System.out.println("블로그서버 패스워드 : " + jwKey);

        User set_User = new User();
        set_User.setUsername(kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
        set_User.setPassword(jwKey);
        set_User.setEmail(kakaoProfile.getKakao_account().getEmail());
        set_User.setRole(RoleType.USER);
        set_User.setOauth("kakao");

        User originUser = userService.kakao_login_findByUsername(set_User.getUsername());
        if (originUser.getUsername() == null) {
            System.out.println("기존에 없던 회원이므로, 회원가입 처리");
            userService.user_join(set_User);
        }

        // 로그인 처리 및 세션처리
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(set_User.getUsername(), jwKey));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";
    }

}
