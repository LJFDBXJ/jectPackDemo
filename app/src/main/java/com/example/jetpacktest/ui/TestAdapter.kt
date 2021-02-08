package com.example.jetpacktest.ui

import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.example.jetpacktest.BR
import com.example.jetpacktest.R
import com.example.jetpacktest.databinding.ItemHomeBinding
import com.example.jetpacktest.entity.User
import com.example.jetpacktest.utle.BaseAdapter

val dif = object : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: User,
        newItem: User
    ): Boolean {
        return oldItem == newItem
    }
}

class TestAdapter : BaseAdapter<User>(
    dif,
    R.layout.item_home,
    R.layout.item_two_home
) {

    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)
        return if (position < 5)
            0
        else
            1
    }

    override fun bindData(bind: ViewDataBinding, item: User) {
        bind.setVariable(BR.user, item)

    }
    init {
        elementClick={binding->
            if (binding.binding is ItemHomeBinding)
                binding.binding.name.setOnClickListener {
                    Toast.makeText(it.context, getItem(binding.adapterPosition).name, Toast.LENGTH_SHORT).show()
                }

        }
    }
}



