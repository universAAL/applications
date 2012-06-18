dojo.declare("org.persona.service.helpwhenoutside.MapEditor", org.persona.service.helpwhenoutside.MapFrame, // parent
{
    oldCursor: null,
    mapController: null,
    drawSafeAreaButton: null,
    addPOIButton: null,
    markersTree: null,
    homeMarker: null,
    mapClickListener: null,
    /**
     * Create the class MapEditor and initialize
     * the GUI with dijit
     */
    constructor: function(){
    
    
        this.SAFE_AREA_END_THRESHOLD = 0.05; // in Kilometers
        this.homeMarkerAlreadyAdded = false;
        
        mapController.setServletURL('mapeditor');
        // load the data, like POI list and safe area from the PERSONA server
        // using AJAX.
        mapController.loadDataFromServer('all', dojo.hitch(this, this.showData));
        
        // Translates the UI
        this.translateUI();
        
        // Initializes GUI with buttons and styles
        this.initGUI();
    },
    translateUI: function(){
        this.inherited(arguments);
		        
		var assignTranslation = function(item) {
            var translation;
			// is it a dropdownbutton ? [item.title is empty]
			if (item.title == "") {
				var translation = translatedStrings[item.label];
				if (translation != null)
					item.setLabel(translation);	
			}
			else { // if item.title is not null it is a widget
				var translation = translatedStrings[item.title];
				if (translation != null) 
            		item.setAttribute('title', translation);
			}
                
        };
        // Dropdown buttons and dialogs behave differentely, they 
		// need a different translation code
        dijit.registry.byClass("dijit.form.DropDownButton").forEach(assignTranslation);
		dijit.registry.byClass("dijit.Dialog").forEach(assignTranslation);

    },
    showData: function(){
        this.inherited(arguments);
        // add the listner used to catch the movement of the home icon
        //GEvent.addListener(this.homeMarker, 'dragend', dojo.hitch(this, this.homePositionChanged));
        
        var poiList = mapData.getPOIList();
        var markerElement = dojo.byId("markersList");
        for (var i = 0; i < poiList.length; ++i) {
            var markerItem = document.createElement('li');
            markerItem.innerHTML = poiList[i].name;
            markerElement.appendChild(markerItem);
            this.createMarkerHTML(poiList[i]);
        }
        
        
    },
    // callback called when the user clicks on the "Home position" button
    homePositionChanged: function(overlay, p){
    
	    // remove the event listener if it has been called by setHomePosition
        if (this.homePositionListener) {
            GEvent.removeListener(this.homePositionListener);
            this.homePositionListener = null;
        }
    
		// first get the home area and check that is valid
		// by checking its length and then check if the point 'p'
		// is contained in the 
		var homeArea = mapData.getArea(mapData.AREAS.HOME_AREA);
		if (homeArea != null && homeArea.getLength() > 1 && !homeArea.containsLatLng(p))
		{
			alert(translatedStrings['HOME_POS_NOT_IN_HOME_AREA']);
			return;
        }
	    this.homeMarker.setLatLng(p);
		// FIREFOX 3.6 compatibility  !!!
		// Need to remove and add to overlay
		// in order to show the marker
		map.removeOverlay(this.homeMarker);
		map.addOverlay(this.homeMarker);
		this.homeMarker.show();
	    mapController.sendToServer('homePosition', p.lat() + " " + p.lng());
    },
    
    setHomePosition: function(){
        this.homePositionListener = GEvent.addListener(map, 'click', dojo.hitch(this, this.homePositionChanged));
    },
    
    setHistoryInterval: function(){
        var numberBox = dijit.byId('historyIntervalTextBox');
        if (numberBox.isValid()) 
            mapController.sendToServer('historyInterval', numberBox.getValue());
        
    },
    setMapKey: function(){
        var mapKey = dijit.byId('mapKeyTextBox');
        if (mapKey == null || mapKey == "") 
            alert("Can't set an empty map key");
        else 
            mapController.sendToServer('mapKey', mapKey.getValue());
    },
    
    initGUI: function(){
        // common GUI from MapFrame
        this.inherited(arguments);
        // The buttons and the their callbacks
        this.drawSafeAreaButton = dijit.byId("drawSafeAreaButton");
        this.drawHomeAreaButton = dijit.byId("drawHomeAreaButton");
        this.addPOIButton = dijit.byId("addPOIButton");
        this.historyDialogOkButton = dijit.byId("historyDialogOkButton");
        this.mapKeyOkButton = dijit.byId("mapKeyOkButton");
        
        
        dijit.byId("setHomePositionButton").onClick = dojo.hitch(this, this.setHomePosition);
        this.historyDialogOkButton.onClick = dojo.hitch(this, this.setHistoryInterval);
        this.mapKeyOkButton.onClick = dojo.hitch(this, this.setMapKey);
        this.drawSafeAreaButton.onClick = dojo.hitch(this, this.drawArea, mapData.AREAS.SAFE_AREA);
        this.drawHomeAreaButton.onClick = dojo.hitch(this, this.drawArea, mapData.AREAS.HOME_AREA);
        this.addPOIButton.onClick = dojo.hitch(this, this.addPOI);
        
        dijit.byId("clearMapButton").onClick = dojo.hitch(this, this.clearMap);
        
    },
    
    /**
     * Delete the POI marked by name
     * @param {Object} name
     */
    deletePOI: function(name){
    
        var marker = mapData.getPOI(name);
        if (marker == null) {
            console.error("Cannot delete the POI : " + name);
            return null;
        }
        mapData.deletePOI(marker);
        var markerLatLng = marker.getLatLng();
        console.debug("Remove marker %s at %f %f", marker.name, markerLatLng.lat(), markerLatLng.lng());
        var dataToSend = markerLatLng.lat() + ',' + markerLatLng.lng() + ',' + marker.name;
        mapController.sendToServer('deletePOI', dataToSend);
        map.removeOverlay(marker);
        // Remove the element from the GUI list on the right
        var markersListChild = document.getElementById('markersList').childNodes;
        for (var i = 0; i < markersListChild.length; ++i)
		{
			if (markersListChild[i] != null && markersListChild[i].firstChild != null)
			{
				if (markersListChild[i].firstChild.nodeValue == name)
					document.getElementById('markersList').removeChild(markersListChild[i]);
			}
		}
        
    },
    
    
    /**
     * Delete all the markers and the safe area from the map
     */
    clearMap: function(){
    
        // show the dialog
        showDialog(translatedStrings['ARE_YOU_SURE'], translatedStrings['CONFIRMATION'], dojo.hitch(this, function() // execute this function when OK is pressed
        {
            // Remove the markers, safe area and clear the markerStore
            map.clearOverlays();
            //map.removeAllPolylines();
            dojo.byId("poiOptions").style.display = "none";
            mapData.clear();
			// remove the GUI list
			var markersList = document.getElementById('markersList');
			// clears the marker list, since childNodes is a read only
			// attribute markerList.childNodes = [] is not allowed
			while (markersList.childNodes != null && markersList.childNodes[0] != null)
  			{
    			markersList.removeChild(markersList.childNodes[0]);
  			}
			//document.getElementById('markersList').childNodes = [];
            // send the command to the server
            mapController.sendToServer('clearMap', []);
            
        }));
    },
    
    addPOI: function(){
        // display the POI options
        //dojo.byId("poiOptions").style.display = "block";
        // add the POI icon when the user clicks on the map
        this.mapClickListener = GEvent.addListener(map, 'click', dojo.hitch(this, this.addSinglePOI));
        
    },
	/**
     * Decorate the function defined in mapFrame with the Delete button
     * to remove the marker from the map
     * @param {Object} marker The marker where the HTML code is added
     */
    createMarkerHTML: function(marker)
	{
		this.inherited(arguments);
		var markerNode = marker.domNode;

		var onClickElement = document.createElement("a");
        onClickElement.setAttribute('id', marker.name);
        onClickElement.setAttribute('href', '#');
        onClickElement.innerHTML = translatedStrings['DELETE_STRING'];
        onClickElement.addEventListener('click', dojo.hitch(this, function(event){
        
            this.deletePOI(event.target.id);
        }), false);
        markerNode.appendChild(onClickElement);

		
    },
    setPoiName: function(marker, poiFormFields){
  		  
        dijit.byId('addPOIDialog').hide();
        
		// remove all the click listener associated to map 
		GEvent.clearListeners(map, 'click');
		
        // get the name from the dialog and add it to 
        // the google api marker structure
        var markerName = poiFormFields.poiName;
        marker.name = markerName
        
        // add the POI to the data structure and to the map
        if (!mapData.addPoi(marker))        
        {
    		// already exist, issue an alert and return
			// without adding the marker
			map.removeOverlay(marker);
			alert(translatedStrings['MARKER_ALREADY_EXISTANT']);
			return;    
        }
        // create the HTML node for the marker with the delete link
        this.createMarkerHTML(marker);
                
        // add the marker to the GUI list on the right
        var markersList = document.getElementById('markersList');
        var markerItem = document.createElement('li');
        markerItem.innerHTML = markerName;
        markersList.appendChild(markerItem);
        
        // then send the command to the server
        /*
         * PROTOCOL : lat, lng , pinID, name
         */
        var markerLatLng = marker.getLatLng();
        var dataToSend = markerLatLng.lat() + ',' + markerLatLng.lng() + ',' + markerName;
        mapController.sendToServer('addPOI', dataToSend);
    },
    
    addSinglePOI: function(overlay, latlng, overlayLatlng){
        var newMarker = new GMarker(latlng, {
            draggable: false
        });
        map.addOverlay(newMarker);
        var poiDialog = dijit.byId('addPOIDialog');
        poiDialog.execute = dojo.hitch(this, this.setPoiName, newMarker);
        poiDialog.show();
        
    },
    /**
     * Callback called when the user presses the button "Draw Safe Area" or "Draw Home Area"
     */
    drawArea: function(whichArea){
        // do nothing if no area is specified
        if (whichArea == null) 
            return;
        // hide the POI options and set the Accordion pane title to Draw Options
        dijit.byId("toolOptionsPane").title = "Draw Options";
        dojo.byId("poiOptions").style.display = "none";
		
		// if the area to be drawn is the safe area and 
		// the home area still doesn't exist, print an alert.
		// The home area can be either null if it has not been
		// created or it can be simply an empty polyline
		var homeArea = mapData.getArea(mapData.AREAS.HOME_AREA);
		if (whichArea == mapData.AREAS.SAFE_AREA &&
			(homeArea == null || homeArea.getLength() < 1)
			)
		{
			alert(translatedStrings['HOME_AREA_NOT_DRAWN']);
			return;
		}
		
        var area = mapData.getArea(whichArea);
        
        // if there is an already existant area, delete it
        if (area != null) {
            map.removeOverlay(area);
        }
        area = mapData.clearArea(whichArea);
        map.addOverlay(area);
        GEvent.addListener(area, 'endline', dojo.hitch(this, function(){
			
			var retString = this.checkArea(); 
			
            if (retString == true) {
                var areaName = (whichArea == mapData.AREAS.SAFE_AREA) ? 'safeArea' : 'homeArea'
                mapController.sendToServer(areaName, mapData.getPoints(whichArea));
            }
            else {
                alert(translatedStrings['NOT_CONTAINED']);
                map.removeOverlay(area);
            }
            
        }));
        // let the user draw the safe area
        area.enableDrawing();
        
        
    },
    
    /**
     * Check that: 
     * - The home area is contained in the safe area
     * It is called when the safe area OR the home are created.
     * If both areas are not defined (because one of them is not drawn yet), returns true
     */
    checkArea: function(){
        // sa -> safe area
        // ha -> home area
        var sa = mapData.getArea(mapData.AREAS.SAFE_AREA);
        var ha = mapData.getArea(mapData.AREAS.HOME_AREA);
        // if the other area is not defined or has no points returns true
        if (sa == null || sa.getVertexCount() < 1 ||
        ha == null ||
        ha.getVertexCount() < 1) 
            return true;
        
        
        /* Core part
         * Iterate over the point of the home area,
         * returns false if a point of the home area is not
         * contained in the safe area
         */
        for (var i = 0; i < ha.getVertexCount(); ++i) {
            if (!sa.containsLatLng(ha.getVertex(i))) 
                return false;
        }
        return true;
    }
    
}); // end of dojo.declare
