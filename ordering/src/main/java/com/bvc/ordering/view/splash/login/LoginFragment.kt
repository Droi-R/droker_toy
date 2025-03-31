package com.bvc.ordering.view.splash.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.bvc.ordering.R
import com.bvc.ordering.base.BaseFragment
import com.bvc.ordering.databinding.FragmentLoginBinding
import com.bvc.ordering.view.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_login
    private val viewModel: LoginViewModel by viewModels()

    override fun init(savedInstanceState: Bundle?) {
        binding?.apply {
            vm = viewModel
        }
    }

    override fun handleViewModel() {
        viewModel.apply {
            action.observe(viewLifecycleOwner) {
                if (it) {
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        }
    }
}
