sap.ui.define([
	"sap/ui/core/mvc/Controller"
], function (Controller) {
	"use strict";

	return Controller.extend("com.sao.clm.sl.cias.kyma.kymapilot-ui.controller.View1", {
		onInit: function () {
			var that = this;
			$.ajax({
				url: "/runtimeservice/api/v2/env",
				method: "GET",
				contentType: "application/json;charset=UTF-8",
				async: false,
				success: function (data) {
					console.log(data)
					var modelData = [];
					var key="";
					for(key in data){
						var obj = {
							"key": key,
							"val": data[key]
						};
						modelData.push(obj);
					}

					var oModel = new sap.ui.model.json.JSONModel();
					oModel.setData(modelData);
					that.getView().setModel(oModel);

				},
				error: function (xhr, err) {
					console.error("error - " + err);

				}
			});
		}
	});
});