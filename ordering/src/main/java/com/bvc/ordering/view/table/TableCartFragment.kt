package com.bvc.ordering.view.table

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bvc.domain.log
import com.bvc.domain.model.ProductEntity
import com.bvc.domain.model.TableEntity
import com.bvc.ordering.R
import com.bvc.ordering.base.BaseFragment
import com.bvc.ordering.databinding.FragmentTableCartBinding
import com.bvc.ordering.ui.VerticalSpaceItemDecoration
import com.bvc.ordering.ui.event.collectNonEmpty
import com.bvc.ordering.util.Utils
import com.bvc.ordering.view.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TableCartFragment : BaseFragment<FragmentTableCartBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_table_cart
    private val viewModel: TableCartViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private var tableEventJob: Job? = null

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
                    TableCartAdapter(
                        viewModel.cartData.value,
                        object : TableCartAdapter.OnItemClickListener {
                            override fun onItemClick(item: ProductEntity) {
                                viewModel.deleteItem(item)
                            }

                            override fun minusClick(item: ProductEntity) {
                                viewModel.minusItem(item)
                            }

                            override fun plusClick(item: ProductEntity) {
                                viewModel.plusItem(item)
                            }
                        },
                    )
                addItemDecoration(VerticalSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.d_2300)))
            }
            ivTableCartClose.setOnClickListener {
                viewModel.clearCart()
                val navController = findNavController()
                if (navController.previousBackStackEntry != null) {
                    navController.navigateUp()
                }
            }
            clTableCartMenu.setOnClickListener {
                viewModel.clearCart()
                tableEventJob?.cancel()
                findNavController().navigate(TableOrderFragment::class.java.name)
                mainViewModel.sendTableEvent(viewModel.tableInfo.value ?: TableEntity())
            }
            llCartCardPayment.setOnClickListener {
                viewModel.postOrder()
            }
        }
    }

    override fun handleViewModel() {
        tableEventJob =
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    mainViewModel.tableEventFlow.collect { event ->
                        event.getContentIfNotHandled()?.let { table ->
                            log.e("tableCart: $table")
                            viewModel.setTableInfo(table)
                        }
                    }
                }
            }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.apply {
                    cartData.collectNonEmpty(viewLifecycleOwner) {
                        binding?.rvCartItems?.adapter?.let { adapter ->
                            if (adapter is TableCartAdapter) {
                                adapter.updateItems(it)
                            }
                        }
                    }
                    launch {
                        supplyAmount.collect {
                            binding?.icCartSummary?.tvSupplyValue?.text = "${Utils.myFormatter(it)}원"
                        }
                    }
                    launch {
                        vatAmount.collect {
                            binding?.icCartSummary?.tvVatValue?.text = "${Utils.myFormatter(it)}원"
                        }
                    }
                    launch {
                        totalAmount.collect {
                            binding?.icCartSummary?.tvTotalValue?.text = "${Utils.myFormatter(it)}원"
                        }
                    }
                    requestTelegram.observe(viewLifecycleOwner) {
                        mainViewModel.requestCaptureTelegram(it)
                    }
                }
            }
        }
    }
}
