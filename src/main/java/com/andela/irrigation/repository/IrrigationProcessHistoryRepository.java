package com.andela.irrigation.repository;

import com.andela.irrigation.entity.IrrigationProcessHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IrrigationProcessHistoryRepository extends JpaRepository<IrrigationProcessHistory, Long> {
}
