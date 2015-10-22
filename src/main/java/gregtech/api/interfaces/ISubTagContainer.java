package gregtech.api.interfaces;

import gregtech.api.enums.SubTag;

public interface ISubTagContainer {
    /**
     * @return if the Tag is inside the List.
     */
    public boolean contains(SubTag aTag);

    /**
     * @return The ISubTagContainer you called this Function on, for convenience.
     */
    public ISubTagContainer add(SubTag... aTags);

    /**
     * @return if the Tag was there before it has been removed.
     */
    public boolean remove(SubTag aTag);
}