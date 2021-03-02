package io.hamlet.projs.suit.repository;

import io.hamlet.projs.suit.entity.User;
import io.hamlet.projs.suit.entity.UserGarment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGarmentRepository extends JpaRepository<UserGarment, Long> {
    UserGarment findByUserIdAndGarmentId(Long garmentId, long currentUserId);
}
