package org.drone.management.impl.mapper;

import java.util.List;

public interface DtoMapper<Dto, DomainObject> {
    Dto toDto(DomainObject domainObject);

    List<Dto> toDto(List<DomainObject> domainObject);

    DomainObject toDomainObject(Dto dto);

    List<DomainObject> toDomainObject(List<Dto> dtos);
}