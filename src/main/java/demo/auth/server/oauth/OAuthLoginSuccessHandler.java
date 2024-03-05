package demo.auth.server.oauth;

import java.util.Map;

import demo.auth.server.dto.domain.DemoUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final Oauth2UserService oauth2UserService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

        String email = null;
        String oauthType = token.getAuthorizedClientRegistrationId();

        if("naver".equals(oauthType.toLowerCase())) {
            email = ((Map<String, Object>) token.getPrincipal().getAttribute("response")).get("email").toString();
        }

        log.info("Login Success!: {} from {}", email, oauthType);

        DemoUser demoUser = oauth2UserService.getUserByEmailAndOauthType(email, oauthType);
        log.info("User Saved In Session");
//        HttpSession session = request.getSession();
//        session.setAttribute("user", demoUser);

//        response.sendRedirect("/login/oauth2/code/" + oauthType);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
