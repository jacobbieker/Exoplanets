package com.jacobbieker.exoplanets.eventbus;

/**
 * Created by Jacob on 7/17/2014.
 */
public class ItemSelected {

    private long mParentId;

    public ItemSelected(long parentId) {
        this.mParentId = parentId;
    }

    public long getParentId() {
        return mParentId;
    }



}
