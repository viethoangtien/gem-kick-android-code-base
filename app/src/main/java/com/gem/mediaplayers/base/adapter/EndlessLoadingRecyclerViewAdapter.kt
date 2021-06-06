package com.gem.mediaplayers.base.adapter

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessLoadingRecyclerViewAdapter<E, V : BaseViewHolder<E>> :
    BaseAdapter<E, V> {

    private var loadingMoreListener: (() -> Unit)? = null
    private var disableLoadMore = false
    private var isLoading = false
    protected var recyclerView: RecyclerView? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, onItemClickListener: OnItemClickListener?) : super(
        context,
        onItemClickListener
    )

    fun setLoadingMoreListener(loadingMoreListener: () -> Unit) {
        this.loadingMoreListener = loadingMoreListener
        enableLoadingMore(true)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> {
                        var firstVisibleItemPosition = 0
                        var lastVisibleItemPosition = 0
                        if (disableLoadMore || isLoading) {
                            return
                        }
                        val layoutManager = recyclerView.layoutManager
                        if (layoutManager is LinearLayoutManager) {
                            firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                            lastVisibleItemPosition =
                                layoutManager.findLastCompletelyVisibleItemPosition()
                        } else if (layoutManager is GridLayoutManager) {
                            firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                            lastVisibleItemPosition =
                                layoutManager.findLastCompletelyVisibleItemPosition()
                        }
                        if (firstVisibleItemPosition > 0 && lastVisibleItemPosition == itemCount - 1) {
                            isLoading = true
                            loadingMoreListener?.invoke()
                        }
                    }
                    else -> {
                    }
                }
            }
        })
    }

    fun setIsLoading(isLoading: Boolean) {
        this.isLoading = isLoading
    }

    fun getIsLoading() = isLoading

    fun enableLoadingMore(enable: Boolean) {
        this.disableLoadMore = !enable
    }
}