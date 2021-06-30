package gregtech.common.asm;

import gregtech.common.asm.util.ObfMapping;
import gregtech.common.asm.util.SafeMethodVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class CompoundDataFixerGetVersionVisitor extends SafeMethodVisitor {

    public static final String TARGET_CLASS_NAME = "net/minecraftforge/common/util/CompoundDataFixer$1";
    public static final ObfMapping TARGET_METHOD = new ObfMapping(TARGET_CLASS_NAME, "getVersion", "(Ljava/lang/String;)I");

    private static final String WORLD_DATA_HOOKS_OWNER = "gregtech/common/datafix/fixes/metablockid/WorldDataHooks";
    private static final String WORLD_DATA_HOOKS_METHOD_NAME = "getFallbackModVersion";
    private static final String WORLD_DATA_HOOKS_SIGNATURE = "(Ljava/lang/String;)I";

    public CompoundDataFixerGetVersionVisitor(MethodVisitor mv) {
        super(Opcodes.ASM5, mv);
    }

    @Override
    public void visitInsn(int opcode) {
        if (opcode == Opcodes.ICONST_M1) {
            markPatchedSuccessfully();
            super.visitVarInsn(Opcodes.ALOAD, 1);
            super.visitMethodInsn(Opcodes.INVOKESTATIC, WORLD_DATA_HOOKS_OWNER,
                    WORLD_DATA_HOOKS_METHOD_NAME, WORLD_DATA_HOOKS_SIGNATURE, false);
        } else {
            super.visitInsn(opcode);
        }
    }

    @Override
    protected String getInjectTargetString() {
        return String.format("Patch target: %s; (point not found)", TARGET_METHOD);
    }

}
