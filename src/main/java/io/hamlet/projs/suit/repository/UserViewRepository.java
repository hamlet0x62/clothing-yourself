package io.hamlet.projs.suit.repository;

import io.hamlet.projs.suit.entity.User;
import io.hamlet.projs.suit.entity.view.UserView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserViewRepository extends JpaRepository<UserView, Long> {

}
