package gregtech.api.enums.material;

import net.minecraftforge.fluids.FluidStack;

public interface FluidMaterial {

    final class MatFlags {

        /**
         * Add to fluid material to have it's cell generated
         */
        public static final int GENERATE_CELL = Material.MatFlags.createFlag(3);

        /**
         * Add to fluid material to have plasma fluid generated
         */
        public static final int GENERATE_PLASMA = Material.MatFlags.createFlag(4);



    }


    default boolean hasFluid() {
        return getFluid(1) != null;
    }

    default boolean hasPlasma() {
        return getPlasma(1) != null;
    }

    FluidStack getFluid(int amount);
    FluidStack getPlasma(int amount);

}
