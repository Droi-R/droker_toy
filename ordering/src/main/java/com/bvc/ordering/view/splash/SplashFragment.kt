package com.bvc.ordering.view.splash

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bvc.ordering.R
import com.bvc.ordering.base.BaseFragment
import com.bvc.ordering.databinding.FragmentSplashBinding
import com.bvc.ordering.view.main.MainActivity
import com.bvc.ordering.view.splash.login.LoginFragment
import com.bvc.ordering.view.splash.select.SelectFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_splash
    private val viewModel: SplashViewModel by viewModels()

    override fun init(savedInstanceState: Bundle?) {
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }
    }

    override fun handleViewModel() {
        viewModel.apply {
            action.observe(viewLifecycleOwner) {
                when (it) {
                    1 -> {
                        findNavController().navigate(SelectFragment::class.java.name)
                    }
                    2 -> {
                        MainActivity.startActivity(requireActivity())
                    }
                    else -> {
                        findNavController().navigate(LoginFragment::class.java.name)
                    }
                }
            }
        }
    }
}
