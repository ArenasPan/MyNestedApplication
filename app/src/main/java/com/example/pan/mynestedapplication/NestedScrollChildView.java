package com.example.pan.mynestedapplication;

import android.content.Context;
import android.support.v4.view.NestedScrollingChildHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.support.v4.view.NestedScrollingChild;

/**
 * Created by Pan on 16/11/5.
 */

public class NestedScrollChildView extends LinearLayout implements NestedScrollingChild {

    private NestedScrollingChildHelper childHelper;

    public NestedScrollChildView(Context context) {
        super(context);
    }

    public NestedScrollChildView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedScrollChildView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NestedScrollingChildHelper getChildHelper() {
        if (childHelper == null) {
            childHelper = new NestedScrollingChildHelper(this);
        }
        return childHelper;
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        getChildHelper().setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return getChildHelper().isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return getChildHelper().startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        getChildHelper().stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return getChildHelper().hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return getChildHelper().dispatchNestedScroll(dxConsumed,dyConsumed,dxUnconsumed,dyUnconsumed,offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return getChildHelper().dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return getChildHelper().dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return getChildHelper().dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
        }
        return true;
    }
}
