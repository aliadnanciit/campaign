package de.westwing.campaignbrowser.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemVerticalSpaceDecoration(private val verticalSpaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        parent.adapter?.let{
            if(parent.getChildLayoutPosition(view) < it.itemCount - 1) {
                outRect.bottom = verticalSpaceHeight
            }
        }
    }
}

class ItemHorizontalSpaceDecoration(private val horizontalSpaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        parent.adapter?.let{
            if(parent.getChildLayoutPosition(view) < it.itemCount - 1) {
                outRect.right = horizontalSpaceHeight
            }
        }
    }
}