package com.andela.irrigation.repository;

import com.andela.irrigation.entity.Irrigation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IrrigationRepository extends JpaRepository <Irrigation, Long> {
}
