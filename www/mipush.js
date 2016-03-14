/**
 * Created by Ani on 16/3/12.
 */
var cordova = require('cordova'),
    exec = require('cordova/exec');

function MiPush() {};

MiPush.prototype.miInit = function(successCallback, app_id, app_key) {
    exec(successCallback, null, 'MiPush', 'miInit', [app_id, app_key]);
};

module.exports = new MiPush();