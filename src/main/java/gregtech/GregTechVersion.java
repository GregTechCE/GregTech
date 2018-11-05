package gregtech;

public final class GregTechVersion {

    //This number is incremented every major feature update, and reset every minecraft release
    public static final int MAJOR = 1;
    //This number is incremented every time the feature is added, or bug is fixed. resets every major version change
    public static final int MINOR = 0;
    //This number is incremented every build, and never reset. Should always be 0 in the repo code.
    public static final int BUILD = 0;

    private GregTechVersion() {
    }
}
