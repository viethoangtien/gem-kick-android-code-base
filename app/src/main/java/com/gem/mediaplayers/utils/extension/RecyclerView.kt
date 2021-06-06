package com.gem.mediaplayers.utils.extension

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.gridLayout(context: Context, spanCount: Int = 2) {
    layoutManager = GridLayoutManager(context, spanCount)
}

fun RecyclerView.linearLayout(
    context: Context,
    orientation: Int = RecyclerView.VERTICAL,
    reverseLayout: Boolean = false
) {
    layoutManager = LinearLayoutManager(context, orientation, reverseLayout)
}

fun RecyclerView.linearLayoutReverse(
    context: Context,
    orientation: Int = RecyclerView.VERTICAL,
    reverseLayout: Boolean = false
) {
    val layoutManagers = LinearLayoutManager(context, orientation, reverseLayout)
    layoutManager = layoutManagers
    layoutManagers.stackFromEnd = true
}

fun RecyclerView.spanCount(number: Int) {
    (layoutManager as GridLayoutManager).spanCount = number
}

fun RecyclerView.removeItemDecorations() {
    while (itemDecorationCount > 0) {
        removeItemDecorationAt(0)
    }
}
