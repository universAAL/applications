// Create polygon method for collision detection
// Code taken from http://dawsdesign.com/drupal/google_maps_point_in_polygon
GPolyline.prototype.containsLatLng = function(latLng) {
	// Do simple calculation so we don't do more CPU-intensive calcs for obvious misses
	var bounds = this.getBounds();
	
	if(bounds != null && !bounds.containsLatLng(latLng)) {
		return false;
	}
	
	// Point in polygon algorithm found at http://msdn.microsoft.com/en-us/library/cc451895.aspx
	var numPoints = this.getVertexCount();
	var inPoly = false;
	var i;
	var j = numPoints-1;
	
	for(var i=0; i < numPoints; i++) { 
		var vertex1 = this.getVertex(i);
		var vertex2 = this.getVertex(j);
		
		if (vertex1.lng() < latLng.lng() && vertex2.lng() >= latLng.lng() || vertex2.lng() < latLng.lng() && vertex1.lng() >= latLng.lng())  {
			if (vertex1.lat() + (latLng.lng() - vertex1.lng()) / (vertex2.lng() - vertex1.lng()) * (vertex2.lat() - vertex1.lat()) < latLng.lat()) {
				inPoly = !inPoly;
			}
		}
		
		j = i;
	}
	
	return inPoly;
};

/**
  * Show a non-modal dialog with an OK and a Cancel button. 
  * @param msg The message to visualize
  * @param title The title of the dialog
  * @param executeFunc The function executed when the user presses OK
  */

function showDialog(msg, title, executeFunc){
	var idName = "dynamicDialog";
	var dialog = dijit.byId(idName);
	
	if (dialog != null) {
		dialog.show();
	}
	else {
	
		var dialogPane = document.createElement('div');
		var msgElement = document.createElement('div')
		
		msgElement.innerHTML = "<p>" + msg + "</p> <br/>";
		dialogPane.appendChild(msgElement);
		
		// create the buttons and add them to the dialog
		var buttons = document.createElement('div');
		buttons.setAttribute("style", "float:right");
		// create the buttons if they're not already been created
		var okButton = dijit.byId("okButtonDLG");
		var cancelButton = dijit.byId("cancelButtonDLG");
		if (okButton == null) 
			var okButton = new dijit.form.Button({
				id: "okButtonDLG",
				type: "submit",
				label: "OK"
			})
		buttons.appendChild(okButton.domNode);
		if (cancelButton == null) {
			var cancelButton = new dijit.form.Button({
				id: "cancelButtonDLG",
				type: "reset",
				label: "Cancel"
			});
			cancelButton.onClick = function(){
				dijit.byId(idName).hide();
			};
		}
		buttons.appendChild(cancelButton.domNode);
		
		dialogPane.appendChild(buttons);
		// create the dialog and add it to domNode
		var dialog = new dijit.Dialog({
			id: idName,
			style: "display:none"
		}, dialogPane);
		dialog.titleNode.innerHTML = title;
		
		dialog.startup();
		dialog.execute = executeFunc;
		dialog.show();
	}	
}
