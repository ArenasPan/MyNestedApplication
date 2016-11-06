package com.example.pan.mynestedapplication;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Pan on 16/11/5.
 */

public class NestedScrollParentView extends LinearLayout implements NestedScrollingParent {

    private String Tag = "MyNestedScrollParent";

    private TextView tvHeader;
    private TextView tvHeaderSticky;
    private RecyclerView nestedScrollChildView;
    private int tvHeaderHeight;
    private int tvHeaderStickyHeight;
    private NestedScrollingParentHelper parentHelper;

    public NestedScrollingParentHelper getParentHelper() {
        if (parentHelper == null) {
            parentHelper = new NestedScrollingParentHelper(this);
        }
        return parentHelper;
    }

    public NestedScrollParentView(Context context) {
        super(context);
    }

    public NestedScrollParentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedScrollParentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvHeader = (TextView) findViewById(R.id.tv_header);
        tvHeaderSticky = (TextView) findViewById(R.id.tv_sticky_header);
        nestedScrollChildView = (RecyclerView) getChildAt(2);
        //在所有测量结束后获取高度
        tvHeader.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(tvHeaderHeight<=0){
                    tvHeaderHeight =  tvHeader.getMeasuredHeight();
                    Log.i(Tag,"tvHeaderHeight:"+tvHeaderHeight+",tvHeaderStickyHeight:"+tvHeaderStickyHeight);
                }
            }
        });
        tvHeaderSticky.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (tvHeaderStickyHeight <= 0) {
                    tvHeaderStickyHeight = tvHeaderSticky.getMeasuredHeight();
                    Log.i(Tag,"tvHeaderHeight:"+tvHeaderHeight+",tvHeaderStickyHeight:"+tvHeaderStickyHeight);
                }
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        tvHeaderHeight = tvHeader.getMeasuredHeight();
//        tvHeaderStickyHeight = tvHeaderSticky.getMeasuredHeight();
//        Log.i(Tag,"tvHeaderHeight:"+tvHeaderHeight+",tvHeaderStickyHeight:"+tvHeaderStickyHeight);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        if (nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL) {
            return true;
        }
        return false;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        getParentHelper().onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        getParentHelper().onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        boolean hiddenTop = (dy > 0 && getScrollY() < tvHeaderHeight); //表示上拉且向下滑动距离没有超过头部的高度
        // dy<0表示下拉
        //getScrollY() > 0表示当前的NestedScrollParentView还没有滑动到顶部
        //!ViewCompat.canScrollVertically(target, -1)表示不能再向上滑动了
        boolean showTop = (dy < 0 && getScrollY() > 0 && !ViewCompat.canScrollVertically(target, -1));

        //当hiddenTop或者showTop的值为true时,表示父view的stickyHeader也要一起滑动,所以父View要消费掉这个滑动距离
        //否则父view不滑动,stickyHeader停靠在屏幕顶端,子view滑动
        if (hiddenTop || showTop) {
            consumed[1] = dy;
            scrollBy(0, dy);
        }
        Log.i(Tag,"onNestedPreScroll--getScrollY():"+getScrollY()+",dx:"+dx+",dy:"+dy+",consumed:"+consumed[1]);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return 0;
    }

    /**
     * 重写scrollTo方法,避免一次滑动过大的距离导致stickyHeader滑出顶部
     * @param x
     * @param y
     */
    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > tvHeaderStickyHeight) {
            y = tvHeaderStickyHeight;
        }
        if (y != getScrollY())
        {
            super.scrollTo(x, y);
        }
    }
}
