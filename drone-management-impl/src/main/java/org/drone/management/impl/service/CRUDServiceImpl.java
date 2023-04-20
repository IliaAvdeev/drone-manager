package org.drone.management.impl.service;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import org.drone.management.api.service.CRUDService;
import org.drone.management.model.db.HasId;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

public abstract class CRUDServiceImpl<DomainObject extends HasId> implements CRUDService<DomainObject> {
    @Inject
    PanacheRepositoryBase<DomainObject, UUID> repository;

    @Override
    public DomainObject getById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public DomainObject save(DomainObject object) {
        repository.persist(object);
        return getById(object.id());
    }

    @Override
    public List<DomainObject> save(List<DomainObject> domainObjects) {
        repository.persist(domainObjects);
        List<UUID> ids = domainObjects.stream()
                .map(HasId::id)
                .toList();
        return getByIds(ids);
    }

    @Override
    public List<DomainObject> getAll() {
        return repository.listAll();
    }

    @Override
    public List<DomainObject> getByIds(List<UUID> ids) {
        return repository.list("id in ?1", ids);
    }
}