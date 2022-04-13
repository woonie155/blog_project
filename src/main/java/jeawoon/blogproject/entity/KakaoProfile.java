package jeawoon.blogproject.entity;

import lombok.Data;


//회원 정보 응답 매핑클래스
/*
 json => 자바 객체 클래스 변환 사이트
 https://www.jsonschema2pojo.org/
 주의: 변환시 _는 카멜케이스로 변환되므로 후작업 필요
*/
@Data
public class KakaoProfile {

    public long id;
    public String connected_at;
    public Properties properties;
    public KakaoAccount kakao_account;

    @Data
    public class Properties {
        public String nickname;
        public String profile_image;
        public String thumbnail_image;
    }

    @Data
    public class KakaoAccount {
        public Boolean profile_nickname_needs_agreement;
        public Boolean profile_image_needs_agreement;
        public Profile profile;
        public Boolean has_email;
        public Boolean email_needs_agreement;
        public Boolean is_email_valid;
        public Boolean is_email_verified;
        public String email;

        @Data
        public class Profile {
            public String nickname;
            public String thumbnail_image_url;
            public String profile_image_url;
            public Boolean is_default_image;
        }
    }
}
