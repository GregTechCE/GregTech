package gregtech.common.asm;

import gregtech.common.asm.util.ObfMapping;
import gregtech.common.asm.util.SafeMethodVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class SaveFormatOldLoadVisitor extends SafeMethodVisitor {

    public static final String TARGET_CLASS_NAME = "net/minecraft/world/storage/SaveFormatOld";
    public static final ObfMapping TARGET_METHOD = new ObfMapping(TARGET_CLASS_NAME, "loadAndFix", // forge method
            "(Ljava/io/File;Lnet/minecraft/util/datafix/DataFixer;Lnet/minecraft/world/storage/SaveHandler;)Lnet/minecraft/world/storage/WorldInfo;");

    private static final String LOAD_COMPRESSED_OWNER = "net/minecraft/nbt/CompressedStreamTools";
    private static final ObfMapping LOAD_COMPRESSED_METHOD = new ObfMapping(LOAD_COMPRESSED_OWNER, "func_74796_a",
            "(Ljava/io/InputStream;)Lnet/minecraft/nbt/NBTTagCompound;").toRuntime();

    private static final String WORLD_DATA_HOOKS_OWNER = "gregtech/common/datafix/fixes/metablockid/WorldDataHooks";
    private static final String WORLD_DATA_HOOKS_METHOD_NAME = "onWorldLoad";
    private static final String WORLD_DATA_HOOKS_SIGNATURE = "(Lnet/minecraft/world/storage/SaveHandler;Lnet/minecraft/nbt/NBTTagCompound;)V";

    private State state = State.WAITING_FOR_READ;

    public SaveFormatOldLoadVisitor(MethodVisitor mv) {
        super(Opcodes.ASM5, mv);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        super.visitMethodInsn(opcode, owner, name, desc, itf);
        if (state == State.WAITING_FOR_READ && opcode == Opcodes.INVOKESTATIC
                && owner.equals(LOAD_COMPRESSED_OWNER) && LOAD_COMPRESSED_METHOD.matches(name, desc)) {
            state = State.WAITING_FOR_VAR;
        }
    }

    @Override
    public void visitVarInsn(int opcode, int var) {
        super.visitVarInsn(opcode, var);
        if (state == State.WAITING_FOR_VAR && opcode == Opcodes.ASTORE) {
            state = State.DONE;
            markPatchedSuccessfully();
            super.visitVarInsn(Opcodes.ALOAD, 2);
            super.visitVarInsn(Opcodes.ALOAD, var);
            super.visitMethodInsn(Opcodes.INVOKESTATIC, WORLD_DATA_HOOKS_OWNER,
                    WORLD_DATA_HOOKS_METHOD_NAME, WORLD_DATA_HOOKS_SIGNATURE, false);
        }
    }

    @Override
    protected String getInjectTargetString() {
        return String.format("Patch target: %s; (point not found)", TARGET_METHOD);
    }

    private enum State {
        WAITING_FOR_READ, WAITING_FOR_VAR, DONE
    }

}
