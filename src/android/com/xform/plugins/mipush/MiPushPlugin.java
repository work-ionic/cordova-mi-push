package com.xform.plugins.mipush;

import android.content.Context;
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
 * Created by Ani on 16/3/12.
 */
public class MiPushPlugin extends CordovaPlugin {

    private static CallbackContext cContext = null;
    private static String appId;
    private static String appKey;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        PackageManager packageManager = cordova.getActivity().getApplicationContext().getPackageManager();
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = packageManager.getApplicationInfo(cordova.getActivity().getApplicationContext().getPackageName(), 128);
            appId = applicationInfo.metaData.getString("miPush_appId");
            appKey = applicationInfo.metaData.getString("miPush_appKey");
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(cordova.getActivity().getApplicationContext(), "推送服务初始化错误", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if("register".equals(action)) {
            register(cordova.getActivity().getApplicationContext(), appId, appKey);
            return true;
        }
        if("unregister".equals(action)) {
            unregister(cordova.getActivity().getApplicationContext());
            return true;
        }
        return false;
    }

    private void register(Context context, String appId, String appKey) {
        MiPushClient.registerPush(context, appId, appKey);
    }

    private void unregister(Context context) {
        MiPushClient.unregisterPush(context);
    }

    private void setAlias(Context context, String alias, String category) {
        MiPushClient.setAlias(context, alias, category);
    }

    private void unsetAlias(Context context, String alias, String category) {
        MiPushClient.unsetAlias(context, alias, category);
    }

    public static CallbackContext getContext() {
        return cContext;
    }
}
