package jeawoon.blogproject.entity;


import lombok.Data;

//회원 인증으로 로그인 가능시, 토큰과 함께 오는 정보들 매핑 클래스
@Data
public class OAuthToken {

    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private int refresh_token_expires_in;
}