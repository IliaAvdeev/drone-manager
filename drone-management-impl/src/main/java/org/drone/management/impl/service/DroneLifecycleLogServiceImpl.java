package org.drone.management.impl.service;

import io.quarkus.panache.common.Sort;
import org.drone.management.api.service.DroneLifecycleLogService;
import org.drone.management.impl.repository.DroneLifecycleLogRepositoryImpl;
import org.drone.management.model.db.DbRecords;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Named
@Transactional
@ApplicationScoped
public class DroneLifecycleLogServiceImpl implements DroneLifecycleLogService {
    @Inject
    DroneLifecycleLogRepositoryImpl droneLifecycleLogRepository;

    @Override
    public OffsetDateTime getLatestStateTransitionTimestamp(UUID droneId) {
        return droneLifecycleLogRepository.find("droneId", Sort.descending("transitionTimestamp"), droneId)
                .stream()
                .findFirst()
                .get()
                .transitionTimestamp();
    }

    @Override
    public void save(DbRecords.DroneLifecycleLog log) {
        droneLifecycleLogRepository.persist(log);
    }

    @Override
    public void save(List<DbRecords.DroneLifecycleLog> logs) {
        droneLifecycleLogRepository.persist(logs);
    }
}