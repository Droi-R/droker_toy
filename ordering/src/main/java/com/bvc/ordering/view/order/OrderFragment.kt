package com.bvc.ordering.view.order

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bvc.domain.model.CategoryEntity
import com.bvc.domain.model.ProductEntity
import com.bvc.domain.model.SubCategoryEntity
import com.bvc.ordering.R
import com.bvc.ordering.base.BaseFragment
import com.bvc.ordering.databinding.FragmentOrderBinding
import com.bvc.ordering.ui.HorizontalSpaceItemDecoration
import com.bvc.ordering.ui.VerticalSpaceItemDecoration
import com.bvc.ordering.view.inflate.CategoryAdapter
import com.bvc.ordering.view.inflate.GridAdapter
import com.bvc.ordering.view.inflate.SubCategoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_order
    private val viewModel: OrderViewModel by viewModels()

    override fun init(savedInstanceState: Bundle?) {
        binding?.apply {
            vm = viewModel
            icOrder.vm = viewModel
            icOrder.rvInflateCategory.apply {
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
            icOrder.rvInflateSubCategory.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(HorizontalSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.d_600)))
                adapter =
                    SubCategoryAdapter(
                        object : SubCategoryAdapter.OnItemClickListener<SubCategoryEntity> {
                            override fun onItemClick(item: SubCategoryEntity) {
                                viewModel.updateSubCategory(item)
                            }
                        },
                    )
            }
            icOrder.rvInflateGrid.apply {
                layoutManager = GridLayoutManager(context, 3)
                addItemDecoration(HorizontalSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.d_1000)))
                addItemDecoration(VerticalSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.d_1000)))
                adapter =
                    GridAdapter(
                        object : GridAdapter.OnItemClickListener<ProductEntity> {
                            override fun onItemClick(item: ProductEntity) {
                            }
                        },
                    )
            }
        }
    }

    override fun handleViewModel() {
        viewModel.apply {
            category.observe(viewLifecycleOwner) {
                binding?.icOrder?.rvInflateCategory?.adapter?.let { adapter ->
                    if (adapter is CategoryAdapter<*>) {
                        (adapter as CategoryAdapter<CategoryEntity>).submitList(it.toList())
                    }
                }
                viewModel.getSubCategory(it.find { it.selected }?.id ?: "")
            }
            subCategory.observe(viewLifecycleOwner) {
                binding?.icOrder?.rvInflateSubCategory?.adapter?.let { adapter ->
                    if (adapter is SubCategoryAdapter<*>) {
                        (adapter as SubCategoryAdapter<SubCategoryEntity>).submitList(it.toList())
                    }
                }
                viewModel.getProducts(it.find { it.selected }?.id ?: "")
            }
            product.observe(viewLifecycleOwner) {
                binding?.icOrder?.rvInflateGrid?.adapter?.let { adapter ->
                    if (adapter is GridAdapter<*>) {
                        (adapter as GridAdapter<ProductEntity>).submitList(it.toList())
                    }
                }
            }
        }
    }
}
