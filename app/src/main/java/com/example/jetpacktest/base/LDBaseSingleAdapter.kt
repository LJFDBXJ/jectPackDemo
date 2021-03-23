package com.example.jetpacktest.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
abstract class LDBaseSingleAdapter<E : Any>(
    callBack: DiffUtil.ItemCallback<E>,
    private var layoutId: Int
) : ListAdapter<E, BaseDViewHolder<ViewDataBinding>>(callBack) {

    var onClick: ClickItem? = null
    var onLongClick: ClickItem? = null
    lateinit var binding: ViewDataBinding
    var initDViewHolder: Function1<BaseDViewHolder<ViewDataBinding>, Unit>? = null
    var headLayoutId: Int? = null
        set(value) {
            field = value
            notifyItemInserted(0)
        }

    var footLayoutId: Int? = null
        set(value) {
            field = value
            notifyItemInserted(currentList.size + 1)
        }

    abstract fun bindData(holder: BaseDViewHolder<ViewDataBinding>, item: E)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseDViewHolder<ViewDataBinding> {
        //通过不同 viewType  产生不同DataBiding
        when (viewType) {
            HEAD_VIEW_TYPE -> {
                headLayoutId?.let {
                    binding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context), it,
                        parent,
                        false
                    )
                }
            }
            FOOT_VIEW_TYPE -> {
                footLayoutId?.let {
                    binding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context), it,
                        parent,
                        false
                    )
                }
            }
            else -> {
                binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    layoutId,
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
                    when (getItemViewType(adapterPosition)) {
                        HEAD_VIEW_TYPE -> {

                        }
                        FOOT_VIEW_TYPE -> {

                        }
                        else -> {
                            val position = getPosition(adapterPosition)
                            onClick.invoke(it, position)
                        }
                    }
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


    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)
        return if (position == 0 && headLayoutId != null) {
            HEAD_VIEW_TYPE
        } else if (position == itemCount - 1 && footLayoutId != null) {
            FOOT_VIEW_TYPE
        } else {
            position
        }
    }


    override fun getItemCount(): Int {
        super.getItemCount()
        return if (headLayoutId != null && footLayoutId != null)
            currentList.size + 2
        else if (headLayoutId == null && footLayoutId == null)
            currentList.size
        else
            currentList.size + 1
    }

    //need process
    fun getPosition(position: Int): Int {
        /**
         * why cut 1 ?
         * we only deal with    @param headLayoutId situation
         * if headLayoutId exist  not null  ,when call    notifyItemInserted(0) method, the onBindViewHolder method  @param position will be 0
         *
         * and next position will sub 0 ,and  than last ,position  on my gad  i really don`t know  how to say it in english,
         *
         * i can`t speak English
         *
         * fuck .....
         */
        return if (headLayoutId != null)
            position - 1
        else
            position
    }

    override fun onBindViewHolder(holderBaseD: BaseDViewHolder<ViewDataBinding>, position: Int) {
        /**
         * self choice,  do you want to initialize the current head or foot?
         * no : return
         * yes :  please deal with the current possible problems
         */
        if (getItemViewType(position) == HEAD_VIEW_TYPE || getItemViewType(position) == FOOT_VIEW_TYPE)
            return
        val posit = getPosition(position)
        bindData(holderBaseD, getItem(posit))
    }

    companion object {
        // head view type Tag
        const val HEAD_VIEW_TYPE = -1

        // foot view type Tag
        const val FOOT_VIEW_TYPE = -2
    }

}