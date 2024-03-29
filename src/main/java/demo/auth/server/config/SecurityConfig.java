package demo.auth.server.config;

import demo.auth.server.oauth.CustomUserDetailService;
import demo.auth.server.oauth.OAuthLoginSuccessHandler;
import demo.auth.server.oauth.Oauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private String redirectUri;
    @Value("${spring.security.oauth2.client.registration.naver.authorization-grant-type}")
    private AuthorizationGrantType authorizationGrantType;
    @Value("${spring.security.oauth2.client.registration.naver.client-name}")
    private String clientName;
    @Value("${spring.security.oauth2.client.provider.naver.authorization_uri}")
    private String authorizationUri;
    @Value("${spring.security.oauth2.client.provider.naver.token_uri}")
    private String tokenUri;
    @Value("${spring.security.oauth2.client.provider.naver.user-info-uri}")
    private String userInfoUri;
    @Value("${spring.security.oauth2.client.provider.naver.user_name_attribute}")
    private String userNameAttribute;

    private final CustomUserDetailService customUserDetailService;
    private final Oauth2UserService oauth2UserService;
    private final OAuthLoginSuccessHandler oAuthLoginSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> request.requestMatchers("/login/oauth2/code/naver"))
                .authorizeHttpRequests(request -> request.anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2.clientRegistrationRepository(clientRegistrationRepository())
                                .userInfoEndpoint(userInfo -> userInfo
                                                .userService(oauth2UserService)
//                                .userAuthoritiesMapper(this.userAuthoritiesMapper())
//                                .oidcUserService(this.oidcUserService())
                                )
                                .successHandler(oAuthLoginSuccessHandler)
                                .defaultSuccessUrl("/test")
                );

        return http.build();
    }

    private ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(
                // 클라이언트 등록 정보 설정
                ClientRegistration.withRegistrationId("naver")
                        .clientId(clientId)
                        .clientSecret(clientSecret)
                        .redirectUri(redirectUri)
                        .authorizationGrantType(authorizationGrantType)
                        .authorizationUri(authorizationUri)  // 프로바이더의 Authorization URI 설정
                        .scope("name", "email")
                        .clientName(clientName)
                        .tokenUri(tokenUri)
                        .userInfoUri(userInfoUri)
                        .userNameAttributeName(userNameAttribute)
//                        .authorizationUriBuilderCustomizer(
//                                uriBuilder -> uriBuilder.queryParam("additional_param", "value"))  // 추가적인 파라미터 설정
                        .build()
        );
    }
}
