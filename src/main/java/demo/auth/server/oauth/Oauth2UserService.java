package demo.auth.server.oauth;

import demo.auth.server.dao.DemoUserRepository;
import demo.auth.server.dto.domain.DemoUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class Oauth2UserService extends DefaultOAuth2UserService {
    private final DemoUserRepository demoUserRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.debug("oAuth2User = {}" , oAuth2User.getAttributes());
        return super.loadUser(userRequest);
    }

    public DemoUser getUserByEmailAndOauthType(String email, String oauthType) {
        return demoUserRepository.findByEmail(email);
    }
}
