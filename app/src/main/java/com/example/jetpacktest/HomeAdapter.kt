package com.example.jetpacktest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.example.jetpacktest.base.BaseDViewHolder
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
    LDBaseSingleAdapter<Class<out AppCompatActivity>>(
        difSingle,
        R.layout.item_home
    ) {

    init {
      initDViewHolder={holder->
          val bind = binding
          if (bind is ItemHomeBinding) {
              bind.jumpActivity.setOnClickListener {
                  val item = getItem(getPosition(holder.adapterPosition))
                  val intent = Intent(it.context, item)
                  it.context.startActivity(intent)
              }
          }
      }
    }

    override fun bindData(
        holder: BaseDViewHolder<ViewDataBinding>,
        item: Class<out AppCompatActivity>
    ) {
        val bind = holder.binding
        if (bind is ItemHomeBinding)
            bind.jumpActivity.text = item.simpleName
    }


}