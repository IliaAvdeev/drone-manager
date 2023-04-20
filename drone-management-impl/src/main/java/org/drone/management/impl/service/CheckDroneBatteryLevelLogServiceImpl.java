package org.drone.management.impl.service;

import org.drone.management.api.service.CheckDroneBatteryLevelLogService;
import org.drone.management.impl.repository.CheckDroneBatteryLevelLogRepositoryImpl;
import org.drone.management.model.db.DbRecords;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

@Named
@Transactional
@ApplicationScoped
public class CheckDroneBatteryLevelLogServiceImpl implements CheckDroneBatteryLevelLogService {
    @Inject
    CheckDroneBatteryLevelLogRepositoryImpl checkDroneBatteryLevelLogRepository;

    @Override
    public void save(List<DbRecords.CheckDroneBatteryLevelLog> logs) {
        checkDroneBatteryLevelLogRepository.persist(logs);
    }

    @Override
    public List<DbRecords.CheckDroneBatteryLevelLog> getAll() {
        return checkDroneBatteryLevelLogRepository.listAll();
    }
}