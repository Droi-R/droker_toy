package com.bvc.ordering.view.cart

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.bvc.ordering.R
import com.bvc.ordering.base.BaseFragment
import com.bvc.ordering.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_cart
    private val viewModel: CartViewModel by viewModels()

    override fun init(savedInstanceState: Bundle?) {
        binding?.apply {
            vm = viewModel
        }
    }

    override fun handleViewModel() {
        viewModel.apply {
            category.observe(viewLifecycleOwner) {
            }
            subCategory.observe(viewLifecycleOwner) {
            }
            product.observe(viewLifecycleOwner) {
            }
            cartData.observe(viewLifecycleOwner) {
            }
        }
    }
}
