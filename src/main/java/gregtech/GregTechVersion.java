package gregtech;

public final class GregTechVersion {

    //This number is incremented every minecraft release, never reset
    public static final int MAJOR = 0;
    //This number is incremented every major feature update, and reset every minecraft release
    public static final int MINOR = 7;
    //This number is incremented every time the feature is added, or bug is fixed. resets every minor version change
    public static final int REVISION = 0;
    //This number is incremented every build, and never reset. Should always be 0 in the repo code.
    public static final int BUILD = 0;

    private GregTechVersion() {
    }
}
