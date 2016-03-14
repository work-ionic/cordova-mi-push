package com.xform.plugins.mipush;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.*;
import org.apache.cordova.PluginResult;

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
        mMessage = message.getContent();
        if(!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if(!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        }
        PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, mMessage);
        MiPushPlugin.getContext().sendPluginResult(pluginResult);
    }

    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        mMessage = message.getContent();
        if(!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if(!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        }
        PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, mMessage);
        MiPushPlugin.getContext().sendPluginResult(pluginResult);
    }

    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        mMessage = message.getContent();
        if(!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if(!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        }
        PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, mMessage);
        MiPushPlugin.getContext().sendPluginResult(pluginResult);
    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        if(MiPushClient.COMMAND_REGISTER.equals(command)) {
            if(message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
            }
        } else if(MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
            if(message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
            }
        } else if(MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
            if(message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
            }
        } else if(MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
            if(message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
            }
        } else if(MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
            if(message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
            }
        } else if(MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
            if(message.getResultCode() == ErrorCode.SUCCESS) {
                mStartTime = cmdArg1;
                mEndTime = cmdArg2;
            }
        }
        PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, command);
        MiPushPlugin.getContext().sendPluginResult(pluginResult);
    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String log;
        if(MiPushClient.COMMAND_REGISTER.equals(command)) {
            if(message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
                log = "注册成功";
            } else {
                log = "注册失败";
            }
        } else {
            log = message.getReason();
        }
        PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, command);
        MiPushPlugin.getContext().sendPluginResult(pluginResult);
    }

    @Override
    public void onReceiveMessage(Context context, MiPushMessage message) {

    }
}
