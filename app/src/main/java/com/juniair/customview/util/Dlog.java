package com.juniair.customview.util;

import android.util.Log;

import com.juniair.customview.app.AppController;

/**
 * Created by juniair on 2016-08-05.
 */
public class Dlog {

    static final String TAG = "Juniair.Kim";


    /** Log Level Error **/
    public static final void e(String message) {
        if (AppController.DEBUG)Log.e(TAG, buildLogMsg(message));
    }
    /** Log Level Warning **/
    public static final void w(String message) {
        if (AppController.DEBUG)Log.w(TAG, buildLogMsg(message));
    }
    /** Log Level Information **/
    public static final void i(String message) {
        if (AppController.DEBUG) Log.i(TAG, buildLogMsg(message));
    }
    /** Log Level Debug **/
    public static final void d(String message) {
        if (AppController.DEBUG)Log.d(TAG, buildLogMsg(message));
    }
    /** Log Level Verbose **/
    public static final void v(String message) {
        if (AppController.DEBUG)Log.v(TAG, buildLogMsg(message));
    }


    public static String buildLogMsg(String message) {

        StackTraceElement ste = Thread.currentThread().getStackTrace()[4];

        StringBuilder sb = new StringBuilder();

        sb.append("[");
        sb.append(ste.getFileName().replace(".java", ""));
        sb.append("::");
        sb.append(ste.getMethodName());
        sb.append("(Line:");
        sb.append(ste.getLineNumber());
        sb.append(")");
        sb.append("]");
        sb.append(message);

        return sb.toString();

    }

}
