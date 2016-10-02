package gregtech.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Map;

@IFMLLoadingPlugin.Name("GregtechASM")
@IFMLLoadingPlugin.SortingIndex(10001)
public class ASM implements IFMLLoadingPlugin, IClassTransformer {

    private static final String OBF_METHOD_NAME = "func_178125_b";
    private static final String BLOCK_RENDERER = "gregtech/common/render/GT_BlockRenderer";
    private static final String SHOULD_HOOK_DESC = "(Lnet/minecraft/block/state/IBlockState;)Z";
    private static final String HOOK_DESC = "(Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/client/renderer/block/model/IBakedModel;";

    @Override
    public byte[] transform(String s, String s1, byte[] bytes) {
        if(s1.equals("net.minecraft.client.renderer.BlockModelShapes")) {
            ClassReader classReader = new ClassReader(bytes);
            ClassNode classNode = new ClassNode();
            classReader.accept(classNode, 0);

            for(MethodNode method : classNode.methods) {
                if(method.name.equals("getModelForState") || method.name.equals(OBF_METHOD_NAME)) {
                    InsnList insnList = new InsnList();
                    // load block state argument
                    insnList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                    //shouldHook: pop IBlockState -> push boolean
                    insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, BLOCK_RENDERER, "shouldHook", SHOULD_HOOK_DESC, false));
                    LabelNode labelNode = new LabelNode();
                    //pop boolean and jump if 0
                    insnList.add(new JumpInsnNode(Opcodes.IFEQ, labelNode));
                    // load block state argument
                    insnList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                    //hook: pop IBlockState -> push IBakedModel
                    insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, BLOCK_RENDERER, "hook", HOOK_DESC, false));
                    //returnL pop IBakedModel
                    insnList.add(new InsnNode(Opcodes.ARETURN));
                    //normal execution
                    insnList.add(labelNode);
                    //inject before method code
                    method.instructions.insert(insnList);
                }
            }


            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            classNode.accept(classWriter);
            return classWriter.toByteArray();
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
    public void injectData(Map<String, Object> data) {}

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

}
