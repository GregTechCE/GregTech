package gregtech.api.unification.material.type;

import com.google.common.collect.ImmutableList;
import gregtech.api.unification.Elements;
import gregtech.api.unification.material.IMaterial;
import gregtech.api.unification.stack.MaterialStack;

public abstract class SimpleMaterial <T extends SimpleMaterial<T>> implements IMaterial<T> {

    public final int materialRGB;
    private String chemicalFormula;
    public final ImmutableList<MaterialStack> materialComponents;
    private long materialGenerationFlags;

    public SimpleMaterial(int materialRGB, ImmutableList<MaterialStack> materialComponents, long flags) {
        this.materialRGB = materialRGB;
        this.materialComponents = materialComponents;
        this.chemicalFormula = calculateChemicalFormula();
        this.materialGenerationFlags = flags;
    }

    private String calculateChemicalFormula() {
        if (!materialComponents.isEmpty()) {
            StringBuilder components = new StringBuilder();
            for (MaterialStack component : materialComponents)
                components.append(component.toString());
            return components.toString();
        }
        return "";
    }

    public T setFormula(String formula) {
        this.chemicalFormula = formula;
        return (T)this;
    }

    @Override
    public String getChemicalFormula() {
        return chemicalFormula;
    }

    @Override
    public ImmutableList<MaterialStack> getMaterialComponents() {
        return materialComponents;
    }

    @Override
    public boolean hasFlag(long flag) {
        return (materialGenerationFlags & flag) >= flag;
    }

    @Override
    public long getAverageMass() {
        if (materialComponents.size() <= 0)
            return Elements.get("Neutronium").getMass();
        long totalMass = 0, totalAmount = 0;
        for (MaterialStack material : materialComponents) {
            totalAmount += material.amount;
            totalMass += material.amount * material.material.getAverageMass();
        }
        return totalMass / totalAmount;
    }

    @Override
    public long getAverageNeutrons() {
        if (materialComponents.isEmpty())
            return Elements.get("Neutronium").getNeutrons();
        long totalNeutrons = 0, totalAmount = 0;
        for (MaterialStack material : materialComponents) {
            totalAmount += material.amount;
            totalNeutrons += material.amount * material.material.getAverageNeutrons();
        }
        return totalNeutrons / totalAmount;
    }

    @Override
    public long getAverageProtons() {
        if (materialComponents.isEmpty())
            return Math.max(1, Elements.get("Neutronium").getProtons());
        long totalProtons = 0, totalAmount = 0;
        for (MaterialStack material : materialComponents) {
            totalAmount += material.amount;
            totalProtons += material.amount * material.material.getAverageProtons();
        }
        return totalProtons / totalAmount;
    }

    @Override
    public long getMass() {
        if (materialComponents.isEmpty())
            return Elements.get("Neutronium").getMass();
        long totalMass = 0;
        for (MaterialStack material : materialComponents) {
            totalMass += material.amount * material.material.getMass();
        }
        return totalMass;
    }

    @Override
    public long getNeutrons() {
        if (materialComponents.isEmpty())
            return Elements.get("Neutronium").getNeutrons();
        long totalNeutrons = 0;
        for (MaterialStack material : materialComponents) {
            totalNeutrons += material.amount * material.material.getNeutrons();
        }
        return totalNeutrons;
    }

    @Override
    public long getProtons() {
        if (materialComponents.isEmpty())
            return Elements.get("Neutronium").getProtons();
        long totalProtons = 0;
        for (MaterialStack material : materialComponents) {
            totalProtons += material.amount * material.material.getProtons();
        }
        return totalProtons;
    }

    @Override
    public boolean isRadioactive() {
        for (MaterialStack material : materialComponents)
            if (material.material.isRadioactive()) return true;
        return false;
    }
}
