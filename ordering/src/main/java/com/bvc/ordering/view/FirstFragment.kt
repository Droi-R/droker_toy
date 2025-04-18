//package com.bvc.ordering.view
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.databinding.DataBindingUtil
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.Observer
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.bvc.ordering.base.BaseFragment
//import com.bvc.R
//import com.bvc.data.util.Logger
//import com.bvc.databinding.FragmentFirstBinding
//import com.bvc.domain.model.YoEntity
//import com.bvc.ordering.ui.CustomRecyclerDecoration_Ve
//import com.bvc.ordering.view.main.MainViewModel
//import com.facebook.shimmer.ShimmerFrameLayout
//import dagger.hilt.android.AndroidEntryPoint
//import javax.inject.Inject
//
//@AndroidEntryPoint
//class FirstFragment @Inject constructor() : BaseFragment() {
//    private lateinit var binding: FragmentFirstBinding
//
//    private lateinit var firstAdapter: FirstAdapter
//    private val model: MainViewModel by viewModels()
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?,
//    ): View {
//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false)
////        return super.onCreateView(inflater, container, savedInstanceState)
//        return binding.root
//    }
//
//    override fun _init() {
//        initRecyclerView()
//        startShimmer(binding.sfRecruit)
//        model.requsetUsers()
//    }
//    private fun initRecyclerView() {
//        binding.rvRecruit.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
////        binding.rvRecruit.layoutManager = GridLayoutManager(requireActivity(),2)
//        firstAdapter = FirstAdapter(this, requireActivity())
//        binding.rvRecruit.adapter = firstAdapter
//        val adapterobseverRecruit: Observer<YoEntity.Res> =
//            Observer {
//                Logger.loge("liveData_Res   $it")
//                firstAdapter.diff(it.items, "", model.change)
//                model.change = -1
//                stopShimmer(binding.sfRecruit)
//            }
//        model.liveData_Res.observe(this, adapterobseverRecruit)
//        binding.rvRecruit.addItemDecoration(CustomRecyclerDecoration_Ve(20))
//    }
//
//    fun startShimmer(view: ShimmerFrameLayout) {
//        view.showShimmer(true)
//        view.startShimmer()
//    }
//
//    fun stopShimmer(view: ShimmerFrameLayout) {
//        view.stopShimmer()
//        view.hideShimmer()
//    }
//
//    override fun oneClick(v: View, position: Int) {
//        when (v.id) {
//            R.id.cl_first -> {
//                val intent = Intent(requireActivity(), DetailActivity::class.java)
////                intent.putExtra("item",model.liveData_Res.value?.items?.get(position))
//                intent.putExtra("position", position)
//                startActivity(intent)
//            }
//            R.id.iv_like -> {
//                model.isLike(position)
//            }
//        }
//    }
//
//    override fun onClick(v: View) {
//        super.onClick(v)
//        when (v.id) {
//            else -> {
//            }
//        }
//    }
//}
