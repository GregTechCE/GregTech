package gregtech;

import gregtech.api.util.Version;

public final class GregTechVersion {

    public static final int MAJOR = 1;
    //This number is incremented every major feature update
    public static final int MINOR = 17;
    //This number is incremented every time the feature is added, or bug is fixed. resets every major version change
    public static final int REVISION = 0;
    //This number is incremented every build, and never reset. Should always be 0 in the repo code.
    public static final int BUILD = 0;

    public static final Version VERSION = new Version(MAJOR, MINOR, REVISION, BUILD);

    private GregTechVersion() {
    }
}
