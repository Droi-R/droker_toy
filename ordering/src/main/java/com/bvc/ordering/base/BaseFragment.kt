package com.bvc.ordering.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.bvc.ordering.callback.OnItemClick
import com.google.gson.Gson

abstract class BaseFragment<T : ViewDataBinding> :
    Fragment(),
    View.OnClickListener,
    OnItemClick {
    protected var binding: T? = null
    protected val viewDataBinding: T
        get() = binding!!
    var gson = Gson()
    abstract val layoutResourceId: Int

    abstract fun init(savedInstanceState: Bundle?)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        return viewDataBinding.root
    }

    override fun onClick(v: View) {
    }

    override fun oneClick(
        v: View,
        position: Int,
    ) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.unbind()
        binding = null
    }
}
