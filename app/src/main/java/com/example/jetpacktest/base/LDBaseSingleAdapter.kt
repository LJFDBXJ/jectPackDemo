package com.example.jetpacktest.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.jetpacktest.databinding.ui.DataBindingActivity
import java.util.ArrayList

/**
 * auth:LJFDBXJ
 * emil: ljfdbxj@163.com
 * weChart: 935312568
 */
abstract class LDBaseSingleAdapter<E : Any, D : ViewDataBinding>(
    callBack: DiffUtil.ItemCallback<E>,
    private var layoutId: Int
) : ListAdapter<E, BaseDViewHolder<D>>(callBack) {

    var onClick: ClickItem? = null
    var onLongClick: ClickItem? = null
    var initDViewHolder: Function1<BaseDViewHolder<D>, Unit>? = null
    abstract fun bindData(bind: D, item: E)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseDViewHolder<D> {
        //通过不同 viewType  产生不同DataBiding
        val binding = DataBindingUtil.inflate<D>(
            LayoutInflater.from(parent.context), layoutId,
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
            //item element set Init
            initDViewHolder?.invoke(viewHolder)

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

    override fun onBindViewHolder(holderBaseD: BaseDViewHolder<D>, position: Int) {
        bindData(holderBaseD.binding, getItem(position))
    }


}