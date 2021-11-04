package gregtech.api.terminal;

import gregtech.api.GTValues;
import gregtech.api.terminal.app.AbstractApplication;
import gregtech.api.terminal.hardware.Hardware;
import gregtech.api.terminal.util.GuideJsonLoader;
import gregtech.api.util.FileUtility;
import gregtech.api.util.GTLog;
import gregtech.common.ConfigHolder;
import gregtech.common.items.MetaItems;
import gregtech.common.terminal.app.appstore.AppStoreApp;
import gregtech.common.terminal.app.batterymanager.BatteryManagerApp;
import gregtech.common.terminal.app.console.ConsoleApp;
import gregtech.common.terminal.app.game.maze.MazeApp;
import gregtech.common.terminal.app.game.minesweeper.MinesweeperApp;
import gregtech.common.terminal.app.game.pong.PongApp;
import gregtech.common.terminal.app.guide.ItemGuideApp;
import gregtech.common.terminal.app.guide.MultiBlockGuideApp;
import gregtech.common.terminal.app.guide.SimpleMachineGuideApp;
import gregtech.common.terminal.app.guide.TutorialGuideApp;
import gregtech.common.terminal.app.guideeditor.GuideEditorApp;
import gregtech.common.terminal.app.hardwaremanager.HardwareManagerApp;
import gregtech.common.terminal.app.multiblockhelper.MultiBlockPreviewARApp;
import gregtech.common.terminal.app.prospector.OreProspectorApp;
import gregtech.common.terminal.app.recipechart.RecipeChartApp;
import gregtech.common.terminal.app.settings.SettingsApp;
import gregtech.common.terminal.app.worldprospector.WorldProspectorARApp;
import gregtech.common.terminal.hardware.BatteryHardware;
import gregtech.common.terminal.hardware.DeviceHardware;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class TerminalRegistry {
    protected static final Map<String, AbstractApplication> APP_REGISTER = new LinkedHashMap<>();
    protected static final Map<String, Hardware> HW_REGISTER = new LinkedHashMap<>();
    protected static final Map<String, List<Hardware>[]> APP_HW_DEMAND = new HashMap<>();
    protected static final Map<String, List<ItemStack>[]> APP_UPGRADE_CONDITIONS = new HashMap<>();
    protected static final List<String> DEFAULT_APPS = new ArrayList<>();
    @SideOnly(Side.CLIENT)
    public static File TERMINAL_PATH;

    static {
        if (FMLCommonHandler.instance().getSide().isClient()) {
            TERMINAL_PATH = new File(Loader.instance().getConfigDir(), ConfigHolder.U.clientConfig.terminalRootPath);
        }
    }

    public static void init() {
        // register hardware
        registerHardware(new BatteryHardware());
        int deviceSize = DeviceHardware.DEVICE.values().length;
        for (int i = 1; i < deviceSize; i++) {
            registerHardware(new DeviceHardware(i));
        }
        // register applications
        AppRegistryBuilder.create(new SimpleMachineGuideApp()).defaultApp().build();
        AppRegistryBuilder.create(new MultiBlockGuideApp()).defaultApp().build();
        AppRegistryBuilder.create(new ItemGuideApp()).defaultApp().build();
        AppRegistryBuilder.create(new TutorialGuideApp()).defaultApp().build();
        AppRegistryBuilder.create(new GuideEditorApp()).defaultApp().build();
        AppRegistryBuilder.create(new SettingsApp()).defaultApp().build();

        AppRegistryBuilder.create(new PongApp())
                .battery(GTValues.LV, 75)
                .build();
        AppRegistryBuilder.create(new MazeApp())
                .battery(GTValues.LV, 150)
                .build();
        AppRegistryBuilder.create(new MinesweeperApp())
                .battery(GTValues.LV, 150)
                .build();

        AppRegistryBuilder.create(new OreProspectorApp())
                .battery(GTValues.MV, 1000)
                .upgrade(MetaItems.COIN_DOGE.getStackForm(10))
                .upgrade(6, MetaItems.COIN_GOLD_ANCIENT.getStackForm())
                .device(DeviceHardware.DEVICE.SCANNER)
                .build();
        if (GTValues.isModLoaded(GTValues.MODID_JEI)) {
            AppRegistryBuilder.create(new MultiBlockPreviewARApp())
                    .battery(GTValues.LV, 512)
                    .device(DeviceHardware.DEVICE.CAMERA)
                    .upgrade(0, MetaItems.COIN_DOGE.getStackForm(10))
                    .upgrade(1, MetaItems.COIN_DOGE.getStackForm(30), MetaItems.COIN_CHOCOLATE.getStackForm(10))
                    .build();
            AppRegistryBuilder.create(new RecipeChartApp())
                    .battery(GTValues.LV, 100)
                    .upgrade(0, MetaItems.COIN_DOGE.getStackForm(10))
                    .upgrade(1, MetaItems.COIN_DOGE.getStackForm(20))
                    .upgrade(2, MetaItems.COIN_DOGE.getStackForm(30), MetaItems.COIN_CHOCOLATE.getStackForm(10))
                    .upgrade(3, MetaItems.COIN_DOGE.getStackForm(40), MetaItems.COIN_CHOCOLATE.getStackForm(10), MetaItems.COIN_GOLD_ANCIENT.getStackForm())
                    .build();
        }
        AppRegistryBuilder.create(new ConsoleApp())
                .battery(GTValues.LV, 500)
                .device(DeviceHardware.DEVICE.WIRELESS)
                .build();
        AppRegistryBuilder.create(new BatteryManagerApp()).defaultApp()
                .battery(GTValues.ULV, 0)
                .build();
        AppRegistryBuilder.create(new HardwareManagerApp()).defaultApp().build();
        AppRegistryBuilder.create(new AppStoreApp()).defaultApp().build();
        AppRegistryBuilder.create(new WorldProspectorARApp())
                .battery(GTValues.LV, 233)
                .device(DeviceHardware.DEVICE.CAMERA)
                .build();

        if (GTValues.isModLoaded(GTValues.MODID_CT)) { // handle CT register
            CTTerminalRegistry.register();
        }
    }

    @SideOnly(Side.CLIENT)
    public static void initTerminalFiles() {
        FileUtility.extractJarFiles(String.format("/assets/%s/%s", GTValues.MODID, "terminal"), TERMINAL_PATH, false);
        ((SimpleReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(new GuideJsonLoader());
    }

    public static void registerApp(AbstractApplication application) {
        String name = application.getRegistryName();
        if (APP_REGISTER.containsKey(name)) {
            GTLog.logger.warn("Duplicate APP registry names exist: {}", name);
            return;
        }
        APP_REGISTER.put(name, application);
    }

    public static void registerHardware(Hardware hardware) {
        String name = hardware.getRegistryName();
        if (APP_REGISTER.containsKey(name)) {
            GTLog.logger.warn("Duplicate APP registry names exist: {}", name);
            return;
        }
        HW_REGISTER.put(name, hardware);
    }

    public static void registerHardwareDemand(String name, boolean isDefaultApp, @Nonnull List<Hardware>[] hardware, @Nonnull List<ItemStack>[] upgrade) {
        if (name != null && APP_REGISTER.containsKey(name)) {
            if (isDefaultApp) {
                DEFAULT_APPS.add(name);
            }
            APP_HW_DEMAND.put(name, hardware);
            APP_UPGRADE_CONDITIONS.put(name, upgrade);
        } else {
            GTLog.logger.error("Not found the app {}", name);
        }
    }

    public static List<AbstractApplication> getDefaultApps() {
        return DEFAULT_APPS.stream().map(APP_REGISTER::get).collect(Collectors.toList());
    }

    public static Collection<AbstractApplication> getAllApps() {
        return APP_REGISTER.values();
    }

    public static AbstractApplication getApplication(String name) {
        return APP_REGISTER.get(name);
    }

    public static Collection<Hardware> getAllHardware() {
        return HW_REGISTER.values();
    }

    public static Hardware getHardware(String name) {
        return HW_REGISTER.get(name);
    }

    public static List<Hardware> getAppHardwareDemand(String name, int tier) {
        return APP_HW_DEMAND.get(name)[tier] != null ? APP_HW_DEMAND.get(name)[tier] : Collections.emptyList();
    }

    public static List<ItemStack> getAppHardwareUpgradeConditions(String name, int tier) {
        return APP_UPGRADE_CONDITIONS.get(name)[tier] != null ? APP_UPGRADE_CONDITIONS.get(name)[tier] : Collections.emptyList();
    }

    private static class AppRegistryBuilder {
        AbstractApplication app;
        boolean isDefaultApp;
        BatteryHardware[] battery;
        List<Hardware>[] hardware;
        List<ItemStack>[] upgrade;

        public static AppRegistryBuilder create(AbstractApplication app){
            AppRegistryBuilder builder = new AppRegistryBuilder();
            builder.app = app;
            builder.battery = new BatteryHardware[app.getMaxTier() + 1];
            builder.hardware = new List[app.getMaxTier() + 1];
            builder.upgrade = new List[app.getMaxTier() + 1];
            return builder;
        }

        public AppRegistryBuilder defaultApp(){
            this.isDefaultApp = true;
            return this;
        }

        public AppRegistryBuilder battery(int batteryTier, long cost) {
            BatteryHardware hw = new BatteryHardware.BatteryDemand(batteryTier, cost);
            for (int i = 0; i <= app.getMaxTier(); i++) {
                battery[i] = hw;
            }
            return this;
        }

        public AppRegistryBuilder battery(int tier, int batteryTier, long cost) {
            if (tier < battery.length) {
                battery[tier] = new BatteryHardware.BatteryDemand(batteryTier, cost);
            }
            return this;
        }

        public AppRegistryBuilder device(DeviceHardware.DEVICE... device) {
            Hardware[] hw = Arrays.stream(device).map(DeviceHardware.DeviceDemand::new).toArray(Hardware[]::new);
            for (int i = 0; i <= app.getMaxTier(); i++) {
                this.hardware(i, hw);
            }
            return this;
        }

        public AppRegistryBuilder device(int tier, DeviceHardware.DEVICE... device) {
            this.hardware(tier, Arrays.stream(device).map(DeviceHardware.DeviceDemand::new).toArray(Hardware[]::new));
            return this;
        }

        public AppRegistryBuilder hardware(Hardware... hardware) {
            for (int i = 0; i <= app.getMaxTier(); i++) {
                this.hardware(i, hardware);
            }
            return this;
        }

        public AppRegistryBuilder hardware(int tier, Hardware... hardware) {
            if (tier < this.hardware.length) {
                this.hardware[tier] = new LinkedList<>();
                for (Hardware hw : hardware) {
                    this.hardware[tier].add(hw);
                }
            }
            return this;
        }

        public AppRegistryBuilder appendHardware(int tier, Hardware... hardware) {
            if (tier < this.hardware.length) {
                if (this.hardware[tier] == null) {
                    this.hardware[tier] = new LinkedList<>();
                }
                for (Hardware hw : hardware) {
                    this.hardware[tier].add(hw);
                }
            }
            return this;
        }

        public AppRegistryBuilder upgrade(ItemStack... upgrade) {
            ItemStack[] up = Arrays.stream(upgrade).toArray(ItemStack[]::new);
            for (int i = 0; i <= app.getMaxTier(); i++) {
                this.upgrade(i, up);
            }
            return this;
        }

        public AppRegistryBuilder upgrade(int tier, ItemStack... upgrade) {
            if (tier < this.upgrade.length) {
                this.upgrade[tier] = new LinkedList<>();
                for (ItemStack up : upgrade) {
                    this.upgrade[tier].add(up);
                }
            }
            return this;
        }

        public void build() {
            TerminalRegistry.registerApp(app);
            for (int i = 0; i < hardware.length; i++) {
                if (battery[i] != null) {
                    if (hardware[i] == null) {
                        hardware[i] = new LinkedList<>();
                    }
                    hardware[i].add(battery[i]);
                }
            }
            TerminalRegistry.registerHardwareDemand(app.getRegistryName(), isDefaultApp, hardware, upgrade);
        }
    }
}
