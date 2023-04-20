package org.drone.management.model.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "drone")
public class Drone implements HasId {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "serial_number")
    private String serialNumber;
    private String model;
    @Column(name = "weight_limit")
    private Integer weightLimit;
    @Column(name = "battery_capacity")
    private Integer batteryCapacity;
    private String state;

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(Integer weightLimit) {
        this.weightLimit = weightLimit;
    }

    public Integer getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(Integer batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drone drone = (Drone) o;
        return Objects.equals(id, drone.id) && Objects.equals(serialNumber, drone.serialNumber) && Objects.equals(model, drone.model) && Objects.equals(weightLimit, drone.weightLimit) && Objects.equals(batteryCapacity, drone.batteryCapacity) && Objects.equals(state, drone.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serialNumber, model, weightLimit, batteryCapacity, state);
    }
}