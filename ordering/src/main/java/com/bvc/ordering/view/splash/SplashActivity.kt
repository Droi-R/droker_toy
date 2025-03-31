package com.bvc.ordering.view.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.bvc.ordering.R
import com.bvc.ordering.base.BaseActivity
import com.bvc.ordering.databinding.ActivitySplashBinding
import com.bvc.ordering.view.splash.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun init(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.lifecycleOwner = this
        if (savedInstanceState == null) {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.splash_container_view) as NavHostFragment
            val navController = navHostFragment.navController
            val navGraph =
                navController.createGraph(
                    startDestination = SplashFragment::class.java.name,
                    route = "splash_graph",
                ) {
                    fragment<LoginFragment>(route = LoginFragment::class.java.name)
                    fragment<SplashFragment>(route = SplashFragment::class.java.name)
                }
            navController.setGraph(
                graph = navGraph,
                startDestinationArgs = null,
            )
        }
    }
}
