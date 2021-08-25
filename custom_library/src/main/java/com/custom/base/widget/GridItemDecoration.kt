package com.custom.base.widget

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.custom.base.expand.toPx

/**
 *  作者 : dao sanqing
 *  描述 : description
 *  日期 : 2021/7/21 4:42 下午
 */
class GridItemDecoration(private val unit: Int = 5.toPx()) : RecyclerView.ItemDecoration(){

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        when(parent.layoutManager){
            is GridLayoutManager-> equilibriumAssignmentOfGrid(outRect,view,parent)
            is LinearLayoutManager-> equilibriumAssignmentOfLinear(outRect,view,parent)
            else-> {

            }
        }
    }

    private fun equilibriumAssignmentOfGrid( outRect: Rect, view: View, parent: RecyclerView) {
        // item 的个数
        val itemCount = parent.getItemCount()
        // 网格布局的跨度数
        val spanCount = parent.getSpanCount()
        // 当前 item 的 position
        val itemPosition = parent.getChildAdapterPosition(view)
        val layoutManager = parent.checkGridLayoutManager() ?: return
        if (spanCount < 2) {
            equilibriumAssignmentOfLinear(view = view, parent = parent, outRect = outRect)
            return
        }
        // 获取 GridLayoutManager 的布局方向
        val orientation = layoutManager.orientation
        if (orientation == RecyclerView.HORIZONTAL) {
            // 暂不支持横向布局的 GridLayoutManager
            throw UnsupportedOperationException("You can’t set a horizontal grid layout because we don’t support！")
        }
        // 遍历所有 item
        for (index in 0..itemCount) {
            when {
                // 最左边的那一列
                itemPosition % spanCount == 0 -> {
                    outRect.left = unit * 2
                    outRect.right = unit
                }
                // 最右边的那一列
                (itemPosition - (spanCount - 1)) % spanCount == 0 -> {
                    outRect.left = unit
                    outRect.right = unit * 2
                }
                // 中间的列（可能有多列）
                else -> {
                    outRect.left = unit
                    outRect.right = unit
                }
            }
            outRect.top = unit * 2
            // 判断是否为最后一行，最后一行单独添加底部的间距
            if (itemPosition in (itemCount - spanCount) until itemCount) {
                outRect.bottom = unit * 2
            }
        }
    }

    private fun equilibriumAssignmentOfLinear(outRect: Rect, view: View, parent: RecyclerView) {
        // item 的个数
        val itemCount = parent.getItemCount()
        // 当前 item 的 position
        val itemPosition = parent.getChildAdapterPosition(view)
        val layoutManager = parent.checkLinearLayoutManager() ?: return
        // 获取 LinearLayoutManager 的布局方向
        val orientation = layoutManager.orientation
        // 遍历所有 item
        for (index in 0..itemCount) {
            when (itemPosition) {
                // 第一行/列
                0 -> {
                    if (orientation == RecyclerView.VERTICAL) {
                        // 第一行/列 && VERTICAL 布局方式 -> 对item的底部特殊处理
                        outRect.top = unit * 2
                        outRect.bottom = unit
                    } else {
                        // 第一行/列 && HORIZONTAL 布局方式 -> 对item的右边特殊处理
                        outRect.top = unit * 2
                        outRect.bottom = unit * 2
                        outRect.left = unit * 2
                        outRect.right = unit
                    }
                }
                // 最后一行/列
                itemCount - 1 -> {
                    if (orientation == RecyclerView.VERTICAL) {
                        // 最后一行/列 && VERTICAL 布局方式 -> 对item的顶部特殊处理
                        outRect.top = unit
                        outRect.bottom = unit * 2
                    } else {
                        // 最后一行/列 && HORIZONTAL 布局方式 -> 对item的左边特殊处理
                        outRect.top = unit * 2
                        outRect.bottom = unit * 2
                        outRect.left = unit
                        outRect.right = unit * 2
                    }
                }
                // 中间的行/列
                else -> {
                    if (orientation == RecyclerView.VERTICAL) {
                        // 中间的行/列 && VERTICAL 布局方式 -> 对item的顶部和底部特殊处理
                        outRect.top = unit
                        outRect.bottom = unit
                    } else {
                        // 中间的行/列 && HORIZONTAL 布局方式 -> 对item的左边和右边特殊处理
                        outRect.top = unit * 2
                        outRect.bottom = unit * 2
                        outRect.left = unit
                        outRect.right = unit
                    }
                }
            }
        }
    }

    /**
     * 获取 spanCount
     * 注：此方法只针对设置 LayoutManager 为 GridLayoutManager 的 RecyclerView 生效
     */
    fun RecyclerView.getSpanCount(): Int {
        val layoutManager = checkGridLayoutManager() ?: return 0
        return layoutManager.spanCount
    }

    /**
     * 返回绑定到父 RecyclerView 的适配器中的项目数
     */
    fun RecyclerView.getItemCount(): Int {
        val layoutManager = layoutManager ?: return 0
        return layoutManager.itemCount
    }

    /**
     * 检查 RecyclerView 设置的 LinearLayoutManager
     */
    private fun RecyclerView.checkLinearLayoutManager(): LinearLayoutManager? {
        val layoutManager = layoutManager ?: return null
        if (layoutManager !is LinearLayoutManager) {
            throw IllegalStateException("Make sure you are using the LinearLayoutManager！")
        }
        return layoutManager
    }

    private fun RecyclerView.checkGridLayoutManager(): GridLayoutManager? {
        val layoutManager = layoutManager ?: return null
        if (layoutManager !is GridLayoutManager) {
            throw IllegalStateException("Make sure you are using the GridLayoutManager！")
        }
        return layoutManager
    }

}