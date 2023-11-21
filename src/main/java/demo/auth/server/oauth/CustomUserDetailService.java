package demo.auth.server.oauth;

import demo.auth.server.dao.DemoUserRepository;
import demo.auth.server.dto.domain.DemoUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final DemoUserRepository demoUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DemoUser user = demoUserRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 %s를 찾을 수 없습니다.".formatted(username)));
        return new CustomDetails(user);
    }
}
