package com.example.jetpacktest.databinding.adapter

import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.example.jetpacktest.BR
import com.example.jetpacktest.R
import com.example.jetpacktest.base.BaseDViewHolder
import com.example.jetpacktest.base.LDBaseSingleAdapter
import com.example.jetpacktest.databinding.ItemBindOneHomeBinding
import com.example.jetpacktest.databinding.ItemHomeBinding
import com.example.jetpacktest.entity.User

val difSingle = object : DiffUtil.ItemCallback<User>() {
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

class TestAdapterLDSingle : LDBaseSingleAdapter<User>(
    difSingle,
    R.layout.item_bind_one_home
) {

    init {
        initDViewHolder = { binding ->
            if (binding.binding is ItemBindOneHomeBinding)
                binding.binding.name.setOnClickListener {
                    Toast.makeText(
                        it.context,
                        getItem(binding.adapterPosition).name.get(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    override fun bindData(holder: BaseDViewHolder<ViewDataBinding>, item: User) {
        val binding = holder.binding
        binding.setVariable(BR.user, item)
    }


}
