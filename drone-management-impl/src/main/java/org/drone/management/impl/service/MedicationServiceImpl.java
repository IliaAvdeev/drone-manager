package org.drone.management.impl.service;

import org.drone.management.api.service.MedicationService;
import org.drone.management.model.db.DbRecords;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.transaction.Transactional;

@Named
@Transactional
@ApplicationScoped
public class MedicationServiceImpl extends CRUDServiceImpl<DbRecords.Medication> implements MedicationService {}