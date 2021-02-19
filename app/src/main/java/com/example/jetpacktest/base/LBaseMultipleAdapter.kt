package com.example.jetpacktest.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

/**
 * auth:LJFDBXJ
 * emil: ljfdbxj@163.com
 * weChart: 935312568
 */
abstract class LBaseMultipleAdapter<E : Any>(
    callBack: DiffUtil.ItemCallback<E>,
    vararg var layoutId: Int
) : ListAdapter<E, BaseDViewHolder<ViewDataBinding>>(callBack) {

    var onClick: ClickItem? = null
    var onLongClick: ClickItem? = null
    var elementClick: Function1<BaseDViewHolder<ViewDataBinding>, Unit>? = null
    abstract fun bindData(bind: ViewDataBinding, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseDViewHolder<ViewDataBinding> {

        //通过不同 viewType  产生不同DataBiding
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layoutId[viewType],
            parent,
            false
        )
        val viewHolder = BaseDViewHolder(binding)
        viewHolder.apply {
            //item single click
            onClick?.let { onClick ->
                binding.root.setOnClickListener {
                    onClick.invoke(it, viewHolder.adapterPosition)
                }
            }
            //item element set Click
            elementClick?.invoke(viewHolder)

            //item Long click
            onLongClick?.let { onLongClick ->
                binding.root.setOnLongClickListener {
                    onLongClick.invoke(it, viewHolder.adapterPosition)
                    return@setOnLongClickListener true
                }
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holderBaseD: BaseDViewHolder<ViewDataBinding>, position: Int) {
        bindData(holderBaseD.binding, position)
    }

    /**
     * 多布局情况
     * 默认返回 0   layout
     * 重写则返回相应  vararg 布局下标
     */
    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)
        return 0
    }
}