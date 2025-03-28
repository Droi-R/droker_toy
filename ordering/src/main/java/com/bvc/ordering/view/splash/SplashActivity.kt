package com.bvc.ordering.view.splash

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.bvc.domain.log
import com.bvc.ordering.R
import com.bvc.ordering.base.BaseActivity
import com.bvc.ordering.databinding.ActivitySplashBinding
import com.bvc.ordering.ksnet.KsnetUtil
import com.bvc.ordering.ksnet.TransactionData
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()

    override fun _init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        handleLiveData()
    }

    fun handleLiveData() {
        viewModel.run {
            requestTelegram.observe(this@SplashActivity) { mRequestTelegram ->
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

    val startForResultForTelegram =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val trData = TransactionData()
            val resultCode = result.resultCode
            val data = result.data
            if (resultCode == RESULT_OK && data != null) {
                Toast.makeText(this, "성공", Toast.LENGTH_LONG).show()
                val recvByte: ByteArray = data.getByteArrayExtra("responseTelegram") ?: byteArrayOf()
                // Log.e("KSCAT_INTENT_RESULT", HexDump.dumpHexString(recvByte));
                // KsnetUtil.byteTo20ByteLog(recvByte, "");
                log.e("Recv Telegram \n ${KsnetUtil.HexDump.dumpHexString(recvByte)}")

                val str: String = KsnetUtil.HexDump.dumpHexString(recvByte)
                log.e("str : $str")
                // 승인번호 승인일자 가져오기
                val apprNo = ByteArray(12)
                System.arraycopy(recvByte, 94, apprNo, 0, 12)
                val apprDate = ByteArray(6)
                System.arraycopy(recvByte, 49, apprDate, 0, 6)

                log.e("String(apprNo) : ${String(apprNo)}")
                log.e("String(apprNo) : ${String(apprDate)}")

//            val intent: Intent = Intent(this@CardActivity, ResultActivity::class.java)
//            intent.putExtra("PayType", "CARD")
//            intent.putExtra("resData", recvByte)
//            intent.putExtra("totAmt", mTotAmt)
//            intent.putExtra("VAT", mVat)
//            intent.putExtra("supplyAmt", mSupAmt)
//            startActivity(intent)
            } else if (resultCode == RESULT_CANCELED) {
                if (data != null) {
                    log.e("result" + data.getIntExtra("result", 1).toString())
                    val recvByte: ByteArray = data.getByteArrayExtra("responseTelegram") ?: byteArrayOf()
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
