#!/usr/bin/env node

module.exports = function (context) {

    var fs = context.requireCordovaModule('fs'),
        path = context.requireCordovaModule('path'),
        ConfigParser = context.requireCordovaModule('cordova-common/src/configparser/ConfigParser'),
        XmlHelpers = context.requireCordovaModule('cordova-common/src/util/xml-helpers'),
        et = context.requireCordovaModule('elementtree');

    var pluginConfigurationFile = path.join(context.opts.plugin.dir, 'plugin.xml'),
        androidPlatformDir = path.join(context.opts.projectRoot, 'platforms', 'android'),
        projectConfigurationFile = path.join(context.opts.projectRoot, 'config.xml'),
        projectManifestFile = path.join(androidPlatformDir, 'AndroidManifest.xml');

    var addMiPushParams = function() {
        var cmdLine = context.cmdLine,
            cmdArrays = cmdLine.split(' '),
            appId = null,
            appKey = null;
        cmdArrays.forEach(function (item) {
            if(item.indexOf('--appId') > -1) {
                appId = item.split('--appId=')[1];
            }
            if(item.indexOf('--appKey') > -1) {
                appKey = item.split('--appKey=')[1];
            }
        });
        if(!appId || !appKey) {
            console.error('缺少必要参数[--appId, --appKey], 请卸载后重新安装');
            process.exit(1);
        } else {
            var projectManifestXmlRoot = XmlHelpers.parseElementtreeSync(projectManifestFile);
            var appIdValue = et.XML('<meta-data android:name="miPush_appId" android:value="str_' + appId + '"></meta-data>'),
                appKeyValue = et.XML('<meta-data android:name="miPush_appKey" android:value="str_' + appKey + '"></meta-data>');

            XmlHelpers.graftXML(projectManifestXmlRoot, [appIdValue, appKeyValue], '/manifest/application');
            fs.writeFileSync(projectManifestFile, projectManifestXmlRoot.write({indent: 4}), 'utf-8');
        }
    };

    var removeMiPushParams = function() {
        var projectManifestXmlRoot = XmlHelpers.parseElementtreeSync(projectManifestFile);
        var childs = projectManifestXmlRoot.findall('application/meta-data');

        XmlHelpers.pruneXML(projectManifestXmlRoot, childs, '/manifest/application');
        fs.writeFileSync(projectManifestFile, projectManifestXmlRoot.write({indent: 4}), 'utf-8');
    };

    var addPermission = function() {
        var projectConfigurationXmlRoot = XmlHelpers.parseElementtreeSync(projectConfigurationFile),
            projectManifestXmlRoot = XmlHelpers.parseElementtreeSync(projectManifestFile),
            projectBundleId = projectConfigurationXmlRoot._root.attrib.id;

        var usesPushPermission = et.XML('<uses-permission android:name="' + projectBundleId + '.permission.MIPUSH_RECEIVE" />'),
            pushPermission = et.XML('<permission android:name="' + projectBundleId + '.permission.MIPUSH_RECEIVE" android:protectionLevel="signature" />');

        XmlHelpers.graftXML(projectManifestXmlRoot, [usesPushPermission, pushPermission], '/manifest');
        fs.writeFileSync(projectManifestFile, projectManifestXmlRoot.write({indent: 4}), 'utf-8');

    };

    var removePermission = function() {
        var projectConfigurationXmlRoot = XmlHelpers.parseElementtreeSync(projectConfigurationFile),
            projectManifestXmlRoot = XmlHelpers.parseElementtreeSync(projectManifestFile),
            projectBundleId = projectConfigurationXmlRoot._root.attrib.id;

        var usesPushPermission = et.XML('<uses-permission android:name="' + projectBundleId + '.permission.MIPUSH_RECEIVE" />'),
            pushPermission = et.XML('<permission android:name="' + projectBundleId + '.permission.MIPUSH_RECEIVE" android:protectionLevel="signature" />');

        XmlHelpers.pruneXML(projectManifestXmlRoot, [usesPushPermission, pushPermission], '/manifest');
        fs.writeFileSync(projectManifestFile, projectManifestXmlRoot.write({indent: 4}), 'utf-8');
    };

    this.addPreference = function() {
        addMiPushParams();
        addPermission();
    };

    this.removePrefence = function() {
        removeMiPushParams();
        removePermission();
    };
};