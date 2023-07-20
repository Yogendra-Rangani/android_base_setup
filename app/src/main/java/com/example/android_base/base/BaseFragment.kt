package com.example.android_base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

abstract class BaseFragment<BINDING : ViewDataBinding>(@LayoutRes val layoutResId: Int) :
    Fragment() {

    internal lateinit var binding: BINDING
    internal lateinit var mContext: FragmentActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        GlobalBus.bus.register(this)
        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        mContext = requireActivity()
        return binding.root
    }

//    @Subscribe
//    internal open fun onEventFired(event: EventBusModel) {}

    override fun onDestroyView() {
        super.onDestroyView()
//        GlobalBus.bus.unregister(this)
        binding.unbind()
    }

}