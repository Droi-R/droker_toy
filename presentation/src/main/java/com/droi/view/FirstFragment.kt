package com.droi.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.droi.BaseFragment
import com.droi.R
import com.droi.data.util.Logger
import com.droi.databinding.FragmentFirstBinding
import com.droi.domain.model.YoEntity
import com.droi.viewmodel.MainViewModel
import com.facebook.shimmer.ShimmerFrameLayout

class FirstFragment : BaseFragment() {
    lateinit var binding: FragmentFirstBinding

    lateinit var firstAdapter: FirstAdapter
    private val model: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false)
//        return super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun _init() {
        initRecyclerView()
        startShimmer(binding.sfRecruit)
        model.requsetUsers()
    }
    private fun initRecyclerView() {
        binding.rvRecruit.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
//        binding.rvRecruit.layoutManager = GridLayoutManager(requireActivity(),2)
        firstAdapter = FirstAdapter(this, requireActivity())
        binding.rvRecruit.adapter = firstAdapter
        val adapterobseverRecruit: Observer<YoEntity.Res> =
            Observer {
                Logger.loge("liveData_Res   $it")
                firstAdapter.diff(it.items, "", model.change)
                model.change = -1
                stopShimmer(binding.sfRecruit)
            }
        model.liveData_Res.observe(this, adapterobseverRecruit)
//        binding.rvRecruit.addItemDecoration(CustomRecyclerDecoration_Ho(12))
        binding.rvRecruit.addItemDecoration(com.droi.ui.CustomRecyclerDecoration_Ve(20))
    }

    fun startShimmer(view: ShimmerFrameLayout) {
        view.showShimmer(true)
        view.startShimmer()
    }

    fun stopShimmer(view: ShimmerFrameLayout) {
        view.stopShimmer()
        view.hideShimmer()
    }

    override fun oneClick(v: View, position: Int) {
        when (v.id) {
            R.id.cl_first -> {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
//                intent.putExtra("item",model.liveData_Res.value?.items?.get(position))
                intent.putExtra("position", position)
                startActivity(intent)
            }
            R.id.iv_like -> {
                model.isLike(position)
            }
        }
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            else -> {
            }
        }
    }
}
