/* global QUnit */
QUnit.config.autostart = false;

sap.ui.getCore().attachInit(function () {
	"use strict";

	sap.ui.require([
		"com/sao/clm/sl/cias/kyma/kymapilot-ui/test/integration/AllJourneys"
	], function () {
		QUnit.start();
	});
});