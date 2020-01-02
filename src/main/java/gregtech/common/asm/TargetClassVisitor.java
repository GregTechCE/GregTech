package gregtech.common.asm;

import net.minecraftforge.fml.common.FMLLog;
import org.apache.logging.log4j.Level;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.function.Function;

public class TargetClassVisitor extends ClassVisitor {

    private String className;
    private final String methodKey;
    private final Function<MethodVisitor, MethodVisitor> visitorCreator;
    private boolean foundMethod = false;

    public TargetClassVisitor(ClassVisitor cv, String methodKey, Function<MethodVisitor, MethodVisitor> visitorCreator) {
        super(Opcodes.ASM5, cv);
        this.methodKey = methodKey;
        this.visitorCreator = visitorCreator;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.className = name;
    }

    @SuppressWarnings("deprecation")
	@Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor visitor = super.visitMethod(access, name, desc, signature, exceptions);
        String methodKey = name + desc;
        if (this.methodKey.equals(methodKey)) {
            FMLLog.log("ArmorRenderTransformer", Level.INFO, "Patched method {%s} successfully", methodKey);
            this.foundMethod = true;
            return visitorCreator.apply(visitor);
        }
        return visitor;
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public void visitEnd() {
        super.visitEnd();
        if (!foundMethod) {
            FMLLog.log("ArmorRenderTransformer", Level.FATAL, "Failed to find method {%s} in {%s}.", methodKey, className);
        }
    }
}
