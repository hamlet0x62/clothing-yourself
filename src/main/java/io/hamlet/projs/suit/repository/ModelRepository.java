package io.hamlet.projs.suit.repository;

import io.hamlet.projs.suit.entity.Garment;
import io.hamlet.projs.suit.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
    List<Model> findAllByGender(char charAt);
}
