package com.bvc.ordering.view.table

import android.os.Bundle
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
import com.bvc.domain.model.TableEntity
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
                    GridAdapter(
                        object : GridAdapter.OnItemClickListener<TableEntity> {
                            override fun onItemClick(item: TableEntity) {
                                if (item.orders.isEmpty()) {
                                    findNavController().navigate(TableOrderFragment::class.java.name)
                                } else {
                                    findNavController().navigate(TableCartFragment::class.java.name)
                                }
                                mainViewModel.sendTableEvent(item)
                            }
                        },
                    )
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
                        log.e("category: $list")
                        getSubCategory(list.find { it.selected }?.id ?: "")
                    }

                    subCategory.collectNonEmpty(viewLifecycleOwner) { subCategory ->
                        // 상품 UI 갱신
                        binding?.icTable?.rvInflateSubCategory?.adapter?.let { adapter ->
                            if (adapter is SubCategoryAdapter<*>) {
                                (adapter as SubCategoryAdapter<SubCategoryEntity>).submitList(
                                    subCategory.toList(),
                                )
                            }
                        }
                        log.e("subCategory: $subCategory")
                        getTables(subCategory.find { it.selected }?.id ?: "")
                    }

                    tables.collectNonEmpty(viewLifecycleOwner) { tables ->
                        // 장바구니 UI 갱신
                        binding?.icTable?.rvInflateGrid?.adapter?.let { adapter ->
                            if (adapter is GridAdapter<*>) {
                                (adapter as GridAdapter<TableEntity>).submitList(tables.toList())
                            }
                        }
                    }
                }
            }
        }
    }
}
