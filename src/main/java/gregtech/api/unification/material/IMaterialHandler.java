package gregtech.api.unification.material;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this to register your own materials in the right stage before material registry freezing
 * You should implement this interface and register your implementation by annotating it's class with
 * {@link gregtech.api.unification.material.IMaterialHandler.RegisterMaterialHandler}
 * That way GTCE will automatically pick up your implementation and register it.
 * Note that it should either have public no-arg constructor for class instantiation, or public static final INSTANCE field.
 * <p>
 * {@link IMaterialHandler#onMaterialsInit()} will be called by GTCE in PreInit just before
 * material registry freezing after registration of built-in materials and before
 * running early material-manipulating CraftTweaker scripts.
 */
public interface IMaterialHandler {

    void onMaterialsInit();

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface RegisterMaterialHandler {
    }

}
