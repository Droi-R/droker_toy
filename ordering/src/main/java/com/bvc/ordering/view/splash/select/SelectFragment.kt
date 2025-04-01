package com.bvc.ordering.view.splash.select

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bvc.domain.model.AffiliateEntity
import com.bvc.ordering.R
import com.bvc.ordering.base.BaseFragment
import com.bvc.ordering.databinding.FragmentSelectBinding
import com.bvc.ordering.ui.VerticalSpaceItemDecoration
import com.bvc.ordering.view.main.MainActivity
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
                        override fun onItemClick(item: AffiliateEntity) {
                            if (item.type.isNotEmpty()) {
                                MainActivity.startActivity(requireActivity())
                            } else {
                                // 매장 생성
                            }
                        }
                    },
                )
            rvSelectStore.addItemDecoration(VerticalSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.d_2300)))
        }
    }

    override fun handleViewModel() {
        viewModel.apply {
            action.observe(viewLifecycleOwner) {
                if (it) {
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
            affiliate.observe(viewLifecycleOwner) {
                (binding?.rvSelectStore?.adapter as? SelectAdapter)?.updateItems(it)
            }
        }
    }
}
