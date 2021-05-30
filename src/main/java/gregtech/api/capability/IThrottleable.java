package gregtech.api.capability;

public interface IThrottleable {

    IThrottle getThrottle();

    void setThrottle(IThrottle throttle);

    // Metrics
    long getRecipeOutputVoltage();
}
