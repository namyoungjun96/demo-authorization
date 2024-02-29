package demo.auth.server.dto.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "person")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DemoUser {
    @Id
    @Column(name = "p_id")
    private String id;
    @Column(name = "p_password")
    private String password;
    @Column(name = "p_name")
    private String name;
    @Column(name = "p_email")
    private String email;
}
