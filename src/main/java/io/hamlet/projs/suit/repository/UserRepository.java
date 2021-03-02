package io.hamlet.projs.suit.repository;

import io.hamlet.projs.suit.entity.Role;
import io.hamlet.projs.suit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
