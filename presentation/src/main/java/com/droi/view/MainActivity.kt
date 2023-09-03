package com.droi.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentManager
import com.droi.BaseActivity
import com.droi.R
import com.droi.cp.BaseCompose
import com.droi.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

//    private lateinit var binding: ActivityMainBinding
    var fragmentManager: FragmentManager? = null

    @Inject lateinit var firstFragment: FirstFragment
    private val model: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        binding.lifecycleOwner = this
    }

    override fun _init() {
        model.requsetUsers()
        model.timerJob.start()
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background,
            ) {

            }
//            MaterialTheme {
//                Surface(color = Color.White) {
//                    Column {
//                        BaseCompose().demoScreen()
//                        BaseCompose().funtionA()
//                    }
//                }
//            }
        }
    }
    private fun initTap() {
        fragmentManager = supportFragmentManager
//        if (firstFragment == null) {
//            firstFragment = FirstFragment()
        fragmentManager!!.beginTransaction().add(R.id.fl_main, firstFragment!!)
            .commitAllowingStateLoss()
//        }
    }
}
