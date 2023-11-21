package demo.auth.server.dao;

import demo.auth.server.dto.domain.DemoUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemoUserRepository extends JpaRepository<DemoUser, String> {
}
