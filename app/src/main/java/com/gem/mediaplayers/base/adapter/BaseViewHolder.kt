package com.gem.mediaplayers.base.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<E>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bindView(item: E, position: Int)
}
