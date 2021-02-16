package gregtech.api.capability.impl;

import gregtech.api.capability.IFuelInfo;

/**
 * Fuel information
 */
public abstract class AbstractFuelInfo implements IFuelInfo {

    private int fuelRemaining;
    private int fuelCapacity;
    private int fuelBurnTime;

    public AbstractFuelInfo(int fuelRemaining, int fuelCapacity, int fuelBurnTime) {
        this.fuelRemaining = fuelRemaining;
        this.fuelCapacity = fuelCapacity;
        this.fuelBurnTime = fuelBurnTime;
    }

    public int getFuelRemaining() {
        return fuelRemaining;
    }

    public int getFuelCapacity() {
        return fuelCapacity;
    }

    public int getFuelBurnTime() {
        return fuelBurnTime;
    }

    public void setFuelRemaining(int fuelRemaining) {
        this.fuelRemaining = fuelRemaining;
    }

    public void setFuelCapacity(int fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public void setFuelBurnTime(int fuelBurnTime) {
        this.fuelBurnTime = fuelBurnTime;
    }

    public void addFuelRemaining(int fuelRemaining) {
        this.fuelRemaining += fuelRemaining;
    }

    public void addFuelCapacity(int fuelCapacity) {
        this.fuelCapacity += fuelCapacity;
    }

    public void addFuelBurnTime(int fuelBurnTime) {
        this.fuelBurnTime += fuelBurnTime;
    }
}
