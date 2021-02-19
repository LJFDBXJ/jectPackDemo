package com.example.jetpacktest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import com.example.jetpacktest.base.LDBaseSingleAdapter
import com.example.jetpacktest.databinding.ItemHomeBinding

val difSingle = object : DiffUtil.ItemCallback<Class<out AppCompatActivity>>() {
    override fun areContentsTheSame(
        oldItem: Class<out AppCompatActivity>,
        newItem: Class<out AppCompatActivity>
    ): Boolean {
        return oldItem.simpleName == newItem.simpleName
    }

    override fun areItemsTheSame(
        oldItem: Class<out AppCompatActivity>,
        newItem: Class<out AppCompatActivity>
    ): Boolean {
        return oldItem == newItem
    }
}

class HomeAdapter :
    LDBaseSingleAdapter<Class<out AppCompatActivity>, ItemHomeBinding>(
        difSingle,
        R.layout.item_home
    ) {

    init {
        initDViewHolder = { holder ->
            holder.binding.jumpActivity.setOnClickListener {
                val item = getItem(holder.adapterPosition)
                val intent = Intent(holder.binding.root.context, item)
                holder.binding.root.context.startActivity(intent)
            }
        }
    }

    override fun bindData(bind: ItemHomeBinding, item: Class<out AppCompatActivity>) {
        bind.jumpActivity.text = item.simpleName

    }
}