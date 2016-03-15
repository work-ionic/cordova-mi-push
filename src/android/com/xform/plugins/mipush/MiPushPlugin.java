package com.xform.plugins.mipush;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import com.xiaomi.mipush.sdk.MiPushClient;

import android.widget.Toast;

import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

/**
 * Created by Ani on 16/3/12.
 */
public class MiPushPlugin extends CordovaPlugin {

    private static CallbackContext listenContext = null;
    private static CallbackContext commandResultContext = null;
    private static String appId;
    private static String appKey;

    private static String TAG = "com.xform.plugins.mipush";
    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        PackageManager packageManager = cordova.getActivity().getPackageManager();
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = packageManager.getApplicationInfo(cordova.getActivity().getPackageName(), PackageManager.GET_META_DATA);
                appId = applicationInfo.metaData.getString("miPush_appId").split("str_")[1];
                appKey = applicationInfo.metaData.getString("miPush_appKey").split("str_")[1];
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(cordova.getActivity().getApplicationContext(), "推送服务初始化错误", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if("register".equals(action)) {
            commandResultContext = callbackContext;
            register(cordova.getActivity().getApplicationContext(), appId, appKey);
            return true;
        }
        if("unregister".equals(action)) {
            commandResultContext = callbackContext;
            unregister(cordova.getActivity().getApplicationContext());
            return true;
        }
        if("setAlias".equals(action)) {
            commandResultContext = callbackContext;
            String alias = args.getString(0);
            setAlias(cordova.getActivity().getApplicationContext(), alias, null);
            return true;
        }
        if("unsetAlias".equals(action)) {
            commandResultContext = callbackContext;
            String alias = args.getString(0);
            unsetAlias(cordova.getActivity().getApplicationContext(), alias, null);
            return true;
        }
        if("setUserAccount".equals(action)) {
            commandResultContext = callbackContext;
            String userAccount = args.getString(0);
            setUserAccount(cordova.getActivity().getApplicationContext(), userAccount, null);
            return true;
        }
        if("unsetUserAccount".equals(action)) {
            commandResultContext = callbackContext;
            String userAccount = args.getString(0);
            unsetUserAccount(cordova.getActivity().getApplicationContext(), userAccount, null);
            return true;
        }
        if("subscribe".equals(action)) {
            commandResultContext = callbackContext;
            String topic = args.getString(0);
            subscribe(cordova.getActivity().getApplicationContext(), topic, null);
            return true;
        }
        if("unsubscribe".equals(action)) {
            commandResultContext = callbackContext;
            String topic = args.getString(0);
            unsubscribe(cordova.getActivity().getApplicationContext(), topic, null);
            return true;
        }
        if("pausePush".equals(action)) {
            commandResultContext = callbackContext;
            pausePush(cordova.getActivity().getApplicationContext(), null);
            return true;
        }
        if("resumePush".equals(action)) {
            commandResultContext = callbackContext;
            resumePush(cordova.getActivity().getApplicationContext(), null);
            return true;
        }
        if("setAcceptTime".equals(action)) {
            commandResultContext = callbackContext;
            int startHour = args.getInt(0);
            int startMin = args.getInt(1);
            int endHour = args.getInt(2);
            int endMin = args.getInt(3);
            if(checkAcceptTime(startHour, startMin, endHour, endMin)) {
                setAcceptTime(cordova.getActivity().getApplicationContext(), startHour, startMin, endHour, endMin, null);
            } else {
                // TODO: 16/3/14 Error Callback 
            }
            return true;
        }
        if("getAllAlias".equals(action)) {
            List<String> alias = getAllAlias(cordova.getActivity().getApplicationContext());
            // TODO: 16/3/14 return all alias
            return true;
        }
        if("getAllTopic".equals(action)) {
            List<String> topic = getAllTopic(cordova.getActivity().getApplicationContext());
            // TODO: 16/3/14 return all topic
            return true;
        }
        if("reportMessageClicked".equals(action)) {
            commandResultContext = callbackContext;
            String msgid = args.getString(0);
            reportMessageClicked(cordova.getActivity().getApplicationContext(), msgid);
            return true;
        }
        if("clearNotification".equals(action)) {
            commandResultContext = callbackContext;
            int notifyId = args.getInt(0);
            clearNotification(cordova.getActivity().getApplicationContext(), notifyId);
            return true;
        }
        if("clearAllNotification".equals(action)) {
            commandResultContext = callbackContext;
            clearAllNotification(cordova.getActivity().getApplicationContext());
            return true;
        }
        if("setLocalNotificationType".equals(action)) {
            commandResultContext = callbackContext;
            int notifyType = args.getInt(0);
            setLocalNotificationType(cordova.getActivity().getApplicationContext(), notifyType);
            return true;
        }
        if("clearLocalNotificationType".equals(action)) {
            commandResultContext = callbackContext;
            clearLocalNotificationType(cordova.getActivity().getApplicationContext());
            return true;
        }
        if("getRegId".equals(action)) {
            String regId = getRegId(cordova.getActivity().getApplicationContext());
            // TODO: 16/3/15 return reg ID
            return true;
        }
        if("startListenMessage".equals(action)) {
            listenContext = callbackContext;
        }
        return false;
    }

