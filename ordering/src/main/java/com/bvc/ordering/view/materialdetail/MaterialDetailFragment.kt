package com.bvc.ordering.view.materialdetail

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bvc.domain.log
import com.bvc.domain.model.ProductEntity
import com.bvc.ordering.R
import com.bvc.ordering.base.BaseFragment
import com.bvc.ordering.databinding.FragmentMaterialDetailBinding
import com.bvc.ordering.ui.VerticalSpaceItemDecoration
import com.bvc.ordering.view.components.ChangeConsumptionDialog
import com.bvc.ordering.view.main.MainViewModel
import com.bvc.ordering.view.materials.MaterialsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MaterialDetailFragment : BaseFragment<FragmentMaterialDetailBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_material_detail
    private val viewModel: MaterialDetailViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private var eventJob: Job? = null

    override fun init(savedInstanceState: Bundle?) {
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel

            rvMaterialDeteil.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                addItemDecoration(VerticalSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.d_600)))
                adapter =
                    MaterialDetailAdapter(
                        object : MaterialDetailAdapter.OnItemClickListener {
                            override fun onItemClick(item: ProductEntity) {
                                binding?.composeDialogContainer?.apply {
                                    layoutParams =
                                        ConstraintLayout.LayoutParams(
                                            ConstraintLayout.LayoutParams.MATCH_PARENT,
                                            ConstraintLayout.LayoutParams.MATCH_PARENT,
                                        )
                                    visibility = View.VISIBLE
                                    setContent {
                                        ChangeConsumptionDialog(
                                            productEntity = item,
                                            onDismiss = {
                                                log.e("onDismiss")
                                                setContent {}
                                                visibility = View.GONE
                                            },
                                            onConfirm = { newStock, unit ->
                                                log.e("onConfirm: $newStock")
//                                                viewModel.changeSmartOrder(item, newStock)
                                                setContent {}
                                                visibility = View.GONE
                                            },
                                        )
                                    }
                                }
                            }
                        },
                    )
            }

            tvMaterialDeteilTopbar.setOnClickListener {
                findNavController().navigate(MaterialsFragment::class.java.name)
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun handleViewModel() {
        eventJob =
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    mainViewModel.matarialEventFlow.collect { event ->
                        event.getContentIfNotHandled()?.let { material ->
                            viewModel.setMaterial(material)
                        }
                    }
                }
            }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.apply {
                    productEntity.collect { product ->
                        (binding?.rvMaterialDeteil?.adapter as MaterialDetailAdapter).submitList(
                            product,
                        )
                    }
                }
            }
        }
    }
}
