package com.droi.view

import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.droi.BaseActivity
import com.droi.R
import com.droi.databinding.ActivityDetailBinding
import com.droi.viewmodel.MainViewModel

class DetailActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val model: MainViewModel by viewModels()
    var position = 0

    override fun _init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
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

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.iv_detail_like -> {
                model.isLike(position)
            }
            else -> {
            }
        }
    }
}
