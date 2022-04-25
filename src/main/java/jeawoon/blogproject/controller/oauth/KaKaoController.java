package jeawoon.blogproject.controller.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jeawoon.blogproject.dto.JoinRequestDto;
import jeawoon.blogproject.dto.KakaoProfile;
import jeawoon.blogproject.dto.KaKAoOAuthToken;
import jeawoon.blogproject.entity.RoleType;
import jeawoon.blogproject.entity.User;
import jeawoon.blogproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequiredArgsConstructor
public class KaKaoController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;


    @Value("${jw.key}")
    private String jwKey;

    @GetMapping("/auth/kakao/callback") //인증성공후 코드응답받을 주소
    public String KaKaoCallBack(String code){//파라미터로 코드를 받음

        String client_id = "3be8c1c8253cc21d8691029eccbe6b75";
        String redirect_uri = "http://localhost:8080/auth/kakao/callback";
        String grant_type = "authorization_code";

        RestTemplate rt = new RestTemplate(); //토큰요청할 Http요청위해 용이한 라이브러리

        //헤더
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //바디
        MultiValueMap<String, String> bodys = new LinkedMultiValueMap<>();
        bodys.add("grant_type",grant_type);
        bodys.add("client_id", client_id);
        bodys.add("redirect_uri", redirect_uri);
        bodys.add("code", code);

        //헤더+바디를 exchange들어갈 타입인 HttpEntity로 변환
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(bodys, headers);

        //Http요청하여 응답받기(토큰)
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );
        //System.out.println("로그인 인증으로 토큰 받기\n= " + response.getBody());



        //토큰으로 정보가져오기
        ObjectMapper objectMapper = new ObjectMapper(); //JSON형태를 객체형태로 변환위해
        KaKAoOAuthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(response.getBody(), KaKAoOAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        RestTemplate rt2 = new RestTemplate();
        // HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // 헤더를 exchange들어갈 타입인 HttpEntity로 변환
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);

        //Http요청하여 응답받기
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );
        //System.out.println("토큰받아 회원정보 받기\n= " + response2.getBody());

        //사용자정보JSON을 객체로 변환
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
        System.out.println("블로그서버 유저아이디 : " + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
        System.out.println("블로그서버 이메일 : " + kakaoProfile.getKakao_account().getEmail());
        System.out.println("블로그서버 패스워드 : " + jwKey);

        //웹서버 내로, 회원가입 로직
        JoinRequestDto set_User = JoinRequestDto.builder()
                .loginId(kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId())
                .password(jwKey)
                .username("카카오로그인계정자")
                .nickname(kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId())
                .email(kakaoProfile.getKakao_account().getEmail())
                .role(RoleType.USER)
                .oauth("kakao")
                .build();

        userService.kakao_login_findByLoginId(set_User); //미가입자면 회원가입시키기

        // 로그인 처리 및 세션처리
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(set_User.getLoginId(), jwKey));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";
    }

}
