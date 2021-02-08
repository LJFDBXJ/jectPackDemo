package com.example.jetpacktest.utle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseAdapter<E : Any>(
    callBack: DiffUtil.ItemCallback<E>,
    vararg var layoutId: Int
) : ListAdapter<E, ViewHolder<ViewDataBinding>>(callBack) {

    var onClick: ClickItem<E>? = null
    var onLongClick: ClickItem<E>? = null
    var elementClick:Function1<ViewHolder<ViewDataBinding>,Unit>?=null
    abstract fun bindData(bind: ViewDataBinding, item: E)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ViewDataBinding> {

        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layoutId[viewType],
            parent,
            false
        )
        val viewHolder = ViewHolder(binding)
        viewHolder.apply {
            onClick?.let { onClick ->
                binding.root.setOnClickListener {
                    onClick.invoke(it, getItem(viewHolder.adapterPosition))
                }
            }
            elementClick?.invoke(viewHolder)
            onLongClick?.let { onLongClick ->
                binding.root.setOnLongClickListener {
                    onLongClick.invoke(it, getItem(viewHolder.adapterPosition))
                    return@setOnLongClickListener true
                }
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder<ViewDataBinding>, position: Int) {
        bindData(holder.binding, getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)
        return 0
    }
}