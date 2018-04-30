package gregtech.common.command;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;

public class GregTechCommand extends CommandTreeBase {

    @Override
    public String getName() {
        return "gt";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands.gt.usage";
    }
}
