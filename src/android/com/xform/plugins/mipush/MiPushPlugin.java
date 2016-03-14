package com.xform.plugins.mipush;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import com.xiaomi.mipush.sdk.MiPushClient;

import android.widget.Toast;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by nirui on 16/3/12.
 */
public class MiPushPlugin extends CordovaPlugin {

    private static CallbackContext cContext = null;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        PackageManager packageManager = cordova.getActivity().getApplicationContext().getPackageManager();
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = packageManager.getApplicationInfo(cordova.getActivity().getApplicationContext().getPackageName(), 128);
            String appId = applicationInfo.metaData.getString("miPush_appId");
            String appKey = applicationInfo.metaData.getString("miPush_appKey");
            regist(appId, appKey);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(cordova.getActivity().getApplicationContext(), "推送服务注册失败", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if("miInit".equals(action)) {
            cContext = callbackContext;
            final String appId = args.getString(0);
            final String appKey = args.getString(1);
            return true;
        }
        return false;
    }

    private void regist(String appId, String appKey) {
        MiPushClient.registerPush(cordova.getActivity().getApplicationContext(), appId, appKey);
    }

    public static CallbackContext getContext() {
        return cContext;
    }
}
