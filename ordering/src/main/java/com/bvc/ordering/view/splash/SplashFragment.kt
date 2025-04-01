package com.bvc.ordering.view.splash

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bvc.ordering.R
import com.bvc.ordering.base.BaseFragment
import com.bvc.ordering.databinding.FragmentSplashBinding
import com.bvc.ordering.view.main.MainActivity
import com.bvc.ordering.view.splash.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_splash
    private val viewModel: SplashViewModel by viewModels()

    override fun init(savedInstanceState: Bundle?) {
        binding?.apply {
            vm = viewModel
        }
    }

    override fun handleViewModel() {
        viewModel.apply {
            action.observe(viewLifecycleOwner) {
                if (it) {
                    // 로그인 되어있으면 메인으로 이동
                    MainActivity.startActivity(requireActivity())
                } else {
                    // loginFrament 로 이동
                    findNavController().navigate(LoginFragment::class.java.name)
                    //                requireActivity()
                    //                    .findNavController(R.id.fragment_container_view)
                    //                    .navigate(LoginFragment::class.java.name)
                }
            }
        }
    }
}
