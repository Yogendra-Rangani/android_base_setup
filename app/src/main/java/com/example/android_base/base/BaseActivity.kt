package com.example.android_base.base

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

@SuppressLint("Registered")
abstract class BaseActivity<BINDING: ViewDataBinding> : AppCompatActivity() {

    protected lateinit var mContext: Activity
    protected var TAG: String = ""
    protected lateinit var binding: BINDING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutResourceId())
        binding.lifecycleOwner = this

        // New on back press
        onBackPressedDispatcher.addCallback(this,onBackPressedCallback)

        // Init Global bus
//        GlobalBus.bus.register(this@BaseActivity)
        // init context
        mContext = this@BaseActivity

        // init tag variable for log trace
        TAG = javaClass.simpleName

        clickListeners()
    }

    protected abstract fun getLayoutResourceId(): Int
    protected abstract fun clickListeners()

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBackPress()
        }
    }

    override fun onDestroy() {
//        GlobalBus.bus.unregister(this@BaseActivity)
        super.onDestroy()
    }

//    @Subscribe
//    internal open fun onEventFired(event: EventBusModel) {
//    }

    internal open fun onBackPress(){
        finish()
    }
}