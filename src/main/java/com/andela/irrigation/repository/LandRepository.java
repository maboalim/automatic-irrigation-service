package com.andela.irrigation.repository;

import com.andela.irrigation.entity.Land;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LandRepository extends JpaRepository<Land, Long> {
}
