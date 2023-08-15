package com.droi.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.droi.BaseActivity
import com.droi.R
import com.droi.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    var fragmentManager: FragmentManager? = null
    var firstFragment: FirstFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
    }

    override fun _init() {
//        initTap()
    }

    private fun initTap() {
        fragmentManager = supportFragmentManager
        if (firstFragment == null) {
            firstFragment = FirstFragment()
            fragmentManager!!.beginTransaction().add(R.id.fl_main, firstFragment!!)
                .commitAllowingStateLoss()
        }
    }
}
