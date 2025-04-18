package com.bvc.ordering.view.splash.select

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bvc.domain.model.Store
import com.bvc.ordering.R
import com.bvc.ordering.base.BaseFragment
import com.bvc.ordering.databinding.FragmentSelectBinding
import com.bvc.ordering.ui.VerticalSpaceItemDecoration
import com.bvc.ordering.view.main.MainActivity
import com.bvc.ordering.view.splash.SplashFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectFragment : BaseFragment<FragmentSelectBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_select
    private val viewModel: SelectViewModel by viewModels()

    override fun init(savedInstanceState: Bundle?) {
        binding?.apply {
            vm = viewModel
            rvSelectStore.layoutManager = LinearLayoutManager(context)
            rvSelectStore.adapter =
                SelectAdapter(
                    viewModel.affiliate.value ?: emptyList(),
                    object : SelectAdapter.OnItemClickListener {
                        override fun onItemClick(item: Store) {
                            viewModel.selectStore(item)
                        }
                    },
                )
            rvSelectStore.addItemDecoration(VerticalSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.d_2300)))
        }
    }

//    eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwaG9uZV9udW1iZXIiOiIwMTAzNDUzODA2MCIsInN1YiI6OCwiaWF0IjoxNzQ0MzQwOTQ3LCJleHAiOjE3NTIxMTY5NDd9.zBr3MSvwHuJgR7F8DkPKjCPfknejociYXVjOEFgRNKI
    override fun handleViewModel() {
        viewModel.apply {
            action.observe(viewLifecycleOwner) {
                when (it) {
                    1 -> {
                        MainActivity.startActivity(requireActivity())
                    }
                    2 -> {
                        findNavController().navigate(SplashFragment::class.java.name)
                    }
                }
            }
            affiliate.observe(viewLifecycleOwner) {
                (binding?.rvSelectStore?.adapter as? SelectAdapter)?.updateItems(it)
            }
        }
    }
}