    /**
     * @description 注册MiPush推送服务
     * @param context
     * @param appId
     * @param appKey
     */
    private void register(Context context, String appId, String appKey) {
        MiPushClient.registerPush(context, appId, appKey);
    }

    /**
     * @description 关闭MiPush推送服务
     * @param context
     */
    private void unregister(Context context) {
        MiPushClient.unregisterPush(context);
    }

    /**
     * @description 为指定用户设置alias
     * @param context
     * @param alias
     * @param category
     */
    private void setAlias(Context context, String alias, String category) {
        MiPushClient.setAlias(context, alias, category);
    }

    /**
     * @description 取消指定用户的alias
     * @param context
     * @param alias
     * @param category
     */
    private void unsetAlias(Context context, String alias, String category) {
        MiPushClient.unsetAlias(context, alias, category);
    }

    /**
     * @description 为指定用户设置userAccount
     * @param context
     * @param userAccount
     * @param category
     */
    private void setUserAccount(final Context context, final String userAccount, final String category) {
        MiPushClient.setUserAccount(context, userAccount, category);
    }

    /**
     * @description 取消指定用户的userAccount
     * @param context
     * @param userAccount
     * @param category
     */
    private void unsetUserAccount(final Context context, final String userAccount, final String category) {
        MiPushClient.unsetUserAccount(context, userAccount, category);
    }

    /**
     * @description 为某个用户设置订阅topic
     * @param context
     * @param topic
     * @param category
     */
    private void subscribe(Context context, String topic, String category) {
        MiPushClient.subscribe(context, topic, category);
    }

    /**
     * @description 取消某个用户的订阅topic
     * @param context
     * @param topic
     * @param category
     */
    private void unsubscribe(Context context, String topic, String category) {
        MiPushClient.unsubscribe(context, topic, category);
    }

    /**
     * @description 暂停接收MiPush服务推送的消息
     * @param context
     * @param category
     */
    private void pausePush(Context context, String category) {
        MiPushClient.pausePush(context, category);
    }

    /**
     * @description 恢复接收MiPush服务推送的消息，这时服务器会把暂停时期的推送消息重新推送过来
     * @param context
     * @param category
     */
    private void resumePush(Context context, String category) {
        MiPushClient.resumePush(context, category);
    }

    /**
     * @description 设置接收MiPush服务推送的时段，不在该时段的推送消息会被缓存起来，到了合适的时段再向app推送原先被缓存的消息
     * @param context
     * @param startHour
     * @param startMin
     * @param endHour
     * @param endMin
     * @param category
     */
    private void setAcceptTime(Context context, int startHour, int startMin, int endHour, int endMin, String category) {
        MiPushClient.setAcceptTime(context, startHour, startMin, endHour, endMin, category);
    }

    /**
     * @description 方法返回了客户端设置的别名列表(如果客户端没有设置别名，则返回空列表)
     * @param context
     * @return
     */
    private List<String> getAllAlias(Context context) {
        return MiPushClient.getAllAlias(context);
    }

    /**
     * @description 方法返回了客户端订阅的主题列表(如果客户端没有订阅主题，则返回空列表)
     * @param context
     * @return
     */
    private List<String> getAllTopic(Context context) {
        return MiPushClient.getAllTopic(context);
    }

    /**
     * @description 上报点击的消息
     * @param context
     * @param msgid
     */
    private void reportMessageClicked(Context context, String msgid) {
        MiPushClient.reportMessageClicked(context, msgid);
    }

    /**
     * @description 清除小米推送弹出的某一个notifyId通知
     * @param context
     * @param notifyId
     */
    private void clearNotification(Context context, int notifyId) {
        MiPushClient.clearNotification(context, notifyId);
    }

    /**
     * @description 清除小米推送弹出的所有通知
     * @param context
     */
    private void clearAllNotification(Context context) {
        MiPushClient.clearNotification(context);
    }

    /**
     * @description 客户端设置通知消息的提醒类型
     * @param context
     * @param notifyType
     */
    private void setLocalNotificationType(final Context context, int notifyType) {
        MiPushClient.setLocalNotificationType(context, notifyType);
    }

    /**
     * @description 清除客户端设置的通知消息提醒类型
     * @param context
     */
    private void clearLocalNotificationType(final Context context) {
        MiPushClient.clearLocalNotificationType(context);
    }

    /**
     * @description 获取客户端的RegId
     * @param context
     * @return
     */
    private String getRegId(Context context) {
        return MiPushClient.getRegId(context);
    }

    private boolean checkAcceptTime(int startHour, int startMin, int endHour, int endMin) {
        boolean result = true;
        if(startHour < 0 || startHour > 24 || endHour < 0 || endHour > 24) {
            result = false;
        }
        if(startMin < 0 || startMin > 60 || endMin < 0 || endMin > 60) {
            result = false;
        }
        if(startHour > endHour) {
            result = false;
        }
        if(startHour == endHour && startMin > endMin) {
            result = false;
        }
        return result;
    }

    public static void messageHandler(MiPushMessage message) {
        PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, message.toString());
        pluginResult.setKeepCallback(true);
        listenContext.sendPluginResult(pluginResult);
    }

    public static void commandHandler(MiPushCommandMessage message) {
        PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, message.toString());
        pluginResult.setKeepCallback(true);
        commandResultContext.sendPluginResult(pluginResult);
    }
}
