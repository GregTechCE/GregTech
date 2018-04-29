package gregtech.api.items;

import gregtech.api.gui.IUIHolder;
import net.minecraft.util.EnumHand;

public class HandUIWrapper implements IUIHolder {

    public final EnumHand hand;

    public HandUIWrapper(EnumHand hand) {
        this.hand = hand;
    }

    @Override
    public void markAsDirty() {

    }
}
