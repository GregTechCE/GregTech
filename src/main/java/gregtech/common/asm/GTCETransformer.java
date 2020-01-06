package gregtech.common.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import gregtech.common.asm.util.*;

public class GTCETransformer implements IClassTransformer, Opcodes {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        String internalName = transformedName.replace('.', '/');
        if (internalName.equals(LayerArmorBaseVisitor.TARGET_CLASS_NAME)) {
            ClassReader classReader = new ClassReader(basicClass);
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            classReader.accept(new TargetClassVisitor(classWriter, LayerArmorBaseVisitor.TARGET_METHOD, LayerArmorBaseVisitor::new), 0);
            return classWriter.toByteArray();
        }
        if (internalName.equals(LayerCustomHeadVisitor.TARGET_CLASS_NAME)) {
            ClassReader classReader = new ClassReader(basicClass);
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            classReader.accept(new TargetClassVisitor(classWriter, LayerCustomHeadVisitor.TARGET_METHOD, LayerCustomHeadVisitor::new), 0);
            return classWriter.toByteArray();
        }
        if (internalName.equals(SpecialArmorApplyVisitor.TARGET_CLASS_NAME)) {
            ClassReader classReader = new ClassReader(basicClass);
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            classReader.accept(new SpecialArmorClassVisitor(classWriter, SpecialArmorApplyVisitor.TARGET_METHOD, SpecialArmorApplyVisitor::new), 0);
            return classWriter.toByteArray();
        }
        return basicClass;
    }
}
