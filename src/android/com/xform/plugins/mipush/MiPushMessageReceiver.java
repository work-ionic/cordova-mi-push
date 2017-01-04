package com.xform.plugins.mipush;

import android.content.Context;
import android.content.Intent;
import com.xiaomi.mipush.sdk.*;
import org.apache.cordova.PluginResult;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Ani on 16/3/12.
 */
public class MiPushMessageReceiver extends PushMessageReceiver {
    private String mRegId;
    private long mResultCode = -1;
    private String mResaon;
    private String mCommand;
    private String mMessage;
    private String mTopic;
    private String mAlias;
    private String mStartTime;
    private String mEndTime;

    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
        PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, MiPushPlugin.jsonMessage(message));
        pluginResult.setKeepCallback(true);
        MiPushPlugin.getListenCallback().sendPluginResult(pluginResult);
    }


    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        if (MiPushPlugin.getClickCallback() != null) {
            // app is alive
            Intent mainIntent = null;
            try {
                mainIntent = new Intent(context, this.getClass().getClassLoader().loadClass(context.getPackageName() + ".MainActivity"));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mainIntent);
            PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, MiPushPlugin.jsonMessage(message));
            pluginResult.setKeepCallback(true);
            MiPushPlugin.getClickCallback().sendPluginResult(pluginResult);
        } else {
            // app is dead
            Intent launchIntent = context.getPackageManager().
                    getLaunchIntentForPackage(context.getPackageName());
            launchIntent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            launchIntent.putExtra(PushMessageHelper.KEY_MESSAGE, message);
            context.startActivity(launchIntent);
        }
    }

    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, MiPushPlugin.jsonMessage(message));
        pluginResult.setKeepCallback(true);
        MiPushPlugin.getListenCallback().sendPluginResult(pluginResult);
    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        JSONObject json = new JSONObject();
        String command = message.getCommand();
        try {
            json.put("command", command);
            json.put("resultCode", message.getResultCode());
            json.put("reason", message.getReason());
            List<String> arguments = message.getCommandArguments();
            String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
            String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
            if(MiPushClient.COMMAND_REGISTER.equals(command)) {
                if(message.getResultCode() == ErrorCode.SUCCESS) {
                    json.put("regId", cmdArg1);
                }
            } else if(MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
                if(message.getResultCode() == ErrorCode.SUCCESS) {
                    json.put("alias", cmdArg1);
                }
            } else if(MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
                if(message.getResultCode() == ErrorCode.SUCCESS) {
                    json.put("alias", cmdArg1);
                }
            } else if(MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
                if(message.getResultCode() == ErrorCode.SUCCESS) {
                    json.put("topic", cmdArg1);
                }
            } else if(MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
                if(message.getResultCode() == ErrorCode.SUCCESS) {
                    json.put("topic", cmdArg1);
                }
            } else if(MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
                if(message.getResultCode() == ErrorCode.SUCCESS) {
                    json.put("startTime", cmdArg1);
                    json.put("endTime", cmdArg2);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MiPushPlugin.commandHandler(json);
    }

    @Override
    public void onReceiveMessage(Context context, MiPushMessage message) {
        if (MiPushPlugin.getListenCallback() != null) {
            PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, MiPushPlugin.jsonMessage(message));
            pluginResult.setKeepCallback(true);
            MiPushPlugin.getListenCallback().sendPluginResult(pluginResult);
        }
    }
}
