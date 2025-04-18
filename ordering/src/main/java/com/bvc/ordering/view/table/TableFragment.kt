package com.bvc.ordering.view.table

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bvc.domain.log
import com.bvc.domain.model.CategoryEntity
import com.bvc.domain.model.SubCategoryEntity
import com.bvc.ordering.R
import com.bvc.ordering.base.BaseFragment
import com.bvc.ordering.databinding.FragmentTableBinding
import com.bvc.ordering.ui.HorizontalSpaceItemDecoration
import com.bvc.ordering.ui.VerticalSpaceItemDecoration
import com.bvc.ordering.ui.event.collectNonEmpty
import com.bvc.ordering.view.inflate.CategoryAdapter
import com.bvc.ordering.view.inflate.GridAdapter
import com.bvc.ordering.view.inflate.SubCategoryAdapter
import com.bvc.ordering.view.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TableFragment : BaseFragment<FragmentTableBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_table
    private val viewModel: TableViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun init(savedInstanceState: Bundle?) {
        binding?.apply {
            vm = viewModel
            icTable.rvInflateCategory.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(HorizontalSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.d_3200)))
                adapter =
                    CategoryAdapter(
                        object : CategoryAdapter.OnItemClickListener<CategoryEntity> {
                            override fun onItemClick(item: CategoryEntity) {
                                viewModel.updateCategory(item)
                            }
                        },
                    )
            }
            icTable.rvInflateSubCategory.apply {
                visibility = View.GONE
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(HorizontalSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.d_600)))
                adapter =
                    SubCategoryAdapter(
                        object : SubCategoryAdapter.OnItemClickListener<SubCategoryEntity> {
                            override fun onItemClick(item: SubCategoryEntity) {
//                                viewModel.updateSubCategory(item)
                            }
                        },
                    )
            }
            icTable.rvInflateGrid.apply {
                layoutManager = GridLayoutManager(context, 3)
                addItemDecoration(HorizontalSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.d_1000)))
                addItemDecoration(VerticalSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.d_1000)))
                adapter =
                    GridAdapter {
                        onTableClick { item ->
                            log.e("Table Clicked: $item")
                            if (item.orders.isEmpty()) {
                                findNavController().navigate(TableOrderFragment::class.java.name)
                            } else {
                                findNavController().navigate(TableCartFragment::class.java.name)
                            }
                            mainViewModel.sendTableEvent(item)
                        }
                    }
            }
        }
    }

    override fun handleViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.apply {
                    category.collectNonEmpty(viewLifecycleOwner) { list ->
                        // 카테고리 UI 갱신
                        binding?.icTable?.rvInflateCategory?.adapter?.let { adapter ->
                            if (adapter is CategoryAdapter<*>) {
                                (adapter as CategoryAdapter<CategoryEntity>).submitList(list.toList())
                            }
                        }
                        getTables(list.find { it.selected }?.mainCategoryId ?: "")
                    }

//                    subCategory.collectNonEmpty(viewLifecycleOwner) { subCategory ->
//                        // 상품 UI 갱신
//                        binding?.icTable?.rvInflateSubCategory?.adapter?.let { adapter ->
//                            if (adapter is SubCategoryAdapter<*>) {
//                                (adapter as SubCategoryAdapter<SubCategoryEntity>).submitList(
//                                    subCategory.toList(),
//                                )
//                            }
//                        }
//                        getTables(subCategory.find { it.selected }?.subCategoryId ?: "")
//                    }

                    tables.collectNonEmpty(viewLifecycleOwner) { tables ->
                        // 장바구니 UI 갱신
                        binding?.icTable?.rvInflateGrid?.adapter?.let { adapter ->
                            if (adapter is GridAdapter) {
                                adapter.submitList(tables.toList())
                            }
                        }
                    }
                }
            }
        }
    }
}
