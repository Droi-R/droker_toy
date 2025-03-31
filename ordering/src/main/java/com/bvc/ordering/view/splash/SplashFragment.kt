package com.bvc.ordering.view.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bvc.ordering.R
import com.bvc.ordering.base.BaseFragment
import com.bvc.ordering.databinding.FragmentSplashBinding
import com.bvc.ordering.view.splash.login.LoginFragment

class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_splash
    private val viewModel: SplashViewModel by viewModels()

    override fun init(savedInstanceState: Bundle?) {
        handleLiveData()
        binding?.apply {
            ivIntroLogo.setOnClickListener {
                // loginFrament 로 이동
                findNavController().navigate(LoginFragment::class.java.name)
//                requireActivity()
//                    .findNavController(R.id.fragment_container_view)
//                    .navigate(LoginFragment::class.java.name)
            }
        }
    }

    fun handleLiveData() {
    }

    override fun oneClick(
        v: View,
        position: Int,
    ) {
    }
}
