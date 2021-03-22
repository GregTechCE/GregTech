package gregtech.common.datafix;

import net.minecraft.util.datafix.IFixType;

public enum GregTechFixType implements IFixType {

    ITEM_STACK_LIKE, // any compound tag that looks like an item stack
    CHUNK_SECTION // a vertical section of a chunk

}
