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

import java.util.List;

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

    public static CallbackContext getContext() {
        return cContext;
    }
}
