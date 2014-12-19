package com.jacobbieker.exoplanets.eventbus;

/**
 * Created by Jacob on 7/16/2014.
 */
public class ItemSelectedEvent {

    public final ItemSelected mItemSelected;

    public ItemSelectedEvent(ItemSelected itemSelected) {
        mItemSelected = itemSelected;
    }

    public ItemSelected getItemSelected() {
        return mItemSelected;
    }

}
