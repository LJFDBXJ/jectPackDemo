package com.example.jetpacktest.base

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class BaseDViewHolder<V : ViewDataBinding>(val binding: V) : RecyclerView.ViewHolder(binding.root)
typealias ClickItem = (view: View, position: Int) -> Unit
