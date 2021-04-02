package gregtech.api.render;

import codechicken.lib.vec.uv.UV;
import codechicken.lib.vec.uv.UVTransformation;

public class UVMirror extends UVTransformation{
    public double minU;
    public double maxU;
    public double minV;
    public double maxV;

    public UVMirror(double minU, double maxU, double minV, double maxV) {
        this.minU = minU;
        this.maxU = maxU;
        this.minV = minV;
        this.maxV = maxV;
    }

    @Override
    public void apply(UV vec) {
        if (vec.u == minU) {
            vec.u = maxU;
        } else if (vec.u == maxU){
            vec.u = minU;
        }
        if (vec.v == minV) {
            vec.v = maxV;
        } else if (vec.v == maxV){
            vec.v = minV;
        }
    }

    @Override
    public UVTransformation inverse() {
        return null;
    }

}
