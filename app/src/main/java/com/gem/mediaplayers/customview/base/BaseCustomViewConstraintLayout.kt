package com.gem.mediaplayers.customview.base

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout

abstract class BaseCustomViewConstraintLayout : ConstraintLayout {
    abstract val layoutRes: Int
    protected open val styleRes: IntArray? = null

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    ) {
        setLayout()
        initListener()
        initView()
        initData()
        attrs?.let {
            initStyleable(it)
        }
    }

    private fun initStyleable(it: AttributeSet) {
        if (styleRes != null && styleRes?.isNotEmpty()!!) {
            val typeArray = context.obtainStyledAttributes(it, styleRes, 0, 0)
            initDataFromStyleable(typeArray)
            typeArray.recycle()
        }
    }

    protected open fun initDataFromStyleable(typeArray: TypedArray?) {
    }

    open fun initView() = Unit

    open fun initData() = Unit

    open fun initListener() = Unit

    private fun setLayout() {
        LayoutInflater.from(context).inflate(layoutRes, this, true)
    }


}