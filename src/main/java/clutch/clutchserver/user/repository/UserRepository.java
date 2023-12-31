package clutch.clutchserver.user.repository;


import clutch.clutchserver.global.common.enums.AuthProvider;
import clutch.clutchserver.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}