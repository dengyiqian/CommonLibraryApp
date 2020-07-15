package org.dyq.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author： 邓益千
 * @Create by：   2020/5/14 13:59
 * @Description：
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    private int mOrientation;

    /**
     * item间距
     */
    private int spacing = 0;

    private Drawable mDivider;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    /**
     * @param orientation 方向
     * @param spacing     item间距
     */
    public void setOrientation(int orientation, int spacing) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("请设置方向：VERTICAL or HORIZONTAL");
        }
        this.spacing = spacing;
        this.mOrientation = orientation;
    }

    /**
     * @param orientation 方向
     * @param spacing     item间距
     */
    public DividerItemDecoration(int orientation, int spacing) {
        setOrientation(orientation, spacing);
    }


    /**
     * @param orientation 方向
     * @param spacing     item间距
     */
    public DividerItemDecoration(Context context, int orientation, int spacing) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(orientation, spacing);
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, spacing);
        } else {

            int spanCount = getSpanCount(parent);
            int childCount = parent.getAdapter().getItemCount();
            int itemPosition = parent.getChildAdapterPosition(view);
            //如果是第一列
            if (isFirstColum(parent,itemPosition,spanCount)){
                outRect.set((spacing / 2), 0, (spacing / 2), 0);

            // 如果是最后一行
            } else if (isLastRaw(parent, itemPosition, spanCount, childCount)) {
                outRect.set(0, 0, spacing, 0);

            // 如果是最后一列
            } else if (isLastColum(parent, itemPosition, spanCount)) {
                outRect.set(0, 0, (spacing / 2), spacing);
            } else {
                outRect.set(0, 0, (spacing / 2), spacing);
            }
        }

    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mDivider != null) {
            if (mOrientation == VERTICAL_LIST) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            childCount = childCount - childCount % spanCount;
            if (pos >= childCount)// 如果是最后一行
                return true;
        }
        return false;
    }

    private boolean isFirstColum(RecyclerView parent, int pos, int spanCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if ((pos + 1) % spanCount == 1) { // 如果是第一列
                return true;
            }
        }
        return false;
    }

    private boolean isLastColum(RecyclerView parent, int pos, int spanCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if ((pos + 1) % spanCount == 0) { // 如果是最后一列
                return true;
            }
        }
        return false;
    }

    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }
}
