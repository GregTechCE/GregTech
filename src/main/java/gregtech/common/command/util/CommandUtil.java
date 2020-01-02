package gregtech.common.command.util;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;

public class CommandUtil extends CommandTreeBase {

    public CommandUtil() {
        addSubcommand(new CommandHand());
    }

    @Override
    public String getName() {
        return "util";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "gregtech.command.util.usage";
    }
}
