package com.example.android_base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.android_base.retrofit.ResponseStatus
import com.example.android_base.utils.show
import com.example.android_base.utils.toast
import com.example.android_base.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val _postViewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _postViewModel.getPostVM(query = "AI+Animal")
        observer()
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