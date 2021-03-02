package io.hamlet.projs.suit.repository;

import io.hamlet.projs.suit.entity.Garment;
import io.hamlet.projs.suit.entity.GarmentClazz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GarmentClazzRepository extends JpaRepository<GarmentClazz, Long> {
    GarmentClazz findByNo(String no);
}
