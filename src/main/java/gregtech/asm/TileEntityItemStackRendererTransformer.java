package gregtech.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;

public class TileEntityItemStackRendererTransformer implements IClassTransformer, Opcodes {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if(transformedName.equals("net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer")) {
            GregtechPreloader.logger.info("Found target class: {}", transformedName);
            ClassReader classReader = new ClassReader(basicClass);
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            classReader.accept(new TEISRClassVisitor(classWriter), 0);
            return classWriter.toByteArray();
        }
        return basicClass;
    }

    private static class TEISRClassVisitor extends ClassVisitor {
        public TEISRClassVisitor(ClassVisitor cv) {
            super(ASM5, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor visitor = super.visitMethod(access, name, desc, signature, exceptions);
            if(desc.equals("(Lnet/minecraft/item/ItemStack;F)V")) {
                GregtechPreloader.logger.info("Found target method: {}{}", name, desc);
                visitor = new RenderItemVisitor(visitor);
            }
            return visitor;
        }
    }

    private static class RenderItemVisitor extends MethodVisitor {
        public RenderItemVisitor(MethodVisitor mv) {
            super(ASM5, mv);
        }

        @Override
        public void visitCode() {
            super.visitCode();
            super.visitVarInsn(ALOAD, 1);
            super.visitVarInsn(FLOAD, 2);
            super.visitMethodInsn(INVOKESTATIC, "gregtech/asm/GTHooksClient",
                "renderTileEntityItem", "(Lnet/minecraft/item/ItemStack;F)Z", false);
            super.visitInsn(ICONST_1);
            Label skipLabel = new Label();
            super.visitJumpInsn(IFNE, skipLabel); //if not true, continue,
            super.visitInsn(RETURN);
            super.visitLabel(skipLabel);
        }
    }
}
