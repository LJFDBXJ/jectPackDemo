package com.example.jetpacktest.utle

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class ViewHolder<V : ViewDataBinding>(val binding: V) : RecyclerView.ViewHolder(binding.root)
typealias ClickItem<E> = (view: View, item: E) -> Unit