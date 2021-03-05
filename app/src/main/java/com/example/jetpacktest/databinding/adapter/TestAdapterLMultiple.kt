package com.example.jetpacktest.databinding.adapter

import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.example.jetpacktest.BR
import com.example.jetpacktest.R
import com.example.jetpacktest.base.LBaseMultipleAdapter
import com.example.jetpacktest.databinding.ItemBindTwoHomeBinding
import com.example.jetpacktest.entity.User

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

class TestAdapterLMultiple : LBaseMultipleAdapter<User>(
    dif,
    R.layout.item_bind_one_home,
    R.layout.item_bind_two_home
) {

    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)
        val item = getItem(position)
        return item.layoutType
    }

    override fun bindData(bind: ViewDataBinding, position: Int) {
        val item = getItem(position)
        bind.setVariable(BR.user, item)
    }

    init {
        elementClick = { binding ->
            if (binding.binding is ItemBindTwoHomeBinding)
                binding.binding.name.setOnClickListener {
                    Toast.makeText(
                        it.context,
                        getItem(binding.adapterPosition).name.get(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

        }
    }
}



