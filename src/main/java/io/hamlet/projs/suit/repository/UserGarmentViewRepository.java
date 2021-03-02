package io.hamlet.projs.suit.repository;

import io.hamlet.projs.suit.entity.UserGarment;
import io.hamlet.projs.suit.entity.view.UserGarmentView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGarmentViewRepository extends JpaRepository<UserGarmentView, Long> {
    List<UserGarmentView> findAllByUserId(Long userId);
}
