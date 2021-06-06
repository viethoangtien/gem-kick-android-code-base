package com.gem.mediaplayers.base.adapter

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gem.mediaplayers.R
import com.gem.mediaplayers.utils.extension.gone
import com.gem.mediaplayers.utils.extension.gridLayout
import com.gem.mediaplayers.utils.extension.visible
import kotlinx.android.synthetic.main.layout_base_recyclerview.view.*

class BaseRecyclerView : RelativeLayout {
    private var mAdapter: EndlessLoadingRecyclerViewAdapter<Any, *>? = null
    var isLoadingMore = false
    private var enableTextEmpty = false

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_base_recyclerview, this, true)
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setParams(attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        setParams(attrs)
    }

    private fun setParams(attrs: AttributeSet) {
        val a =
            context!!.theme.obtainStyledAttributes(attrs, R.styleable.BaseRecyclerView, 0, 0)
        a.getDrawable(R.styleable.BaseRecyclerView_brv_background)?.let {
            rcv_data.background = it
        }
        enableTextEmpty = a.getBoolean(R.styleable.BaseRecyclerView_brv_enable_show_empty, false)
        setTextEmpty(a.getString(R.styleable.BaseRecyclerView_brv_text_empty))
        val padding = a.getDimension(R.styleable.BaseRecyclerView_brv_padding, 0f)
        if (padding != 0f) {
            rcv_data.setPadding(padding.toInt(), padding.toInt(), padding.toInt(), padding.toInt())
        } else {
            val paddingStart =
                a.getDimension(R.styleable.BaseRecyclerView_brv_padding_start, 0f)
            val paddingEnd =
                a.getDimension(R.styleable.BaseRecyclerView_brv_padding_end, 0f)
            val paddingTop =
                a.getDimension(R.styleable.BaseRecyclerView_brv_padding_top, 0f)
            val paddingBottom =
                a.getDimension(R.styleable.BaseRecyclerView_brv_padding_bottom, 0f)
            rcv_data.setPadding(
                paddingStart.toInt(),
                paddingTop.toInt(),
                paddingEnd.toInt(),
                paddingBottom.toInt()
            )
        }
        val enableRefresh =
            a.getBoolean(R.styleable.BaseRecyclerView_brv_enable_refresh, true)
        swipeRefresh.isEnabled = enableRefresh
        swipeRefresh.setColorSchemeColors(
            ContextCompat.getColor(context!!, R.color.colorPrimary),
            ContextCompat.getColor(context!!, R.color.colorPrimaryDark)
        )
        val isClipToPadding = a.getBoolean(R.styleable.BaseRecyclerView_brv_clip_padding, false)
        rcv_data.clipToPadding = isClipToPadding
        a.recycle()
    }

    private fun setTextEmpty(textEmpty: String?) {
        textEmpty?.let {
            tv_no_result.text = it
        }
    }

    fun setEmptyTextStyle(
        @StringRes textRes: Int = R.string.no_contents_found,
        @ColorRes colorRes: Int = R.color.md_black_1000,
        @DimenRes textsizeRes: Int = R.dimen.textsize_16_sp
    ) {
        tv_no_result.apply {
            text = context!!.resources.getString(textRes)
            setTextColor(ContextCompat.getColor(context!!, colorRes))
            setTextSize(TypedValue.COMPLEX_UNIT_PX, context.resources.getDimension(textsizeRes))
        }
    }

    fun enableRefresh(enable: Boolean) {
        swipeRefresh.isEnabled = enable
    }

    fun enableLoadMore(enableLoadMore: Boolean) {
        mAdapter?.enableLoadingMore(enableLoadMore)
    }

    fun setRefreshing(refreshing: Boolean) {
        swipeRefresh.isRefreshing = refreshing
    }

    fun setListLayoutManager(orientation: Int = RecyclerView.VERTICAL) {
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context, orientation, false)
        rcv_data.layoutManager = layoutManager
    }

    fun setListLayoutManager(layoutManager: LinearLayoutManager) {
        rcv_data.layoutManager = layoutManager
    }

    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        rcv_data.layoutManager = layoutManager
    }

    fun setGridLayoutManager(spanCount: Int) {
        rcv_data.gridLayout(context, spanCount)
    }

    fun addItemDecoration(itemDecoration: RecyclerView.ItemDecoration) {
        rcv_data.addItemDecoration(itemDecoration)
    }

    fun updateData(data: List<Any>?) {
        if (data != null) {
            if (!isLoadingMore) {
                refresh(data)
                isLoadingMore = true
            } else {
                addItems(data)
            }
        }
        tv_no_result.visibility = if (mAdapter?.items.isNullOrEmpty()) VISIBLE else GONE
    }

    fun refresh(data: List<Any>) {
        mAdapter?.setIsLoading(false)
        mAdapter?.refreshLists(data)
        swipeRefresh.isRefreshing = false
        enableLoadMore(true)
        if (data.isEmpty() && enableTextEmpty) {
            tv_no_result.visible()
        } else {
            tv_no_result.gone()
        }
    }

    fun addItems(data: List<Any>) {
        mAdapter?.setIsLoading(false)
        mAdapter?.addItems(data)
        if (data.isEmpty()) {
            enableLoadMore(false)
        } else {
            enableLoadMore(true)
        }
    }

    fun setOnRefreshListener(func: () -> Unit) {
        swipeRefresh.setOnRefreshListener {
            isLoadingMore = false
            func.invoke()
        }
        swipeRefresh.isRefreshing = false
    }

    fun setOnLoadingMoreListener(loadingMoreListener: () -> Unit) {
        isLoadingMore = true
        mAdapter?.setLoadingMoreListener(loadingMoreListener)
    }

    fun setOnItemClickListener(onItemClickListener: BaseAdapter.OnItemClickListener) {
        mAdapter?.setOnItemClick(onItemClickListener)
    }

    fun setAdapter(adapter: EndlessLoadingRecyclerViewAdapter<*, *>?) {
        mAdapter = adapter as EndlessLoadingRecyclerViewAdapter<Any, *>?
        rcv_data.adapter = adapter
    }

    fun smoothScrollToPosition(position: Int) {
        rcv_data.smoothScrollToPosition(position)
    }

    fun setRecyclerViewOverScrollMode(overScrollMode: Int) {
        rcv_data.overScrollMode = overScrollMode
    }

}