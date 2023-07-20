package com.example.android_base.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.android_base.R
import com.example.android_base.databinding.BottomSheetTextViewBinding
import com.example.android_base.utils.inflateView

class CommonDropDownAdapter(
    mContext: Context,
    private val list: List<Any?>,
    private val onBind: (textView: TextView, data: Any?) -> Unit = { _: TextView, _: Any? -> }
) : ArrayAdapter<Any>(mContext, R.layout.bottom_sheet_text_view, list) {

    override fun getCount() = list.size

    override fun getItem(position: Int) = list[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = BottomSheetTextViewBinding.bind(context.inflateView(R.layout.bottom_sheet_text_view))
        onBind(binding.text1, getItem(position))
        return binding.root
    }
}