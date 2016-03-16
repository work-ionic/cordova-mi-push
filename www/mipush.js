/**
 * Created by Ani on 16/3/12.
 */
var cordova = require('cordova'),
    exec = require('cordova/exec');

var TAG = 'MiPush';
var METHOD = {
    REGISTER: 'register',
    UNREGISTER: 'unregister',
    SET_ALIAS: 'setAlias',
    UNSET_ALIAS: 'unsetAlias',
    SET_USER_ACCOUNT: 'setUserAccount',
    UNSET_USER_ACCOUNT: 'unsetUserAccount',
    SUBSCRIBE: 'subscribe',
    UNSUBSCRIBE: 'unsubscribe',
    PAUSE_PUSH: 'pausePush',
    RESUME_PUSH: 'resumePush',
    SET_ACCEPT_TIME: 'setAcceptTime',
    GET_ALL_ALIAS: 'getAllAlias',
    GET_ALL_TOPIC: 'getAllTopic',
    REPORT_MESSAGE_CLICKED: 'reportMessageClicked',
    CLEAR_NOTIFICATION: 'clearNotification',
    CLEAR_ALL_NOTIFICATION: 'clearAllNotification',
    SET_LOCAL_NOTIFICATION_TYPE: 'setLocalNotificationType',
    CLEAR_LOCAL_NOTIFICATION_TYPE: 'clearLocalNotificationType',
    GET_REG_ID: 'getRegId',
    START_LISTEN_MESSAGE: 'startListenMessage',
    START_LISTEN_CLICK_MESSAGE: 'startListenClickMessage'
};

function MiPush() {};

MiPush.prototype.register = function(successCallback) {
    exec(successCallback, null, TAG, METHOD.REGISTER, []);
};

MiPush.prototype.unregister = function(successCallback) {
    exec(successCallback, null, TAG, METHOD.UNREGISTER, []);
};

MiPush.prototype.setAlias = function(successCallback, alias) {
    exec(successCallback, null, TAG, METHOD.SET_ALIAS, [alias]);
};

MiPush.prototype.unsetAlias = function(successCallback, alias) {
    exec(successCallback, null, TAG, METHOD.UNSET_ALIAS, [alias]);
};

MiPush.prototype.setUserAccount = function(successCallback, userAccount) {
    exec(successCallback, null, TAG, METHOD.SET_USER_ACCOUNT, [userAccount]);
};

MiPush.prototype.unsetUserAccount = function(successCallback, userAccount) {
    exec(successCallback, null, TAG, METHOD.UNSET_USER_ACCOUNT, [userAccount]);
};

MiPush.prototype.subscribe = function(successCallback, topic) {
    exec(successCallback, null, TAG, METHOD.SUBSCRIBE, [topic]);
};

MiPush.prototype.unsubscribe = function(successCallback, topic) {
    exec(successCallback, null, TAG, METHOD.UNSUBSCRIBE, [topic]);
};

MiPush.prototype.pausePush = function(successCallback) {
    exec(successCallback, null, TAG, METHOD.PAUSE_PUSH, []);
};

MiPush.prototype.resumePush = function(successCallback) {
    exec(successCallback, null, TAG, METHOD.RESUME_PUSH, []);
};

MiPush.prototype.setAcceptTime = function(startHour, startMin, endHour, endMin, successCallback, errorCallback) {
    exec(successCallback, errorCallback, TAG, METHOD.SET_ACCEPT_TIME, [startHour, startMin, endHour, endMin]);
};

MiPush.prototype.getAllAlias = function(successCallback) {
    exec(successCallback, null, TAG, METHOD.GET_ALL_ALIAS, []);
};

MiPush.prototype.getAllTopic = function(successCallback) {
    exec(successCallback, null, TAG, METHOD.GET_ALL_TOPIC, []);
};

MiPush.prototype.reportMessageClicked = function(msgid) {
    exec(null, null, TAG, METHOD.REPORT_MESSAGE_CLICKED, [msgid]);
};

MiPush.prototype.clearNotification = function(notifyId) {
    exec(null, null, TAG, METHOD.CLEAR_NOTIFICATION, [notifyId]);
};

MiPush.prototype.clearAllNotification = function() {
    exec(null, null, TAG, METHOD.CLEAR_ALL_NOTIFICATION, []);
};

MiPush.prototype.setLocalNotificationType = function(successCallback, notifyType) {
    exec(successCallback, null, TAG, METHOD.SET_LOCAL_NOTIFICATION_TYPE, [notifyType]);
};

MiPush.prototype.clearLocalNotificationType = function(successCallback) {
    exec(successCallback, null, TAG, METHOD.CLEAR_LOCAL_NOTIFICATION_TYPE, []);
};

MiPush.prototype.getRegId = function(successCallback) {
    exec(successCallback, null, TAG, METHOD.GET_REG_ID, []);
};

MiPush.prototype.startListenMessage = function(successCallback) {
    exec(successCallback, null, TAG, METHOD.START_LISTEN_MESSAGE, []);
};

MiPush.prototype.startListenClickMessage = function(successCallback) {
    exec(successCallback, null, TAG, METHOD.START_LISTEN_CLICK_MESSAGE, []);
};

module.exports = new MiPush();
