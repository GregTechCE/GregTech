package gregtech.api.interfaces.tileentity;

public interface IMachineBlockUpdateable {

    /**
     * The Machine Update, which is called when the Machine needs an Update of its Parts.
     * I suggest to wait 1-5 seconds before actually checking the Machine Parts.
     * RedstoneInMotion-Frames could for example cause Problems when you insta-check the Machine Parts.
     */
    void onMachineBlockUpdate();

}
