package gregtech.api.capability;

public interface IThrottle {

    public static final IThrottle FULL_THROTTLE = constantThrottle(100);

    public static IThrottle constantThrottle(final int percentage) {
        return () -> percentage;
    }

    int getThrottlePercentage();

    default double calculateConsumptionMultiplier() {
        return ((double) getThrottlePercentage()) / 100;
    }

    default double calculateDurationMultiplier() {
        return ((double) getThrottlePercentage()) / 100;
    }

    default double calculateOutputVoltageMultiplier() {
        return ((double) getThrottlePercentage()) / 100;
    }
}
