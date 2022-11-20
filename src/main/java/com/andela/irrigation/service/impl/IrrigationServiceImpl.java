package com.andela.irrigation.service.impl;

import com.andela.irrigation.entity.Irrigation;
import com.andela.irrigation.entity.Land;
import com.andela.irrigation.enums.IrrigationStatus;
import com.andela.irrigation.payload.converter.IrrigationConverter;
import com.andela.irrigation.payload.dto.IrrigationDto;
import com.andela.irrigation.repository.IrrigationRepository;
import com.andela.irrigation.repository.LandRepository;
import com.andela.irrigation.service.IrrigationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class IrrigationServiceImpl implements IrrigationService {

    private final IrrigationRepository irrigationRepository;
    private final LandRepository landRepository;

    private final EntityManager entityManager;

    private Irrigation getById(Long id) {
        return irrigationRepository.getReferenceById(id);
    }

    @Override
    public IrrigationDto getIrrigationById(Long id) {
        return IrrigationConverter.toIrrigationDto(this.getById(id));
    }

    @Override
    public IrrigationDto update(IrrigationDto irrigationDto) {
        Irrigation currentIrrigation = getById(irrigationDto.getId());
        Irrigation irrigation = IrrigationConverter.toIrrigation(irrigationDto);
        irrigation.setUpdatedAt(LocalDateTime.now());

        //Not allowed to update created At field
        irrigation.setCreatedAt(currentIrrigation.getCreatedAt());
        return IrrigationConverter.toIrrigationDto(this.save(irrigation));
    }

    @Override
    public IrrigationDto createNewIrrigation(IrrigationDto irrigationDto, Long landId) {
        LocalDateTime now = LocalDateTime.now();
        Land land = landRepository.getReferenceById(landId);
        land.setIrrigation(IrrigationConverter.toIrrigation(irrigationDto));
        land.getIrrigation().setCreatedAt(now);
        land.getIrrigation().setUpdatedAt(now);
        land.setUpdatedAt(now);
        if (land.getIrrigation().getNextIrrigationAt() == null) {
            land.getIrrigation().setNextIrrigationAt(LocalDateTime.now());
        }
        return IrrigationConverter.toIrrigationDto(landRepository.save(land).getIrrigation());
    }

    @Override
    public Collection<Irrigation> getAllReadyIrrigationTriggersAndUpdateToReadyStatus() {
        return getAllReadyIrrigationTriggers().stream()
                .filter(irrigation ->
                        updateIrrigationStatusWithConditionalUpdate(irrigation, IrrigationStatus.ACTIVE, IrrigationStatus.PROCESSING))
                .collect(Collectors.toList());

    }

    @Override
    public Collection<Irrigation> getAllReadyIrrigationTriggers() {
        return irrigationRepository.findAllByIrrigationStatusIn(List.of(IrrigationStatus.ACTIVE)).stream()
                .filter(irrigation -> irrigation.getNextIrrigationAt() != null &&
                        (irrigation.getNextIrrigationAt().isBefore(LocalDateTime.now()) || irrigation.getNextIrrigationAt().isEqual(LocalDateTime.now())))
                .collect(Collectors.toList());
    }

    boolean updateIrrigationStatusWithConditionalUpdate(Irrigation irrigation, IrrigationStatus currentStatus, IrrigationStatus newStatus) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Irrigation> q = criteriaBuilder.createCriteriaUpdate(Irrigation.class);
        Root<Irrigation> root = q.from(Irrigation.class);

        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(root.get("id"), irrigation.getId()));
        predicates.add(criteriaBuilder.equal(root.get("irrigationStatus"), currentStatus));

        q.set(root.get("irrigationStatus"), newStatus).set(root.get("updatedAt"), LocalDateTime.now())
                .where(predicates.toArray(new Predicate[]{}));
        return entityManager.createQuery(q).executeUpdate() > 0;
    }

    public Irrigation save(Irrigation irrigation) {
        return irrigationRepository.save(irrigation);
    }
}
