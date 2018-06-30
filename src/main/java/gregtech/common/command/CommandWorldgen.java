package gregtech.common.command;

import gregtech.common.command.worldgen.CommandWorldgenReload;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;

public class CommandWorldgen extends CommandTreeBase {

    public CommandWorldgen() {
        addSubcommand(new CommandWorldgenReload());
    }

    @Override
    public String getName() {
        return "worldgen";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "gregtech.command.worldgen.usage";
    }
}
