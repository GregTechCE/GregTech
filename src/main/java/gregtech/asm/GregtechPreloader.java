package gregtech.asm;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.Name;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.SortingIndex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.Map;

@Name("GregtechPreloader")
@SortingIndex(1001)
public class GregtechPreloader implements IFMLLoadingPlugin {

    public static final Logger logger = LogManager.getLogger(GregtechPreloader.class);

    @Override
    public String[] getASMTransformerClass() {
        return new String[] {TileEntityItemStackRendererTransformer.class.getName()};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
