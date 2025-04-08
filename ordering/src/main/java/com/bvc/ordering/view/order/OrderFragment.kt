package com.bvc.ordering.view.order

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
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
import com.bvc.ordering.util.Util
import com.bvc.ordering.view.cart.CartFragment
import com.bvc.ordering.view.dialog.OptionSelectDialog
import com.bvc.ordering.view.inflate.CategoryAdapter
import com.bvc.ordering.view.inflate.GridAdapter
import com.bvc.ordering.view.inflate.SubCategoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
                                if (item.productOption.size > 1) {
                                    val dialog =
                                        OptionSelectDialog(item) { selectProduct ->
                                            viewModel.addToCart(selectProduct)
                                        }
                                    dialog.show(childFragmentManager, "OptionSelectDialog")
                                } else {
                                    viewModel.addToCart(item)
                                }
                            }
                        },
                    )
            }
            icCartCount.ivCartDelete.setOnClickListener {
                viewModel.clearCart()
            }
            icCartCount.clCartRoot.setOnClickListener {
                findNavController().navigate(CartFragment::class.java.name)
            }
        }
    }

    override fun handleViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.apply {
                    launch {
                        category.collect { list ->
                            // 카테고리 UI 갱신
                            binding?.icOrder?.rvInflateCategory?.adapter?.let { adapter ->
                                if (adapter is CategoryAdapter<*>) {
                                    (adapter as CategoryAdapter<CategoryEntity>).submitList(list.toList())
                                }
                            }
                            getSubCategory(list.find { it.selected }?.id ?: "")
                        }
                    }
                    launch {
                        subCategory.collect { subCategory ->
                            // 상품 UI 갱신
                            binding?.icOrder?.rvInflateSubCategory?.adapter?.let { adapter ->
                                if (adapter is SubCategoryAdapter<*>) {
                                    (adapter as SubCategoryAdapter<SubCategoryEntity>).submitList(subCategory.toList())
                                }
                            }
                            getProducts(subCategory.find { it.selected }?.id ?: "")
                        }
                    }
                    launch {
                        product.collect { product ->
                            // 장바구니 UI 갱신
                            binding?.icOrder?.rvInflateGrid?.adapter?.let { adapter ->
                                if (adapter is GridAdapter<*>) {
                                    (adapter as GridAdapter<ProductEntity>).submitList(product.toList())
                                }
                            }
                        }
                    }
                    launch {
                        cartData.collect { cartData ->
                            binding?.icCartCount?.root?.isVisible = cartData.isNotEmpty()
                            binding?.icCartCount?.tvCartCount?.text = "${cartData.sumOf { it.quantity }}개"
                            val totalPrice = cartData.sumOf { it.getTotalPrice() }
                            binding?.icCartCount?.tvCartPrice?.text = "${Util.myFormatter(totalPrice)}"
                        }
                    }
                }
            }
        }
    }
}
