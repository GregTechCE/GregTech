package gregtech.api.interfaces;

import gregtech.api.enums.SubTag;

public interface ISubTagContainer {

    /**
     * @return if the Tag is inside the List.
     */
    boolean contains(SubTag tag);


}