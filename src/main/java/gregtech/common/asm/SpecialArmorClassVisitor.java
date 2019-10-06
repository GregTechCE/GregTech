package gregtech.common.asm;

import jdk.internal.org.objectweb.asm.Opcodes;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import java.util.function.Function;

public class SpecialArmorClassVisitor extends TargetClassVisitor {

    public static final String CACHED_TOUGHNESS_FIELD_NAME = "gregtech__cachedToughness";
    public static final String CACHED_TOTAL_ARMOR_FIELD_NAME = "gregtech__cachedTotalArmor";

    public SpecialArmorClassVisitor(ClassVisitor cv, String methodKey, Function<MethodVisitor, MethodVisitor> visitorCreator) {
        super(cv, methodKey, visitorCreator);
    }

    @Override
    public void visitEnd() {
        visitField(Opcodes.ACC_STATIC | Opcodes.ACC_PRIVATE, CACHED_TOUGHNESS_FIELD_NAME, "F", null, null);
        visitField(Opcodes.ACC_STATIC | Opcodes.ACC_PRIVATE, CACHED_TOTAL_ARMOR_FIELD_NAME, "F", null, null);
        super.visitEnd();
    }
}
