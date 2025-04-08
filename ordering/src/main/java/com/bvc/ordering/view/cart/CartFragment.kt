package com.bvc.ordering.view.cart

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bvc.domain.model.CartEntity
import com.bvc.ordering.R
import com.bvc.ordering.base.BaseFragment
import com.bvc.ordering.databinding.FragmentCartBinding
import com.bvc.ordering.ui.VerticalSpaceItemDecoration
import com.bvc.ordering.util.Util
import com.bvc.ordering.view.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_cart
    private val viewModel: CartViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun init(savedInstanceState: Bundle?) {
        binding?.apply {
            vm = viewModel

            rvCartItems.apply {
                layoutManager =
                    object : LinearLayoutManager(context) {
                        override fun canScrollVertically(): Boolean {
                            return false // RecyclerView 내부 스크롤을 막고, NestedScrollView에게 스크롤 위임
                        }
                    }
                adapter =
                    CartAdapter(
                        viewModel.cartData.value,
                        object : CartAdapter.OnItemClickListener {
                            override fun onItemClick(item: CartEntity) {
                                viewModel.deleteItem(item)
                            }

                            override fun minusClick(item: CartEntity) {
                                viewModel.minusItem(item)
                            }

                            override fun plusClick(item: CartEntity) {
                                viewModel.plusItem(item)
                            }
                        },
                    )
                addItemDecoration(VerticalSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.d_2300)))
            }
            ivCartClose.setOnClickListener {
                val navController = findNavController()
                if (navController.previousBackStackEntry != null) {
                    navController.navigateUp()
                }
            }
            llCartCardPayment.setOnClickListener {
                viewModel.postOrder()
            }
        }
    }

    override fun handleViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.apply {
                    launch {
                        cartData.collect {
                            binding?.rvCartItems?.adapter?.let { adapter ->
                                if (adapter is CartAdapter) {
                                    adapter.updateItems(it)
                                }
                            }
                        }
                    }
                    launch {
                        supplyAmount.collect {
                            binding?.icCartSummary?.tvSupplyValue?.text = "${Util.myFormatter(it)}원"
                        }
                    }
                    launch {
                        vatAmount.collect {
                            binding?.icCartSummary?.tvVatValue?.text = "${Util.myFormatter(it)}원"
                        }
                    }
                    launch {
                        totalAmount.collect {
                            binding?.icCartSummary?.tvTotalValue?.text = "${Util.myFormatter(it)}원"
                        }
                    }
                }
            }
        }
        viewModel.apply {
            requestTelegram.observe(viewLifecycleOwner) {
                mainViewModel.requestTelegram(it)
            }
        }
    }
}
