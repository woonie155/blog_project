package jeawoon.blogproject.handler;

import org.aspectj.bridge.MessageUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFailureHandler implements AuthenticationFailureHandler {

    private final String failureURL = "/auth/login?error=true";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String message=null;


        if(exception instanceof UsernameNotFoundException || exception instanceof InternalAuthenticationServiceException){
            message = "해당 아이디의 계정은 없습니다.";
        }
        else if(exception instanceof BadCredentialsException){
            message = "비밀번호가 맞지 않습니다.";
        }
        else{
            message = "알수 없는 이유로 로그인 실패 했습니다. 관리자에게 문의 바랍니다.";
        }

        request.setAttribute("error", message);
        request.getRequestDispatcher(failureURL).forward(request, response);
    }


}
