package com.example.android_base

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.android_base.adapter.CommonDropDownAdapter
import com.example.android_base.base.BaseActivity
import com.example.android_base.databinding.ActivityMainBinding
import com.example.android_base.retrofit.ResponseStatus
import com.example.android_base.utils.getClr
import com.example.android_base.utils.show
import com.example.android_base.utils.toast
import com.example.android_base.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val _postViewModel by viewModels<PostViewModel>()
    private var list: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showBottomSheetWithSearchableDialog()

        list.add("a")
        list.add("b")
        list.add("c")
        showPopUp()
//        _postViewModel.getPostVM(query = "AI+Animal")
        _postViewModel.getPostVM(this)
        observer()
    }
    private fun showBottomSheetWithSearchableDialog() {
        val bottomSheetFragment = BottomSheetFragment()
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }
    override fun getLayoutResourceId(): Int = R.layout.activity_main

    override fun clickListeners() {
    }

    private fun showPopUp() {
        binding.tvTransactionFilter.setText(list.first())

        val adapter = CommonDropDownAdapter(this, list.toMutableList()) { textView, data ->
            if (data as String == binding.tvTransactionFilter.text.toString()) {
                textView.setTextColor(this.getClr(R.color.app_primary))
                textView.setTypeface(textView.typeface, Typeface.BOLD)
            } else {
                textView.setTextColor(Color.BLACK)
                textView.setTypeface(textView.typeface, Typeface.NORMAL)
            }
            textView.text = data
        }
        binding.tvTransactionFilter.setAdapter(adapter)

        binding.tvTransactionFilter.setOnItemClickListener { _, _, position, _ ->
//            selectedFilterItem = position
//            financialFilterReq.group = getFilterParam(selectedFilterItem)
//            resetAndGetTransactions()
        }

        binding.tvTransactionFilter.setOnClickListener {
            binding.tvTransactionFilter.showDropDown()
        }
    }

    private fun observer() {
        val loader = show()
        _postViewModel.post.observe(this) { post ->
            post.getContentIfNotHandled().let { it ->
                when (it) {
                    is ResponseStatus.Loading -> {}
                    is ResponseStatus.Success -> {
                        it.data.toString().toast(this)
                        loader.dismiss()
                    }

                    is ResponseStatus.Error -> {
                        loader.dismiss()
                        it.message.toString().toast(this)
                    }

                    else -> {}
                }
            }
        }
    }
}