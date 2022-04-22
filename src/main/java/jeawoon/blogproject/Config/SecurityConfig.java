package jeawoon.blogproject.Config;


import jeawoon.blogproject.Config.auth.PrincipalService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity //시큐리티 필터 추가 적용 (스프링 시큐리티는 이미 활성화중이지만)
@EnableGlobalMethodSecurity(prePostEnabled = true) //권한 및 인증을 요청수행전 미리 체크.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalService principalService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean //해쉬방식 지정
    public BCryptPasswordEncoder encodePWD(){
        return new BCryptPasswordEncoder();
    }

    @Override //로그인시, 세션에 들어있는 정보인 비밀번호 해쉬 비교
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalService).passwordEncoder(encodePWD());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {  //필터
        http
            .csrf().disable()//토큰 비활성화(테스트용)
                .authorizeRequests()
                .antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**")
                .permitAll()    // 위의 접근들 허용
                .anyRequest()   // 그 외의 접근들
                .authenticated()// 인증 필요함
                .and() // antMatchers 주소 외엔 인증필요하므로 밑으로 전달
                .formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/loginProc") //해당주소로 들어오는 값을 로그인 요청으로 보고 가로챔(post- x www form urlencoded형태)
                .defaultSuccessUrl("/");//로그인 정상 처리 될 시 이동(/는 원래이동하려했던 곳으로). (실패- failureUrl)
    }
}