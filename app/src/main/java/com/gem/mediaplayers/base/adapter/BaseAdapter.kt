package com.gem.mediaplayers.base.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import java.util.*

abstract class BaseAdapter<E, V : BaseViewHolder<E>> : RecyclerView.Adapter<V> {
    var type: Int? = 0
    var mItems: MutableList<E>
    protected var context: Context? = null
        private set
    var onItemClickListener: OnItemClickListener? = null

    val items: List<E>
        get() = mItems

    constructor(mItems: MutableList<E>, mContext: Context) {
        this.mItems = mItems
        this.context = mContext
    }

    constructor(
        mItems: MutableList<E>,
        mContext: Context,
        mOnItemClickListener: OnItemClickListener
    ) {
        this.mItems = mItems
        this.context = mContext
        this.onItemClickListener = mOnItemClickListener
    }

    constructor(context: Context) {
        this.context = context
        mItems = ArrayList()
    }

    constructor(context: Context, onItemClickListener: OnItemClickListener?) {
        this.context = context
        this.onItemClickListener = onItemClickListener
        mItems = ArrayList()
    }

    constructor(context: Context, onItemClickListener: OnItemClickListener?, type: Int) {
        this.context = context
        this.onItemClickListener = onItemClickListener
        this.type = type
        mItems = ArrayList()
    }

    fun setOnItemClick(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun addItems(items: List<E>) {
        val previousSize = mItems.size
        mItems.addAll(previousSize, items)
        notifyItemRangeInserted(previousSize, items.size)
    }

    fun addItem(item: E) {
        mItems.add(item)
        notifyDataSetChanged()
    }

    fun addItemAtPosition(item: E, position: Int) {
        if (position < 0) return

        if (position > itemCount - 1) {
            mItems.add(item)
        } else {
            mItems.add(position, item)
        }
        notifyItemRangeInserted(position, mItems.size - position + 1)
        notifyDataSetChanged()
    }

    fun replaceItemAtPosition(item: E, position: Int) {
        if (position in 0 until itemCount) {
            mItems[position] = item
            notifyItemRangeChanged(position, 1)
            notifyDataSetChanged()
        }
    }

    fun removeItemAtPosition(position: Int) {
        if (position in 0 until itemCount) {
            mItems.removeAt(position)
            notifyItemRangeChanged(position, mItems.size)
            notifyDataSetChanged()
        }
    }

    fun updateItemAtPosition(item: E, position: Int) {
        if (position in 0 until itemCount) {
            mItems[position] = item
            notifyItemRangeChanged(position, mItems.size)
            notifyDataSetChanged()
        }
    }

    fun refreshLists(items: List<E>?) {
        mItems.clear()
        if (!items.isNullOrEmpty()) mItems.addAll(items)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): E {
        return mItems[position]
    }

    fun clear() {
        mItems.clear()
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, item: Any?)
    }
}
