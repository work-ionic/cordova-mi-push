#!/usr/bin/env node

module.exports = function(context) {
    var deferral = context.requireCordovaModule('q').defer(),
        Config = require('./../config.js'),
        config = new Config(context);

    var main = function() {
        config.removePrefence();
        deferral.resolve();
    }

    main();

    return deferral.promise;
};