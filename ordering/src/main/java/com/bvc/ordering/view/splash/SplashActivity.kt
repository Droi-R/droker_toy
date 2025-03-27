package com.bvc.ordering.view.splash

import android.annotation.SuppressLint
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bvc.ordering.R
import com.bvc.ordering.base.BaseActivity
import com.bvc.ordering.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity() {
        private lateinit var binding: ActivitySplashBinding
    private val model: SplashViewModel by viewModels()
    var position = 0

    override fun _init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.lifecycleOwner = this
        position = intent.getIntExtra("position", 0)
        binding.item = model.liveData_Res.value?.items?.get(position)
        Glide.with(this).load(binding.item?.avatar_url).centerCrop().into(binding.ivDetail)
        if (binding.item?.like == true) {
            binding.ivDetailLike.setImageResource(R.drawable.circle)
        } else {
            binding.ivDetailLike.setImageResource(R.drawable.circle_empty)
        }
        binding.ivDetailLike.setOnClickListener(this)

        model.liveData_Res.observe(this) {
            val item = it.items[position]
            if (item.like) {
                binding.ivDetailLike.setImageResource(R.drawable.circle)
            } else {
                binding.ivDetailLike.setImageResource(R.drawable.circle_empty)
            }
        }
    }
}