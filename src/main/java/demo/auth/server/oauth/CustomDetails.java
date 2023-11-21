package demo.auth.server.oauth;

import demo.auth.server.dto.domain.DemoUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomDetails implements UserDetails {
    private final DemoUser demoUser;

    public CustomDetails(DemoUser demoUser) {
        this.demoUser = demoUser;
    }

    // 해당 유저의 권한을 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return demoUser.getPassword();
    }

    // Id
    @Override
    public String getUsername() {
        return demoUser.getId();
    }

    //    계정이 만료되지 않았는지 리턴 (true: 만료안됨)
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    //    계정이 잠겨있는지 않았는지 리턴 (true: 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    //    비밀번호가 만료되지 않았는지 리턴한다. (true: 만료안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    //    계정이 활성화(사용가능)인지 리턴 (true: 활성화)
    @Override
    public boolean isEnabled() {
        return false;
    }
}
