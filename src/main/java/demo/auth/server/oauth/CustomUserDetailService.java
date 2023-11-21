package demo.auth.server.oauth;

import demo.auth.server.dto.domain.TestUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TestUser user = repository.findByUserName().orElseThrow(() -> {
            throw new UsernameNotFoundException("해당 %s를 찾을 수 없습니다.".formatted(username));
        });
        return new CustomDetails(user);
    }
}
