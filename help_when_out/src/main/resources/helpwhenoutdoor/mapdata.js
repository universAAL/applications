dojo.declare("org.universAAL.AALapplication.helpwhenoutdoor.MapData", null, // parent
{
    areaPolylines : null,
	currentLatitude: null,
    currentLongitude: null,
    markerList: new Array(), // map : name -> Marker object
    safeAreaPolygon: null,
    agendaEvents: null,
    
    constructor: function(){
    
        this.SAFE_AREA_END_THRESHOLD = 0.05; // in Kilometers
		this.AREAS = {SAFE_AREA : 0, HOME_AREA: 1};

		// safe and home area polylines		
        this.areaPolylines = new Array();

        // Agenda events, visualizes the current 
        // and the next event
        this.agendaEvents = new Array();
        
        // homePosition = [latitude, longitude]
        this.homePosition = new Array();
        
        // History list, each array element contains 
        // the latitude, the longitude and the timestampwww
        this.historyList = new Array();
        
        // this flag is true is the position is dirty
        // and an update on the view is needed 
        this.dirtyPosition = true;
        
    },
	/**
	 *  Remove the marker from the list of markers
	 *  @param {Object} marker The marker to Remove
	 */
	deletePOI: function(marker){
        for (var i = 0; i < this.markerList.length; i++) {
            if (this.markerList[i] == marker) {
                this.markerList.splice(i, 1);
            }
        }
        
    },
    /**
     * Returns a marker identified by its name
     * @param {Object} name
     */
	getPOI : function(name)
	{
		 for (var i = 0; i < this.markerList.length; i++) {
		 	if (this.markerList[i].name == name) {
		 		return this.markerList[i];
		 	}
		 }
		return null;
	},
	
    getPOIList: function(){
        return this.markerList;
    },
    
    
    /**
     * Add the POI to the list
     * @param {Object} marker
     * @return true if not already present, false otherwise
     */
    addPoi: function(marker){
		// if the marker is already present return false
		 for (var i = 0; i < this.markerList.length; i++) {
		 	if (this.markerList[i].name == marker.name) {
				return false;
		 	}
		 }
        console.debug("Adding POI: %s", marker.name);
		this.markerList.push(marker);
        return true;
    },
    
    setHomePosition: function(lat, lng){
        this.homePosition[0] = lat;
        this.homePosition[1] = lng;
    },
    
    getHomePosition: function(){
        return this.homePosition;
    },
    
    
    
    addAgendaEvent: function(agendaEvent){
        this.agendaEvents.push(agendaEvent);
    },
    
    /**
     * Clear the safe area and the POI list
     */
    clear: function(){
        this.clearArea(this.AREAS.SAFE_AREA);
		this.clearArea(this.AREAS.HOME_AREA);
		this.markerList = [];
    },
    /**
     * Returns an array of latitude, longitude coordinates.
     * For example [37.41, -122.01, 37.40, -121.90,37.39].
     * Returns an empty list if the area has not been defined.
     * @param A constant specifying from which area belong the points. 
     * It can be MapData.AREAS.SAFE_AREA or MapData.AREAS.HOME_AREA  
     */
    getPoints: function(whichArea){
    
        // whichArea is an index for the array, it must be <= 1
        if (whichArea > 1)
			return null;
console.log("MAPDATA.JS -> getPoints, antes de leer los areaPolylines[whichArea] " + whichArea);	
		var area = this.areaPolylines[whichArea];
	
console.log("MAPDATA.JS -> getPoints, despues de leer los areaPolylines[whichArea]....voy a petar aqui -> antes");		   
		var nv = area.getVertexCount();
console.log("MAPDATA.JS -> getPoints, despues de leer los areaPolylines[whichArea]....voy a petar aqui -> despues");
        var points = []
        for (var i = 0; i < nv; ++i) {
            var point = area.getVertex(i);
            points.push(point.lat())
            points.push(point.lng());
        }
        return points;
    },
    /**
     * Clears the area with index passed as parameters. Returned the newly created object
     * @param {Object} whichArea
     */
    clearArea: function(whichArea){
    		
		if (whichArea > 1)
			return null;
		
		var color = null;	
		
        if (whichArea == this.AREAS.SAFE_AREA) // default 
            color = "#0000ff"; // red
        else 
            color = "#00ff00";

		console.log("MAPDATA.JS -> clearArea, antes de crear limpiar el areaPolylines[whichArea] " + whichArea);
		this.areaPolylines[whichArea] = new GPolyline([], color);
	
		console.log("MAPDATA.JS -> clearArea, despues de crear limpiar el areaPolylines[whichArea], devolviendo el area... limpico " + whichArea);
		return 	this.areaPolylines[whichArea];		
    },
    
    getArea: function(whichArea){
    
 		if (whichArea > 1) {
			console.log("getArea: wrong area specified");
			return null;
		}
		if (this.areaPolylines!=null) 
		console.log("are poly... distinto de null");
		
		console.log("MAPDATA.JS -> getArea, antes de enviar el areaPolylines[whichArea] " +  " which area " + whichArea);
		return this.areaPolylines[whichArea];
    },
    
    /**
     * Set the safe or home area from an array of GLatLng coordinates.
     * @param {Array} The array of GLatLng coordinates to set
     */
    setArea: function(points, whichArea){
        
		if (whichArea > 1) {
			console.log("setArea: wrong area specified " + whichArea);
			return null;
		}        
       
        if (points != null && points.length > 0) {
            console.log("Setting area " + whichArea + " with points " + points);
			
			var color = null;
	        if (whichArea == this.AREAS.SAFE_AREA) // default 
	            color = "#0000ff"; // red
	        else 
	            color = "#00ff00";

           this.areaPolylines[whichArea] = new GPolyline(points, color);
        }
        else {
            console.debug("Points: " + points);
            alert("Wrong number of points in the safe area");
        }
        
    },
    
    /**
     * Set the dirty flag
     * @param {Object} value True if the position need to be updated on the view, false otherwise
     */
    setDirtyPosition: function(value){
        this.dirtyPosition = value;
    },
    
    isPositionDirty: function(){
        return this.dirtyPosition;
    }

});
