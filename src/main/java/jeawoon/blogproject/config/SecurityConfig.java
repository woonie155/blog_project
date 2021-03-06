package jeawoon.blogproject.config;


import jeawoon.blogproject.config.auth.PrincipalService;
import jeawoon.blogproject.config.oauth.PrincipalOauth2UserService;
import jeawoon.blogproject.handler.LoginFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //권한 및 인증을 요청수행전 미리 체크.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalService principalService;
    private final PrincipalOauth2UserService principalOauth2UserService;
    private final BCryptPasswordEncoder encodePWD;

    @Bean
    @Override //회원정보 수정시 필요(세션교체)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false); //아이디실패, 비번실패 로직 분리
        daoAuthenticationProvider.setUserDetailsService(principalService);
        daoAuthenticationProvider.setPasswordEncoder(encodePWD);
        return daoAuthenticationProvider;
    }

    @Override //로그인시, 세션에 들어있는 정보인 비밀번호 해쉬 비교
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    AuthenticationFailureHandler failureHandler(){
        return new LoginFailureHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {  //필터
        http
            .csrf().disable()//토큰 비활성화(테스트용)
                .authorizeRequests()
                .antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/error")
                .permitAll()    // 위의 접근들 허용
                .anyRequest().authenticated()   // 그 외 접근, 인증 필요
                .and() // antMatchers 주소 외엔 인증필요하므로 밑으로 전달
                    .formLogin()
                    .usernameParameter("loginId")
                    .passwordParameter("password")
                    .loginPage("/auth/login") //해당url은 '로그인페이지' 요청의미(컨트롤러쪽에 전송될것)
                    .loginProcessingUrl("/auth/loginProc") //해당주소로 들어오는 값을 로그인 정보로 보고 가로챔(post- x www form urlencoded형태)
                    .defaultSuccessUrl("/")//로그인 정상 처리 될 시 이동(/는 원래이동하려했던 곳으로)
                    .failureUrl("/auth/login?error=true")
                    .failureHandler(failureHandler())
                //로그아웃은 디폴트값이용 /logout
                .and()
                    .oauth2Login()
                    .loginPage("/auth/login")
                    .userInfoEndpoint()
                    .userService(principalOauth2UserService);

    }
}