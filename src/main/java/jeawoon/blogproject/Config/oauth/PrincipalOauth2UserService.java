package jeawoon.blogproject.Config.oauth;


import jeawoon.blogproject.Config.auth.PrincipalDetail;
import jeawoon.blogproject.Config.oauth.provider.GoogleUserInfo;
import jeawoon.blogproject.Config.oauth.provider.NaverUserInfo;
import jeawoon.blogproject.Config.oauth.provider.OAuth2UserInfo;
import jeawoon.blogproject.dto.JoinRequestDto;
import jeawoon.blogproject.entity.RoleType;
import jeawoon.blogproject.entity.User;
import jeawoon.blogproject.repository.UserRepository;
import jeawoon.blogproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor //OAuth 로그인 시
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {


    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override //구글로 정보(userRequest)받은 것 후처리 함수
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest); //정보를 담은
//        System.out.println("정보 = " + oAuth2User.getAttributes());
        return processOAuth2User(userRequest, oAuth2User);
        //세션 어센티케이션 객체로.
        //함수종료와 함께 @AuthenticationPrincipal 어노테이션 만들어짐
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User){
        OAuth2UserInfo oAuth2UserInfo = null;

        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
            System.out.println("구글로그인중");
        }
        else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
            System.out.println("네이버로그인중");
        }
        else {
            System.out.println("구글 & 네이버 아닌경우");
        }


        Optional<User> userOptional =
                userRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());
        if (userOptional.isPresent()) {
            return new PrincipalDetail(userOptional.get(), oAuth2User.getAttributes());
        } else { //미회원인경우 회원가입
            JoinRequestDto set_User = JoinRequestDto.builder()
                    .loginId(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId())
                    .password(bCryptPasswordEncoder.encode("비밀키"))
                    .username(oAuth2UserInfo.getName())
                    .nickname(oAuth2UserInfo.getName() + "_" + oAuth2UserInfo.getProviderId())
                    .email(oAuth2UserInfo.getEmail())
                    .role(RoleType.USER)
                    .provider(oAuth2UserInfo.getProvider())
                    .providerId(oAuth2UserInfo.getProviderId())
                    .build();
            userRepository.save(set_User.toEntity());
            return new PrincipalDetail(set_User.toEntity(), oAuth2User.getAttributes());
            //->세션에 Authentication객체생성
        }
    }
}
