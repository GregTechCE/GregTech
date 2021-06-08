package gregtech.api.capability;

import net.minecraft.util.text.ITextComponent;

/**
 * Context used during configuration events
 */
public interface ConfigurationContext {

    // For feedback
    void sendMessage(ITextComponent component);

    // TODO advanced behaviour enabling (boolean, tier based?)

    // TODO inventory access for magic stick behaviour
}