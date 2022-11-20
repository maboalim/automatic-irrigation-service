package com.andela.irrigation.repository;

import com.andela.irrigation.entity.Irrigation;
import com.andela.irrigation.enums.IrrigationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface IrrigationRepository extends JpaRepository<Irrigation, Long> {

    Collection<Irrigation> findAllByIrrigationStatusIn(Collection<IrrigationStatus> irrigationStatus);
}
