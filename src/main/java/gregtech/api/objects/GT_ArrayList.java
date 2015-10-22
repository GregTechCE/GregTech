package gregtech.api.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class GT_ArrayList<E> extends ArrayList<E> {
    private static final long serialVersionUID = 1L;

    private final boolean mAllowNulls;

    public GT_ArrayList(boolean aAllowNulls, int aCapacity) {
        super(aCapacity);
        mAllowNulls = aAllowNulls;
    }

    public GT_ArrayList(boolean aAllowNulls, E... aArray) {
        super(Arrays.asList(aArray));
        mAllowNulls = aAllowNulls;
        if (!mAllowNulls) for (int i = 0; i < size(); i++) if (get(i) == null) remove(i--);
    }

    public GT_ArrayList(boolean aAllowNulls, Collection<? extends E> aList) {
        super(aList);
        mAllowNulls = aAllowNulls;
        if (!mAllowNulls) for (int i = 0; i < size(); i++) if (get(i) == null) remove(i--);
    }

    @Override
    public E set(int aIndex, E aElement) {
        if (mAllowNulls || aElement != null) return super.set(aIndex, aElement);
        return null;
    }

    @Override
    public boolean add(E aElement) {
        if (mAllowNulls || aElement != null) return super.add(aElement);
        return false;
    }

    @Override
    public void add(int aIndex, E aElement) {
        if (mAllowNulls || aElement != null) super.add(aIndex, aElement);
    }

    @Override
    public boolean addAll(Collection<? extends E> aList) {
        boolean rReturn = super.addAll(aList);
        if (!mAllowNulls) for (int i = 0; i < size(); i++) if (get(i) == null) remove(i--);
        return rReturn;
    }

    @Override
    public boolean addAll(int aIndex, Collection<? extends E> aList) {
        boolean rReturn = super.addAll(aIndex, aList);
        if (!mAllowNulls) for (int i = 0; i < size(); i++) if (get(i) == null) remove(i--);
        return rReturn;
    }
}