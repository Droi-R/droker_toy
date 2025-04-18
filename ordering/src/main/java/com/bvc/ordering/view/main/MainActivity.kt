package com.bvc.ordering.view.main

import android.app.Activity
import android.app.AlertDialog
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import androidx.navigation.navOptions
import com.bvc.domain.log
import com.bvc.ordering.R
import com.bvc.ordering.base.BaseActivity
import com.bvc.ordering.databinding.ActivityMainBinding
import com.bvc.ordering.ksnet.KsnetUtil
import com.bvc.ordering.ksnet.TransactionData
import com.bvc.ordering.view.cart.CartFragment
import com.bvc.ordering.view.consumption.ConsumptionFragment
import com.bvc.ordering.view.materialdetail.MaterialDetailFragment
import com.bvc.ordering.view.materials.MaterialsFragment
import com.bvc.ordering.view.order.OrderFragment
import com.bvc.ordering.view.table.TableCartFragment
import com.bvc.ordering.view.table.TableFragment
import com.bvc.ordering.view.table.TableOrderFragment
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    companion object {
        fun startActivity(activity: Activity?) {
            val intent = Intent(activity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            activity?.startActivity(intent)
            activity?.finish()
        }
    }

    override fun init(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_container_view) as NavHostFragment
        val navController = navHostFragment.navController
        binding.apply {
            lifecycleOwner = this@MainActivity
            vm = viewModel
            tlMainBottom.run {
                addTab(
                    newTab().apply {
                        setCustomTabTitles(
                            this,
                            getString(R.string.main_tab_order),
                            R.drawable.tab_order,
                        )
                    },
                )
                addTab(
                    newTab().apply {
                        setCustomTabTitles(
                            this,
                            getString(R.string.main_tab_table),
                            R.drawable.tab_table,
                        )
                    },
                )
                addTab(
                    newTab().apply {
                        setCustomTabTitles(
                            this,
                            getString(R.string.main_tab_history),
                            R.drawable.tab_history,
                        )
                    },
                )
                addTab(
                    newTab().apply {
                        setCustomTabTitles(
                            this,
                            getString(R.string.main_tab_materials),
                            R.drawable.tab_materials,
                        )
                    },
                )
                addTab(
                    newTab().apply {
                        setCustomTabTitles(
                            this,
                            getString(R.string.main_tab_setting),
                            R.drawable.tab_setting,
                        )
                    },
                )
                addOnTabSelectedListener(
                    object : TabLayout.OnTabSelectedListener {
                        override fun onTabSelected(tab: TabLayout.Tab?) {
                            tab?.customView?.findViewById<TextView>(R.id.tab_text)?.text?.let { tabText ->
                                when (tabText) {
                                    getString(R.string.main_tab_order) -> {
                                        val popped =
                                            navController.popBackStack(
                                                OrderFragment::class.java.name,
                                                false,
                                            )
                                        if (!popped) {
                                            navController.navigate(
                                                OrderFragment::class.java.name,
                                                navOptions {
                                                    popUpTo(OrderFragment::class.java.name) {
                                                        inclusive = false
                                                        saveState = true
                                                    }
                                                    restoreState = true
                                                    launchSingleTop = true
                                                },
                                            )
                                        }
                                    }

                                    getString(R.string.main_tab_table) -> {
                                        navController.navigate(TableFragment::class.java.name)
                                    }

                                    getString(R.string.main_tab_history) -> {
                                    }

                                    getString(R.string.main_tab_materials) -> {
                                        navController.navigate(MaterialsFragment::class.java.name)
                                    }

                                    getString(R.string.main_tab_setting) -> {
                                    }
                                }
                            }
                        }

                        override fun onTabUnselected(tab: TabLayout.Tab?) {
                        }

                        override fun onTabReselected(tab: TabLayout.Tab?) {
                        }
                    },
                )
            }
        }

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            val layoutParams =
                binding.mainContainerView.layoutParams as ViewGroup.MarginLayoutParams
            when (destination.route) {
                MaterialDetailFragment::class.java.name,
                ConsumptionFragment::class.java.name,
                TableOrderFragment::class.java.name,
                TableCartFragment::class.java.name,
                CartFragment::class.java.name,
                -> {
                    layoutParams.topMargin =
                        TypedValue
                            .applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP,
                                0f,
                                resources.displayMetrics,
                            ).toInt()
                    binding.mainContainerView.layoutParams = layoutParams
                    binding.clMainTop.isVisible = false
                    binding.tlMainBottom.isVisible = false
                }

                else -> {
                    layoutParams.topMargin =
                        TypedValue
                            .applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP,
                                25f,
                                resources.displayMetrics,
                            ).toInt()
                    binding.mainContainerView.layoutParams = layoutParams
                    binding.clMainTop.isVisible = true
                    binding.tlMainBottom.isVisible = true
                }
            }
        }

        if (savedInstanceState == null) {
            val navGraph =
                navController.createGraph(
                    startDestination = OrderFragment::class.java.name,
                    route = "main_graph",
                ) {
                    fragment<OrderFragment>(route = OrderFragment::class.java.name)
                    fragment<CartFragment>(route = CartFragment::class.java.name)
                    fragment<TableFragment>(route = TableFragment::class.java.name)
                    fragment<TableOrderFragment>(route = TableOrderFragment::class.java.name)
                    fragment<TableCartFragment>(route = TableCartFragment::class.java.name)
                    fragment<MaterialsFragment>(route = MaterialsFragment::class.java.name)
                    fragment<MaterialDetailFragment>(route = MaterialDetailFragment::class.java.name)
                    fragment<ConsumptionFragment>(route = ConsumptionFragment::class.java.name)
                }
            navController.setGraph(
                graph = navGraph,
                startDestinationArgs = null,
            )
        }

        handleViewModel()
    }

    private fun handleViewModel() {
        viewModel.run {
            isBusiness.observe(this@MainActivity) {
                if (it) {
                    binding.ivMainBusiness.setBackgroundResource(R.drawable.r_17c2c9)
                } else {
                    binding.ivMainBusiness.setBackgroundResource(R.drawable.r_ff6d3b)
                }
            }

            requestTelegram.observe(this@MainActivity) { (mRequestTelegram, paymentEntity) ->
                // 결제 테스트
                log.e("mRequestTelegram: ${String(mRequestTelegram)}")
                val packageName = "com.ksnet.kscat_a"
                val className = "com.ksnet.kscat_a.PaymentIntentActivity"
                val packageManager: PackageManager = packageManager

                val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
                log.e("launchIntent: $launchIntent")
                if (launchIntent != null) {
                    // 앱이 설치된 경우 실행
                    val mIntent = Intent(Intent.ACTION_MAIN)
                    mIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    mIntent.addCategory(Intent.CATEGORY_LAUNCHER)
                    mIntent.setComponent(ComponentName(packageName, className))
                    mIntent.putExtra("Telegram", mRequestTelegram)
                    mIntent.putExtra("TelegramLength", mRequestTelegram.size)
                    startForResultForTelegram.launch(mIntent)
                } else {
                    // 앱이 설치되지 않은 경우 Play 스토어로 이동
                    try {
                        val storeIntent =
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=$packageName"),
                            )
                        storeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(storeIntent)
                    } catch (e: java.lang.Exception) {
                        // Play 스토어가 없을 경우 웹 브라우저로 이동
                        val webIntent =
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=$packageName"),
                            )
                        webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(webIntent)
                    }
                }
            }
        }
    }

    private fun setCustomTabTitles(
        tab: TabLayout.Tab,
        title: String,
        iconResId: Int,
    ) {
        val customView = layoutInflater.inflate(R.layout.custom_tab, null)
        customView.findViewById<ImageView>(R.id.tab_icon).setImageResource(iconResId)
        customView.findViewById<TextView>(R.id.tab_text).text = title
        tab.customView = customView
    }

    val startForResultForTelegram =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val trData = TransactionData()
            val resultCode = result.resultCode
            val data = result.data
            if (resultCode == RESULT_OK && data != null) {
                Toast.makeText(this, "성공", Toast.LENGTH_LONG).show()
                log.e("data: $data")
                val recvByte: ByteArray =
                    data.getByteArrayExtra("responseTelegram") ?: byteArrayOf()
//                KsnetUtil.byteTo20ByteLog(recvByte, "")
                log.e("Recv Telegram \n ${KsnetUtil.HexDump.dumpHexString(recvByte)}")

//                val trData = TransactionData()
//                trData.SetData(recvByte)
//                log.e("trData: $trData")

//                val responseData = KSNETResponseData.from(recvByte)
//                log.e("responseData: $responseData")

//                val str: String = KsnetUtil.HexDump.dumpHexString(recvByte)
//                log.e("str : $str")
//                // 승인번호 승인일자 가져오기
//                val apprNo = ByteArray(12)
//                System.arraycopy(recvByte, 94, apprNo, 0, 12)
//                val apprDate = ByteArray(6)
//                System.arraycopy(recvByte, 49, apprDate, 0, 6)
//
//                log.e("String(apprNo) : ${String(apprNo)}")
//                log.e("String(apprDate) : ${String(apprDate)}")

                // TODO 여기서 캡쳐 날리자
                viewModel.postCapture(
                    apprNo = String(apprNo),
                    apprDate = String(apprDate),
                )
            } else if (resultCode == RESULT_CANCELED) {
                if (data != null) {
                    log.e("result" + data.getIntExtra("result", 1).toString())
                    val recvByte: ByteArray =
                        data.getByteArrayExtra("responseTelegram") ?: byteArrayOf()
                    log.e("recvByte : \n" + KsnetUtil.HexDump.dumpHexString(recvByte))
                    trData.SetData(recvByte)

                    try {
                        val csMessage1 = String(trData.message1, charset("EUC-KR"))
                        val csMessage2 = String(trData.message2, charset("EUC-KR"))
                        val csNotice1 = String(trData.notice1, charset("EUC-KR"))
                        val csNotice2 = String(trData.notice2, charset("EUC-KR"))

                        val msg =
                            """
                            $csMessage1
                            $csMessage2
                            $csNotice1
                            $csNotice2
                            """.trimIndent()

                        AlertDialog
                            .Builder(this)
                            .setTitle("KSCAT_TEST")
                            .setMessage(msg)
                            .setCancelable(false)
                            .setPositiveButton(
                                "확인",
                            ) { dialog, which -> }
                            .show()

                        // Log.e("KSCAT_INTENT_RESULT", HexDump.dumpHexString(recvByte));
                        // Log.e("Recv Telegram \n", KsnetUtil.HexDump.dumpHexString(recvByte));
                        //                    val tv = findViewById<TextView>(R.id.cardsvtv2)
                        val str: String = KsnetUtil.HexDump.dumpHexString(recvByte)
                        log.e("str: $str")
                        //                    tv.text = str
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    Toast.makeText(this, "앱 호출 실패", Toast.LENGTH_LONG).show()
                }
            }
        }
}
