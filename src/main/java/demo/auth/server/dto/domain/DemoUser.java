package demo.auth.server.dto.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class DemoUser {
    @Id
    private String id;
    private String password;
    private String name;
}
