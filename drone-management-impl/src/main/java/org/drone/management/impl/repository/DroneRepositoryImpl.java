package org.drone.management.impl.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import org.drone.management.model.db.DbRecords;
import org.drone.management.model.db.Drone;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.UUID;

@ApplicationScoped
@Named("droneRepository")
public class DroneRepositoryImpl implements PanacheRepositoryBase<Drone, UUID> {}