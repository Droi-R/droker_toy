package com.bvc.ordering.view.materials

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bvc.domain.log
import com.bvc.domain.model.CategoryEntity
import com.bvc.domain.model.SmartOrderEntity
import com.bvc.domain.model.SubCategoryEntity
import com.bvc.ordering.R
import com.bvc.ordering.base.BaseFragment
import com.bvc.ordering.databinding.FragmentMaterialDetailBinding
import com.bvc.ordering.ui.HorizontalSpaceItemDecoration
import com.bvc.ordering.ui.VerticalSpaceItemDecoration
import com.bvc.ordering.ui.event.collectNonEmpty
import com.bvc.ordering.view.components.ChangeStockDialog
import com.bvc.ordering.view.inflate.CategoryAdapter
import com.bvc.ordering.view.inflate.GridAdapter
import com.bvc.ordering.view.inflate.SubCategoryAdapter
import com.bvc.ordering.view.inflate.TopCategoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MaterialDetailFragment : BaseFragment<FragmentMaterialDetailBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_material_detail
    private val viewModel: MaterialDetailViewModel by viewModels()

    override fun init(savedInstanceState: Bundle?) {
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel

            // 최상단 카테고리
            rvTopCategory.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(HorizontalSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.d_600)))
                adapter =
                    TopCategoryAdapter(
                        object : TopCategoryAdapter.OnItemClickListener<SubCategoryEntity> {
                            override fun onItemClick(item: SubCategoryEntity) {
                                viewModel.updateTopCategory(item)
                            }
                        },
                    )
            }

            rvSmartOrder.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                addItemDecoration(VerticalSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.d_1000)))
                adapter =
                    SmartOrderAdapter(
                        object : SmartOrderAdapter.OnItemClickListener {
                            override fun onItemDeleteClick(item: SmartOrderEntity) {
                                viewModel.deleteSmartOrder(item)
                            }

                            override fun onItemChangeClick(item: SmartOrderEntity) {
                                binding?.composeDialogContainer?.apply {
                                    layoutParams =
                                        ConstraintLayout.LayoutParams(
                                            ConstraintLayout.LayoutParams.MATCH_PARENT,
                                            ConstraintLayout.LayoutParams.MATCH_PARENT,
                                        )
                                    visibility = View.VISIBLE
                                    setContent {
                                        ChangeStockDialog(
                                            materialsEntity = item.material,
                                            onDismiss = {
                                                log.e("onDismiss")
                                                setContent {}
                                                visibility = View.GONE
                                            },
                                            onConfirm = { newStock ->
                                                log.e("onConfirm: $newStock")
                                                viewModel.changeSmartOrder(item, newStock)
                                                setContent {}
                                                visibility = View.GONE
                                            },
                                        )
                                    }
                                }
                            }

                            override fun onItemPlusClick(item: SmartOrderEntity) {
                                viewModel.plusSmartOrder(item)
                            }

                            override fun onItemMinusClick(item: SmartOrderEntity) {
                                viewModel.minusSmartOrder(item)
                            }
                        },
                    )
            }

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
//                                viewModel.updateSubCategory(item)
                            }
                        },
                    )
            }
            icOrder.rvInflateGrid.apply {
                layoutManager = GridLayoutManager(context, 3)
                addItemDecoration(HorizontalSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.d_1000)))
                addItemDecoration(VerticalSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.d_1000)))
                adapter =
                    GridAdapter {
                        onMaterialsClick { material ->
                        }
                    }
            }
            clMaterialsOrderList.setOnClickListener {
                viewModel.selectSmartOrder(true)
            }
            clMaterialStockIn.setOnClickListener {
                viewModel.selectSmartOrder(false)
            }

            icOrder.ivInflate.visibility = View.GONE
            icOrder.rvInflateSubCategory.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun handleViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.apply {
                    topCategory.collectNonEmpty(viewLifecycleOwner) { list ->
                        // 카테고리 UI 갱신
                        binding?.rvTopCategory?.adapter?.let { adapter ->
                            if (adapter is TopCategoryAdapter<*>) {
                                (adapter as TopCategoryAdapter<SubCategoryEntity>).submitList(list.toList())
                            }
                        }
                        binding?.icOrder?.rvInflateSubCategory?.visibility = View.GONE
                        val item = list.find { it.selected }
                        when (item?.name) {
                            getString(R.string.materials_status) -> {
                                viewModel.setVisibleSmartOrder(false)
                                viewModel.getCategory("")
                            }

                            getString(R.string.materials_smart_order) -> {
                                viewModel.setVisibleSmartOrder(true)
                                binding?.tvMaterialsSubTitle?.text =
                                    getString(R.string.materials_smart_order_desc)
                                viewModel.getSmartOrder()
                            }

                            getString(R.string.materials_consumption) -> {
                                binding?.icOrder?.rvInflateSubCategory?.visibility = View.VISIBLE
                                viewModel.setVisibleSmartOrder(false)
                                viewModel.getCategory("")
                            }

                            getString(R.string.materials_order_status) -> {
                                viewModel.setVisibleSmartOrder(false)
                            }

                            else -> {
                            }
                        }
                    }
                    category.collectNonEmpty(viewLifecycleOwner) { category ->
                        // 카테고리 UI 갱신
                        binding?.icOrder?.rvInflateCategory?.adapter?.let { adapter ->
                            if (adapter is CategoryAdapter<*>) {
                                (adapter as CategoryAdapter<CategoryEntity>).submitList(
                                    category.toList(),
                                )
                            }
                        }
                        val item = topCategory.value.find { it.selected }
                        when (item?.name) {
                            getString(R.string.materials_status) -> {
                                viewModel.getMaterials(item.mainCategoryId)
                            }
                            getString(R.string.materials_consumption) -> {
                                viewModel.getSubCategory(item.mainCategoryId)
                            }

                            else -> {
                            }
                        }
                        // TODO 여기서 상품과 재료현황 나누자
                    }
                    subCategory.collectNonEmpty(viewLifecycleOwner) { subCategory ->
                        log.e("subCategory: $subCategory")
                        // 상품 UI 갱신
                        binding?.icOrder?.rvInflateSubCategory?.adapter?.let { adapter ->
                            if (adapter is SubCategoryAdapter<*>) {
                                (adapter as SubCategoryAdapter<SubCategoryEntity>).submitList(
                                    subCategory.toList(),
                                )
                            }
                        }
                        getProducts(subCategory.find { it.selected })
                    }

                    material.collectNonEmpty(viewLifecycleOwner) { materials ->
                        log.e("materials: $materials")
                        binding?.icOrder?.rvInflateGrid?.adapter?.let { adapter ->
                            if (adapter is GridAdapter) {
                                adapter.submitList(materials.toList())
                            }
                        }
                    }

                    smartOrder.collectNonEmpty(viewLifecycleOwner) { materials ->
                        log.e("smartOrder: $materials")
                        binding?.rvSmartOrder?.adapter?.let { adapter ->
                            if (adapter is SmartOrderAdapter) {
                                adapter.submitList(materials.toList())
                            }
                        }
                    }
                    product.collectNonEmpty(viewLifecycleOwner) { product ->
                        binding?.icOrder?.rvInflateGrid?.adapter?.let { adapter ->
                            if (adapter is GridAdapter) {
                                adapter.submitList(product.toList())
                            }
                        }
                    }
                    smartOrderSelect.collect {
                        if (it) {
                            binding?.clMaterialsOrderList?.setBackgroundResource(R.drawable.r15_17c2c9)
                            binding?.tvMaterialsOrderList?.setTextColor(
                                resources.getColor(
                                    R.color.white,
                                    null,
                                ),
                            )
                            binding?.clMaterialStockIn?.setBackgroundResource(R.drawable.r15_f6f6f6)
                            binding?.tvMaterialStockIn?.setTextColor(
                                resources.getColor(
                                    R.color.bvc_666E89,
                                    null,
                                ),
                            )
                        } else {
                            binding?.clMaterialsOrderList?.setBackgroundResource(R.drawable.r15_f6f6f6)
                            binding?.tvMaterialsOrderList?.setTextColor(
                                resources.getColor(
                                    R.color.bvc_666E89,
                                    null,
                                ),
                            )
                            binding?.clMaterialStockIn?.setBackgroundResource(R.drawable.r15_17c2c9)
                            binding?.tvMaterialStockIn?.setTextColor(
                                resources.getColor(
                                    R.color.white,
                                    null,
                                ),
                            )
                        }
                    }
                }
            }
        }
    }
}
