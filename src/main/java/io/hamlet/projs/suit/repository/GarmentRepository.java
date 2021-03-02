package io.hamlet.projs.suit.repository;

import io.hamlet.projs.suit.entity.Garment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GarmentRepository extends JpaRepository<Garment, Long> {
    List<Garment> findAllByGender(char gender);

    List<Garment> findAllByClazzId(Long clazzId);

    List<Garment> findAllByGenderAndClazzId(char gender, Long clazzId);
}
