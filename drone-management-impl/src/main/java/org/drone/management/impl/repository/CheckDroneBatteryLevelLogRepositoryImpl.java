package org.drone.management.impl.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import org.drone.management.model.db.DbRecords;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.UUID;

@ApplicationScoped
@Named("checkDroneBatteryLevelLogRepository")
public class CheckDroneBatteryLevelLogRepositoryImpl implements PanacheRepositoryBase<DbRecords.CheckDroneBatteryLevelLog, UUID> {}