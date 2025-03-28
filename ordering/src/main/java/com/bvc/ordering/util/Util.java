package com.bvc.ordering.util;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bvc.domain.log;
import com.bvc.ordering.App;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Util extends AppCompatActivity {
    public static String getDateFormatTime(String str) {
        log.e("str  :  "   +  str);
        String time = null;
        SimpleDateFormat ori = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        ori.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat tran = new SimpleDateFormat("yyyy.MM");
        tran.setTimeZone(TimeZone.getDefault());
        try {
            Date oDate = ori.parse(str);
            time = tran.format(oDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static void showToast(String str) {

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast toast;
                toast = Toast.makeText(App.Companion.getInstance(), str, Toast.LENGTH_SHORT);
                try {
                    toast.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static String myFormatter(Double num) {
        try {
            return String.format(Locale.getDefault(), "%,.1f", num);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String myFormatter_d2(Double num) {
        try {
            return String.format(Locale.getDefault(), "%,.2f", num);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String myFormatter(Float num) {
        try {
            return String.format(Locale.getDefault(), "%,.1f", num);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String myFormatter(int num) {
        try {
            return String.format(Locale.getDefault(), "%,d", num);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

//    public static String myFormatter_round(String num) {
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


