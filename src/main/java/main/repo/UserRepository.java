package main.repo;

import main.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    User findUserByUsernameAndActive(String username, boolean isActive);

    User findByActivationCode(String code);
}
