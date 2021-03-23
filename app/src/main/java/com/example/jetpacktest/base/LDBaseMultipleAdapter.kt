package com.example.jetpacktest.base

import android.view.LayoutInflater
import android.view.View
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
abstract class LDBaseMultipleAdapter<E : Any>(
    callBack: DiffUtil.ItemCallback<E>,
    vararg var layoutId: Int
) : ListAdapter<E, BaseDViewHolder<ViewDataBinding>>(callBack) {
    private var headLayoutId: Int? = null
    private var headLayout: View? = null
    private var footLayoutId: Int? = null
    private var footLayout: View? = null

    var onClick: ClickItem? = null
    var onLongClick: ClickItem? = null
    var elementClick: Function1<BaseDViewHolder<ViewDataBinding>, Unit>? = null
    abstract fun bindData(bind: BaseDViewHolder<ViewDataBinding>, position: Int)
    lateinit var binding: ViewDataBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseDViewHolder<ViewDataBinding> {

        //通过不同 viewType  产生不同DataBiding
        when (viewType) {
            LDBaseSingleAdapter.HEAD_VIEW_TYPE -> {
                headLayoutId?.let {
                    binding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context), it,
                        parent,
                        false
                    )
                }
                headLayout?.let {
                    val bind = DataBindingUtil.bind<ViewDataBinding>(it)
                    bind?.let { d ->
                        binding = d
                    }
                }

            }
            LDBaseSingleAdapter.FOOT_VIEW_TYPE -> {
                footLayoutId?.let {
                    binding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context), it,
                        parent,
                        false
                    )
                }
                footLayout?.let {
                    val bind = DataBindingUtil.bind<ViewDataBinding>(it)
                    bind?.let { d ->
                        binding = d
                    }
                }
            }
            else -> {
                binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    layoutId[viewType],
                    parent,
                    false
                )
            }
        }
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

    fun setHeadView(headId: Int? = null, headView: View? = null) {
        headId?.let {
            headLayoutId = headId
            notifyItemInserted(0)
        }
        headView?.let {
            headLayout = headView
            notifyItemInserted(0)
        }
    }

    fun setFootView(footId: Int? = null, footView: View? = null) {
        footId?.let {
            footLayoutId = footId
            notifyItemInserted(currentList.size)
        }
        footView?.let {
            footLayout = footView
            notifyItemInserted(currentList.size)
        }
    }

    override fun onBindViewHolder(holderBaseD: BaseDViewHolder<ViewDataBinding>, position: Int) {
        bindData(holderBaseD, position)
    }

    /**
     * 多布局情况
     * 默认返回 0   layout
     * 重写则返回相应  vararg 布局下标
     */
    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)
        return if (position == 0 && headLayoutId != null || headLayout != null) {
            LDBaseSingleAdapter.HEAD_VIEW_TYPE
        } else if (position == currentList.size - 1 && footLayoutId != 0 || footLayout != null) {
            LDBaseSingleAdapter.FOOT_VIEW_TYPE
        } else {
            position
        }
    }
}