package gregtech.asm;

import javassist.*;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.Name("GregtechASM")
@IFMLLoadingPlugin.SortingIndex(10001)
public class ASM implements IFMLLoadingPlugin, IClassTransformer {

    private static ClassPool pool = ClassPool.getDefault();

    private static final String OBF_METHOD_NAME = "func_178125_b";
    private static boolean runtimeDeobfuscation = false;

    static {
        pool.appendClassPath(new LoaderClassPath(Thread.currentThread().getContextClassLoader()));
        pool.appendClassPath(new LoaderClassPath(ClassLoader.getSystemClassLoader()));
    }

    @Override
    public byte[] transform(String s, String s1, byte[] bytes) {
        if(s1.equals("net.minecraft.client.renderer.BlockModelShapes")) {
            pool.appendClassPath(new ByteArrayClassPath(s1, bytes));
            CtClass ctClass = pool.getOrNull(s1);
            System.out.println(s1);
            try {
                CtMethod method = ctClass.getDeclaredMethod(runtimeDeobfuscation ? OBF_METHOD_NAME : "getModelForState");
                method.insertBefore(
                        "if(gregtech.common.render.newblocks.BlockRenderer.shouldHook($1)) {" +
                        "return gregtech.common.render.newblocks.BlockRenderer.hook($1); }");
                return ctClass.toBytecode();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return bytes;
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[] {getClass().getName()};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        runtimeDeobfuscation = (Boolean) data.get("runtimeDeobfuscationEnabled");
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

}
