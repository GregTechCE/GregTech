package gregtech.api.util.watch;

import net.minecraftforge.fluids.capability.IFluidHandler;

public abstract class WatchedFluidHandler implements IFluidHandler {
    private Runnable onContentChanged;

    public void onContentChanged() {
        if (onContentChanged != null)
            onContentChanged.run();
    }

    public WatchedFluidHandler setOnContentChanged(Runnable onContentChanged) {
        this.onContentChanged = onContentChanged;
        return this;
    }
}
