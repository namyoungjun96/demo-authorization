package demo.auth.server.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class IndexController {
    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @GetMapping("/")
    public String index() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var oAuth2AuthorizedClient = oAuth2AuthorizedClientService.loadAuthorizedClient("naver", authentication.getName());
        log.debug("access token log: {}", oAuth2AuthorizedClient.getAccessToken());
        // 사용자의 이름을 출력
        log.debug("현재 사용자 이름: {}", authentication.getName());
        return "good!";
    }

    @GetMapping("/oauth2/callback/naver")
    public String loginSuccessCallback(@RequestParam String code,
                                       @RequestParam String state) {
        return "success!";
    }

    @GetMapping("/token")
    public ResponseEntity<String> test() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var oAuth2AuthorizedClient = oAuth2AuthorizedClientService.loadAuthorizedClient("naver", authentication.getName());

        return new ResponseEntity<>(oAuth2AuthorizedClient.getAccessToken().getTokenValue(), HttpStatus.OK);
    }
}
