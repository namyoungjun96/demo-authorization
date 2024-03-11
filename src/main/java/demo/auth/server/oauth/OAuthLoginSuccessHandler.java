package demo.auth.server.oauth;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import demo.auth.server.dto.domain.DemoUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final Oauth2UserService oauth2UserService;
    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

        String email = null;
        String oauthType = token.getAuthorizedClientRegistrationId();

        if ("naver".equals(oauthType.toLowerCase())) {
            email = ((Map<String, Object>) token.getPrincipal().getAttribute("response")).get("email").toString();
        }

        log.info("Login Success!: {} from {}", email, oauthType);

//        DemoUser demoUser = oauth2UserService.getUserByEmailAndOauthType(email, oauthType);


//        var oAuth2AuthorizedClient = oAuth2AuthorizedClientService.loadAuthorizedClient("naver", authentication.getName());
//        log.info("User Saved In Session");
//        HttpSession session = request.getSession();
//        session.setAttribute("user", demoUser);

        String targetUrl = determineTargetUrl(request, response, authentication);
        response.sendRedirect(targetUrl);
        super.onAuthenticationSuccess(request, response, authentication);
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {

        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

//        OAuth2UserPrincipal principal = getOAuth2UserPrincipal(authentication);
//
//        if (principal == null) {
//            return UriComponentsBuilder.fromUriString(targetUrl)
//                    .queryParam("error", "Login failed")
//                    .build().toUriString();
//        }

        var oAuth2AuthorizedClient = oAuth2AuthorizedClientService.loadAuthorizedClient("naver", authentication.getName());
        String accessToken = oAuth2AuthorizedClient.getAccessToken().getTokenValue();
        String refreshToken = oAuth2AuthorizedClient.getRefreshToken().getTokenValue();

        return UriComponentsBuilder.newInstance()
                .host("localhost:3000")
                .queryParam("access_token", accessToken)
                .queryParam("refresh_token", !refreshToken.isEmpty() ? refreshToken : "null")
                .build().toUriString();
    }
}
