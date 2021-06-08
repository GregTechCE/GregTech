package gregtech.api.capability.impl;

import gregtech.api.capability.ConfigurationContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;

public class PlayerConfigurationContext implements ConfigurationContext {

    private final EntityPlayer player;

    public PlayerConfigurationContext(final EntityPlayer player) {
        this.player = player;
    }

    @Override
    public void sendMessage(final ITextComponent component) {
        this.player.sendMessage(component);
    }
}