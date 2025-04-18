package com.bvc.ordering.util

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.bvc.domain.log
import com.bvc.ordering.App.Companion.getInstance
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

object Utils {
    fun showToast(
        context: Context,
        message: String,
    ) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun getDateFormatTime(str: String): String? {
        log.e("str  :  $str")
        var time: String? = null
        val ori = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
        ori.timeZone = TimeZone.getTimeZone("UTC")
        val tran = SimpleDateFormat("yyyy.MM")
        tran.timeZone = TimeZone.getDefault()
        try {
            val oDate = ori.parse(str)
            time = tran.format(oDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return time
    }

    fun showToast(str: String?) {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            val toast = Toast.makeText(getInstance(), str, Toast.LENGTH_SHORT)
            try {
                toast.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun myFormatter(num: Double?): String {
        try {
            return String.format(Locale.getDefault(), "%,.1f", num)
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }

    fun myFormatter_d2(num: Double?): String {
        try {
            return String.format(Locale.getDefault(), "%,.2f", num)
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }

    fun myFormatter(num: Float?): String {
        try {
            return String.format(Locale.getDefault(), "%,.1f", num)
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }

    fun myFormatter(num: Int): String {
        try {
            return String.format(Locale.getDefault(), "%,d", num)
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    } //    public static String myFormatter_round(String num) {
    //        try {
    //            String str = String.format("%.d",num);
    //            Logger.loge("num   :  "+num);
    //            return String.format(Locale.getDefault(), "%,d", str);
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //            return "";
    //        }
    //    }
    //    public static String myFormatter(String num) {
    //        Logger.loge("num   :  "   +  num);
    //        if (num != null && !num.equals("")&& !num.equals("null")) {
    //            Long n = new kUtil().stringToLong(num);
    //            try {
    //                return String.format(Locale.getDefault(), "%,d", n);
    //            } catch (Exception e) {
    //                e.printStackTrace();
    //                return "";
    //            }
    //        } else {
    //            return "";
    //        }
    //    }
}
