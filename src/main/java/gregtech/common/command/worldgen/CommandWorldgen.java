package gregtech.common.command.worldgen;

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
