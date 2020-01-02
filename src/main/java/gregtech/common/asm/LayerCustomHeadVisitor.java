package gregtech.common.asm;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import net.minecraft.launchwrapper.Launch;

class LayerCustomHeadVisitor extends MethodVisitor {

    public static final String TARGET_CLASS_NAME = "net/minecraft/client/renderer/entity/layers/LayerCustomHead";
    public static final String TARGET_METHOD_NAME;

    private static final String METHOD_OWNER = "net/minecraft/client/renderer/ItemRenderer";
    private static final String METHOD_SIGNATURE = "(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V";
    private static final String METHOD_NAME_FULL_SRG = "a(Lvp;Laip;Lbwc$b;)V";
    private static final String METHOD_NAME_MCP = "renderItem";

    private static final String ARMOR_HOOKS_OWNER = "gregtech/api/items/armor/ArmorRenderHooks";
    private static final String ARMOR_HOOKS_SIGNATURE = "(Lnet/minecraft/entity/EntityLivingBase;)Z";
    private static final String ARMOR_HOOKS_METHOD_NAME = "shouldNotRenderHeadItem";

    public LayerCustomHeadVisitor(MethodVisitor mv) {
        super(Opcodes.ASM5, mv);
    }
    
    private boolean checkTargetInsn(int opcode, String owner, String name, String desc) {
        return (owner.equals(METHOD_OWNER) && desc.equals(METHOD_SIGNATURE) && name.equals(METHOD_NAME_MCP)) || (name + desc).equals(METHOD_NAME_FULL_SRG);
    }
    
    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
    	if (opcode == Opcodes.INVOKEVIRTUAL && checkTargetInsn(opcode, owner, name, desc)) {
            Label endLabel = new Label();
            Label skipLabel = new Label();
            super.visitVarInsn(Opcodes.ALOAD, 1); //load entity
            super.visitMethodInsn(Opcodes.INVOKESTATIC, ARMOR_HOOKS_OWNER, ARMOR_HOOKS_METHOD_NAME, ARMOR_HOOKS_SIGNATURE, false);
            super.visitJumpInsn(Opcodes.IFEQ, skipLabel);
            for (int i = 0; i < 4; i++) super.visitInsn(Opcodes.POP); //pop this, entity, stack, transformType
            super.visitJumpInsn(Opcodes.GOTO, endLabel);
            super.visitLabel(skipLabel);
            super.visitMethodInsn(opcode, owner, name, desc, itf);
            super.visitLabel(endLabel);
            return;
        }
        super.visitMethodInsn(opcode, owner, name, desc, itf);
    }
    
    static {
    	if ((boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment")) {
    		TARGET_METHOD_NAME = "doRenderLayer(Lnet/minecraft/entity/EntityLivingBase;FFFFFFF)V";
    	} else {
    		TARGET_METHOD_NAME = "a(Lvp;FFFFFFF)V";
    	}
    }
}
