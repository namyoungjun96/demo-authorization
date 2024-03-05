package demo.auth.server.dto.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "person")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of ={"id, "})
public class DemoUser {
    @Id
    @Column(name = "p_id")
    private String id;
    @Column(name = "p_password")
    private String password;
    @Column(name = "p_name")
    private String name;
    @Column(name = "p_email", unique = true)
    private String email;
    @Column(name = "auth_type")
    private String authType;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;
}
