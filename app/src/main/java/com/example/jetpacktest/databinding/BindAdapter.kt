package com.example.jetpacktest.databinding

import androidx.appcompat.widget.AppCompatCheckBox
import androidx.databinding.*
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpacktest.databinding.adapter.TestAdapterLMultiple
import com.example.jetpacktest.databinding.adapter.TestAdapterLDSingle
import com.example.jetpacktest.entity.User

//@BindingMethods(
//    value = [
//        BindingMethod(
//            type = AppCompatTextView::class,
//            attribute = "android:text",
//            method = "setImageTintList"
//        )
//    ]
//)
//第一种 实现 DataBindingComponent  使用 3个参数的方法 最后一个传入
//binding = DataBindingUtil.setContentView(this, R.layout.activity_main,BindAdapter())
//class BindAdapter : DataBindingComponent {
//    //普通方法  不需要加  @JvmStatic
//    @BindingAdapter("recyclerList", "error", requireAll = false)
//    fun RecyclerView.recyclerList(age: ArrayList<User>, error: String?) {
//        val adapterTest = TestAdapterLMultiple()
//        adapter = adapterTest
//        adapterTest.submitList(age)
//    }
//
//    @BindingAdapter("recyclerSingleList", "error", requireAll = false)
//    fun RecyclerView.recyclerSingleList(age: ArrayList<User>, error: String?) {
//        val adapterTest = TestAdapterLSingle()
//        adapter = adapterTest
//        adapterTest.submitList(age)
//    }
//
//    override fun getBindStaticAdapter(): BindStaticAdapter {
//        TODO("Not yet implemented")
//    }
//
//
//}

//第二种 静态类  使用 2个参数的方法
//binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
object BindStaticAdapter {
    //普通方法  加  @JvmStatic
    @BindingAdapter("recyclerList", "error", requireAll = false)
    @JvmStatic
    fun RecyclerView.recyclerList(age: ArrayList<User>, error: String?) {
        val adapterTest = TestAdapterLMultiple()
        adapter = adapterTest
        adapterTest.submitList(age)
    }

    @BindingAdapter("recyclerSingleList", "error", requireAll = false)
    @JvmStatic
    fun RecyclerView.recyclerSingleList(age: ArrayList<User>, error: String?) {
        val adapterTest = TestAdapterLDSingle()
        adapter = adapterTest
        adapterTest.submitList(age)
    }


    @BindingAdapter("numberOfSets")
    @JvmStatic
    fun setNumberOfSets(view: AppCompatCheckBox, value: Boolean) {

    }

    @BindingAdapter("numberOfSetsAttrChanged")
    @JvmStatic
    fun setListeners(
        view: AppCompatCheckBox,
        attrChange: InverseBindingListener
    ) {
        // Set a listener for click, focus, touch, etc.
        view.setOnClickListener {
            attrChange.onChange()
        }
    }

    @InverseBindingAdapter(attribute = "numberOfSets")
    @JvmStatic
    fun getNumberOfSets(view: AppCompatCheckBox): Boolean {
        return view.isChecked
    }

}