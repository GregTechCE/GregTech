package gregtech;

public final class GTVersion {

    //This number is incremented every major content change, never reset
    public static final int MAJOR = 1;
    //This number is incremented every minecraft release, never reset
    public static final int MINOR = 0;
    //This number is incremented every time new feature is added, and reset every Minecraft version
    public static final int REVISION = 0;
    //This number is incremented every build, and never reset. Should always be 0 in the repo code.
    public static final int BUILD = 0;

    private GTVersion() {
    }
}
