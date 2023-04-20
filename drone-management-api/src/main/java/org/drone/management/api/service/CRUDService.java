package org.drone.management.api.service;

import java.util.List;
import java.util.UUID;

public interface CRUDService<DomainObject> {
    List<DomainObject> getAll();

    DomainObject getById(UUID id);

    List<DomainObject> getByIds(List<UUID> ids);

    DomainObject save(DomainObject object);

    List<DomainObject> save(List<DomainObject> objects);
}