package org.drone.management.model.db;

import java.util.UUID;

public interface HasId {
    default UUID id() {
        return getId();
    }

    default UUID getId() {
        return id();
    }
}
