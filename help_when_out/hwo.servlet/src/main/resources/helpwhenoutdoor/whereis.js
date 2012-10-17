dojo.declare("org.universAAL.AALapplication.helpwhenoutdoor.Whereis", org.universAAL.AALapplication.helpwhenoutdoor.MapFrame, {

    userMarker: null,
    update: false, // true if the scene need to be updated
    constructor: function(){
        this.DATA_RELOAD_TIME = 5000; //in milliseconds
        this.trailPolyline = new GPolyline([]);
		// boolean array for areas, shows all areas by default 
        this.areaDisplayed = new Array();
		this.areaDisplayed[mapData.AREAS.SAFE_AREA] = true;
		this.areaDisplayed[mapData.AREAS.HOME_AREA] = true;
		
		// boolean flag used to prevent parsing and refreshing
        // always the whole xml
        this.allDataLoaded = false;
        
		this.isAgendaShown = false;
		this.isHistoryShown = false;
		
        this.geocoder = new GClientGeocoder();
        
        // in this array will be stored only unique addresses
        // along with theirs timestamp
        // each element has 2 value : location and timestamp        
        this.historySet = new Array();
        
        this.agendaButton = dijit.byId('showAgendaButton');
        this.historyButton = dijit.byId('showHistoryButton');
        this.resetTrailsButton = dijit.byId('resetTrailsButton');
        
        dojo.connect(this.agendaButton, 'onClick', dojo.hitch(this, this.showAgendaEvents));
        dojo.connect(this.historyButton, 'onClick', dojo.hitch(this, this.showHistory));
        dojo.connect(this.resetTrailsButton, 'onClick', dojo.hitch(this, this.resetTrails));
        // this goes into the information pane
        // and show the agenda events 
        this.agendaDiv = dojo.byId('agendaEvents');
        
        // The same for history
        this.historyDiv = dojo.byId('historyEvents');
        
        // set the URL where the AJAX call is carried out
        mapController.setServletURL('whereis');
        // load the data, like POI list and safe area from the uAAL server
        // using AJAX.
        mapController.loadDataFromServer('all', dojo.hitch(this, this.showData));
        
		// translate the UI
		this.translateUI();
		
        // Initializes GUI with buttons and styles
        this.initGUI();
        
		
        
    },
    
	translateUI : function()
	{
		this.inherited(arguments);
	},
	
    geocode: function(latlng, timeStamp){
    
        this.geocoder.getLocations(latlng, dojo.hitch(this, function(response){
            if (!response || response.Status.code != 200) {
                log.error("Cannot perform the geocode" + response.Status.code);
                return;
            }
            else {
            
                var address = new Object();
                address.location = response.Placemark[0].address;
                address.timestamp = timeStamp;
                //console.log("address: %d \t time %s", address.location, address.timestamp);
				
                // check if this address is already present
                // if not already present fill the <div> historyDiv in 
				// the information pane
				
				var alreadyPresent = false;
				                                
				for (var j = 0; j < this.historySet.length; j++) {
					if (this.historySet[j].location == address.location) {
						console.log("Address %s already present", address.location);
						alreadyPresent = true;
						}
				}
        		if (!alreadyPresent) {
					console.log("Adding: %s", address.location);
					// fill the information pane with history values
					this.historyDiv.innerHTML +=  timeStamp + " <strong> " + translatedStrings['NEAR'] + "</strong> " + address.location + "<br />";
					this.historySet.push(address);
				}
            
                
                
            } // end else
        })); // end getLocations        
    },
    
    showHistory: function(){
        var historyLabel = dojo.byId('showHistoryLabel');
        // If history is not shown, show it 
        if (! this.isHistoryShown) {
			this.isHistoryShown = true;
            // ... set it "Hide History" and shows the corresponding <div>
            historyLabel.innerHTML = translatedStrings['HIDE_HISTORY']; //'Hide History';
            dojo.style(this.historyDiv, 'visibility', 'visible');
            // loads the data and processes them
            mapController.loadDataFromServer('getHistory', dojo.hitch(this, function(){
            
                this.historyDiv.innerHTML = "<h3>" + translatedStrings['HISTORY_H3'] + "</h3>";
				
                for (var l = 0; l < mapData.historyList.length; ++l) {
                
                    // geocode each history event
                    // it has to be run every 3 seconds because Google Geocode
                    // service can't answer to many queries in short time
                    var latlng = new GLatLng(mapData.historyList[l].latitude, mapData.historyList[l].longitude);
                    var timeStamp = mapData.historyList[l].timeStamp;
                    console.log("l: %d", l);
                    //setTimeout("this.geocode(" + latlng + "," +timeStamp+")", 2000);
					setTimeout(dojo.hitch(this, this.geocode, latlng , timeStamp), 2000);
                    
                }// end for
            })); // end loadDataFromServer

        } // end if 'Show' or 'Hide"
        else {
			this.isHistoryShown = false;
            historyLabel.innerHTML = translatedStrings['SHOW_HISTORY'];
            this.historyDiv.innerHTML = "<h3>" + translatedStrings['HISTORY_H3'] + "</h3>";
			// reset the history values for the html <div>
			this.historySet = [];
            dojo.style(this.historyDiv, 'visibility', 'hidden');
        }
        
    },
    
    showAgendaEvents: function(){
        var agendaLabel = dojo.byId('showAgendaLabel');
        // "Show Agenda Events" label is present
        if (! this.isAgendaShown) {
			this.isAgendaShown = true;
            this.agendaDiv.innerHTML = "<h3>" + translatedStrings['AGENDA_H3'] + "</h3>";
            // fill the agenda events <div>
            var agendaEvents = mapData.agendaEvents;
            for (i in agendaEvents) {
                this.agendaDiv.innerHTML += "<p>" + agendaEvents[i].address + " " + translatedStrings['AT'] + " " + agendaEvents[i].beginTime + "<p>";
            }
            agendaLabel.innerHTML = translatedStrings['HIDE_AGENDA_EVENTS'];
            dojo.style(this.agendaDiv, 'visibility', 'visible');
            
        }
        else {
			this.isAgendaShown = false;
            agendaLabel.innerHTML = translatedStrings['SHOW_AGENDA_EVENTS'];
            dojo.style(this.agendaDiv, 'visibility', 'hidden');
        }
        
    },
    /**
     * Show the data stored in the mapData data structure, which is filled by the
     * map controller
     */
    showData: function(){
        if (!this.allDataLoaded) {
            // all data must be downloaded only once
            this.inherited(arguments);
            
            dojo.style(this.agendaDiv, 'visibility', 'hidden');
            dojo.style(this.historyDiv, 'visibility', 'hidden');
			
            // The home position must be fixed in the service viewer
            this.homeMarker.disableDragging();
            map.addOverlay(this.homeMarker);
            this.allDataLoaded = true;
			
			map.addOverlay(this.trailPolyline);
            
        }
        
        
        // reload the data each k seconds    
        if (mapData.isPositionDirty()) {
            console.debug("Need update, p: %f %f",  mapData.currentLatitude, mapData.currentLongitude);
			if (mapData.currentLatitude == null || mapData.currentLongitude == null)
			{
				var latlng = this.homeMarker.getLatLng();
				mapData.currentLatitude = latlng.lat();
				mapData.currentLongitude = latlng.lng();
			} 
            var currentPos = new GLatLng(mapData.currentLatitude, mapData.currentLongitude);
 			// append the point to the trail polyline
            // add as a final vertex
			var trailVC = this.trailPolyline.getVertexCount();
            this.trailPolyline.insertVertex(trailVC, currentPos);
			if (this.userMarker != null) {
                map.removeOverlay(this.userMarker);    
            }

			var userMarkerIcon = new GIcon();
			userMarkerIcon.image = this.rootDir + '/icons/user.png';
			userMarkerIcon.shadow = this.rootDir + '/icons/user_shadow.png';
			userMarkerIcon.iconSize = new GSize(32,32);
			userMarkerIcon.shadowSize = new GSize(49,32);
			userMarkerIcon.iconAnchor = new GPoint(16.0, 16.0);
			
			this.userMarker = new GMarker(currentPos, {icon: userMarkerIcon});
            map.addOverlay(this.userMarker);
            
        }
        else 
            console.debug("Doesn't need update !!");
        
        // the calling class 'this' is Window, which has a mapViewer instance
        t = setTimeout('mapViewer.updatePositionRequest()', this.DATA_RELOAD_TIME);
    },
    
    updatePositionRequest: function(){
    
        mapController.loadDataFromServer('updatePos', dojo.hitch(this, this.showData));
    },
    
    initGUI: function(){
        this.inherited(arguments);
        // The buttons and the their callbacks
        this.showSafeAreaButton = dijit.byId("showSafeAreaButton");
        this.showSafeAreaButton.onClick = dojo.hitch(this, this.toggleArea, mapData.AREAS.SAFE_AREA);
        this.showHomeAreaButton = dijit.byId("showHomeAreaButton");
        this.showHomeAreaButton.onClick = dojo.hitch(this, this.toggleArea, mapData.AREAS.HOME_AREA);

        this.showHistoryButton = dijit.byId("showHistoryButton");

    },
    
    resetTrails: function(){
		
		map.removeOverlay(this.trailPolyline);
		this.trailPolyline = new GPolyline([]);
        map.addOverlay(this.trailPolyline);
    },
    
    toggleArea: function(whichArea){
		
		var area = mapData.getArea(whichArea);
		if (area == null)
			return; // do nothing in case there's no safe area
        if (this.areaDisplayed[whichArea]) 
            map.addOverlay(area);
        else 
            map.removeOverlay(area);
        this.areaDisplayed[whichArea] = !this.areaDisplayed[whichArea];
        
    }
});



