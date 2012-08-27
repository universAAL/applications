dojo.declare("org.universAAL.AALapplication.helpwhenoutdoor.MapController", null, // parent
{

    constructor: function(_mapData){
        this.mapData = _mapData;
        this.servletURL = "";        
    },
    
    setServletURL: function(url){
        this.servletURL = url;
    },
    
    loadDataFromServer: function(whichData, callback){

        dojo.xhrGet({
            url: this.servletURL + "?serviceData=" + whichData,
            handleAs: 'xml',
            load: dojo.hitch(this, function(response, ioArgs)
			{	
			console.log('MAPCONTROLLER.JS -> loadDataFromServer, whichArea]', whichData);
		
				// TODO: at the moment ioArgs is not passed to the
				// handle function
				this.dataLoaded(response, callback)
			}),
            error: function(response, ioArgs){
                console.error('Error: ', response);
                var errorPopUp = window.open("","errorPopUp");
				errorPopUp.document.open();
				errorPopUp.document.write(response.responseText);
            }
        });
        
    },
    
    dataLoaded: function(xml, callback){
        if (xml != null) {
			// Load the safe area
			this.loadSafeArea(xml.getElementsByTagName('SafeArea'));
			
			// Load the home area
			this.loadHomeArea(xml.getElementsByTagName('HomeArea'));
			
			// Get the GPS Position Element
			var gpsElement = xml.getElementsByTagName('gpsPos');
			
			if (gpsElement != null && gpsElement[0] != null) {
				this.mapData.currentLatitude = gpsElement[0].getAttribute('latitude');
				this.mapData.currentLongitude = gpsElement[0].getAttribute('longitude');
				this.mapData.setDirtyPosition(true);
			}
			
			// load the home position
			var homePositionElement = xml.getElementsByTagName('HomePosition');
			this.loadHomePosition(homePositionElement);
			
			// load the poi list
			var poiListElement = xml.getElementsByTagName('PoiList');
			this.loadPOIList(poiListElement);
			
			// load the history
			var historyListElement = xml.getElementsByTagName('HistoryList');
			this.loadHistory(historyListElement);
			
			// load agenda elements
			var agendaListElement = xml.getElementsByTagName('Agenda');
			this.loadAgenda(agendaListElement);
			
		}
		else {
			// no xml, the GPS position doesn't need to be updated
			console.debug("Controller: no pos update");
			this.mapData.setDirtyPosition(false);
		}
        // call the callback provided as a parameter
        callback();
    },
    
    loadPOIList: function(poiListElement){
        if (poiListElement != null && poiListElement[0] != null) {
            // for each poi update the array
            // uses a k index because some childNodes can be different
            // than "Poi"
            for (i = 0; i < poiListElement[0].childNodes.length; i++) {
                var poiElement = poiListElement[0].childNodes[i];
                if (poiElement.nodeName == "Poi") {
                    var poiLatitude = parseFloat(poiElement.getAttribute("latitude"));
                    var poiLongitude = parseFloat(poiElement.getAttribute("longitude"));
					var poiID = poiElement.getAttribute("pinID");
                  	var poiMarker = new GMarker(new GLatLng(poiLatitude, poiLongitude));
					poiMarker.pinID = poiID;
                    poiMarker.name = poiElement.getAttribute("name");
                    mapData.addPoi(poiMarker);
                    
                }
            }
        }
    },
    
    loadHistory: function(historyListElement){
        if (historyListElement != null && historyListElement[0] != null) {
        
            for (i = 0; i < historyListElement[0].childNodes.length; i++) {
                var historyElement = historyListElement[0].childNodes[i];
                if (historyElement.nodeName == "History") {
                    var history = new Object();
                    history.latitude = parseFloat(historyElement.getAttribute("latitude"));
                    history.longitude = parseFloat(historyElement.getAttribute("longitude"));
                    history.timeStamp = parseInt(historyElement.getAttribute("timestamp"));
                    var date = new Date(history.timeStamp);
                    var hours = date.getHours();
                    var minutes = date.getMinutes();
                    if (parseInt(hours) < 10) 
                        hours = "0" + hours;
                    if (parseInt(minutes) < 10) 
                        minutes = "0" + minutes;
                    
                    history.timeStamp = hours + ":" + minutes;
					
                    history.address = "Not Known";
                    mapData.historyList.push(history);
    
                }
                
            }
            
        }
        
    },
    
    /**
     * Load the Safe Area from the XML. Calls the mapData.setArea() object with AREAS.SAFE_AREA parameter
     * @param {Object} safeAreaElement The safe area element. If it's null, simply return without setting
     * a safe area
     */
    loadSafeArea: function(safeAreaElement){
    
        if (safeAreaElement != null && safeAreaElement[0] != null) {
        	var points = new Array();
            // Create the safe area polygon. 
            for (i = 0; i < safeAreaElement[0].childNodes.length; i++) {
                var pointElement = safeAreaElement[0].childNodes[i];
                if (pointElement.nodeName == "SafeAreaPoint") {
                    var latitude = pointElement.getAttribute("latitude");
                    var longitude = pointElement.getAttribute("longitude");
                    
                    var p = new GLatLng(latitude, longitude);
					// fills the array of GLatLng coordinates to be used
					// to construct the GPolyline
                    points.push(p);
                }
            }
			mapData.setArea( points, mapData.AREAS.SAFE_AREA );
        } // safeAreaElement end
    },
    /**
     * Load the Home Area from the XML. Calls the mapData.setArea object with AREAS.HOME_AREA parameter
     * @param {Object} safeAreaElement The safe area element. If it's null, simply return without setting
     * a safe area
     */
    loadHomeArea: function(homeAreaElement){
    
        if (homeAreaElement != null && homeAreaElement[0] != null) {
        	var points = new Array();
            // Create the safe area polygon. 
            for (i = 0; i < homeAreaElement[0].childNodes.length; i++) {
                var pointElement = homeAreaElement[0].childNodes[i];
                if (pointElement.nodeName == "HomeAreaPoint") {
                    var latitude = pointElement.getAttribute("latitude");
                    var longitude = pointElement.getAttribute("longitude");
                    
                    var p = new GLatLng(latitude, longitude);
					// fills the array of GLatLng coordinates to be used
					// to construct the GPolyline
                    points.push(p);
                }
            }
			mapData.setArea( points, mapData.AREAS.HOME_AREA );
        } // safeAreaElement end
    },
    
	loadHomePosition : function(homePositionElement)
	{
		if (homePositionElement != null && homePositionElement[0] != null) {
			var latitude = homePositionElement[0].getAttribute("latitude");
			var longitude = homePositionElement[0].getAttribute("longitude");
			mapData.setHomePosition(latitude, longitude);
		}
	},
	
    /**
     * Get the agenda events, in the form:
     * <Agenda>
     *  <Event type="current" address="Via Bla" beginTime=" ..." endTime=" .."/>
     *  <Event type="next" address="Via AA" beginTime=" ..." endTime=" .."/>
     * </Agenda>
     * @param {Object} agendaListElement The <Agenda> node
     */
    loadAgenda: function(agendaListElement){
    
        if (agendaListElement != null && agendaListElement[0] != null) {
            for (i = 0; i < agendaListElement[0].childNodes.length; i++) {
                var agendaElement = agendaListElement[0].childNodes[i];
                if (agendaElement.nodeName == "Event") {
                    agendaEvent = new Object();
                    agendaEvent.type = agendaElement.getAttribute("type");
                    agendaEvent.beginTime = agendaElement.getAttribute("beginTime");
                    agendaEvent.address = agendaElement.getAttribute("address");
                    agendaEvent.endTime = agendaElement.getAttribute("endTime");
                    mapData.addAgendaEvent(agendaEvent);
                }
            }
        }
    },
    
    /**
     * Send the operation to server, using optional data
     * @param {Object} operation Can be 'deletePOI', 'addPOI', 'clearMap', 'safeArea', 'historyInterval','homeArea'
     * @param {Object} data If operation is 'deletePOI', 'addPOI' it is the point deleted or added.
     * If 'safeArea' it is the list of points belonging to the safe area
     */
    sendToServer: function(operation, data){
        xhrArgs = {
        
            url: this.servletURL,
            putData: operation + ':' + data,
            handleAs: "text",
			error: function(response, ioArgs){
                console.error('Error: ', response);
                var errorPopUp = window.open("","errorPopUp");
				errorPopUp.document.open();
				errorPopUp.document.write(response);
            }
        };
        console.log("operation " + operation + " data " + data);
        dojo.xhrPut(xhrArgs);
        
    }


});
