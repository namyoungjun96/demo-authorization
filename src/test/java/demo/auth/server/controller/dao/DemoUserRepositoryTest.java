package demo.auth.server.controller.dao;

import demo.auth.server.dao.DemoUserRepository;
import demo.auth.server.dto.domain.DemoUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DemoUserRepositoryTest {
    @Autowired
    DemoUserRepository demoUserRepository;

    @Test
    void when_find_by_id() {
        Optional<DemoUser> user = demoUserRepository.findById("jun");
        assertEquals("jun", user.get().getId());
    }
}
