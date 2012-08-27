dojo.declare("org.universAAL.AALapplication.helpwhenoutdoor.MapFrame", null, // parent
{
    constructor: function(){
    
        // Create the Map class on the <div> centerMap
        
        this.MAP_HTML_ELEMENT = 'centerMap';
        // the marker represented with an house icon
        this.homeMarker = null;
        
        map = new GMap2(dojo.byId(this.MAP_HTML_ELEMENT));
        map.addControl(new GSmallMapControl());
		map.addControl(new GMapTypeControl());

        // Data class, GLOBAL VARIABLE
        mapData = new org.universAAL.AALapplication.helpwhenoutdoor.MapData();
        
        mapController = new org.universAAL.AALapplication.helpwhenoutdoor.MapController(mapData);
        this.rootDir = "/helpwhenoutdoor";
    },
    
    translateUI: function(){
        var language = "";
        if (navigator.language) 
            language = navigator.language;
        else 
            language = navigator.userLanguage;
        
        language = language.substring(0, 2);
        // rootDir without the /
        // translated_string without the .js
        // language should be it,dk,en or es
        dojo.requireLocalization("i18n", "translated_strings", language);
        // this is a global variable
        translatedStrings = dojo.i18n.getLocalization("i18n", "translated_strings", language);
        var translationElements = dojo.query(".translation");
        if (translationElements != null) {
            for (var i = 0; i < translationElements.length; ++i) {
                // the translation hashkey is the html value of the original
                // file
                var idString = dojo.trim(translationElements[i].innerHTML);
                var translation = translatedStrings[idString];
                // if there is a translation, set it
                if (translation != null) 
                    translationElements[i].innerHTML = translation;
//				if (translation != null && !dojo.isFF)
//					translationElements[i].firstChild.nodeValue = translation;
            }
        }
        
        // do the same for the accordion pane and other classes derived
        // by _Widget, select them using dijit registry
        var accordionTranslated = dijit.registry.byClass("dijit.layout.AccordionPane");
        if (accordionTranslated != null) {
            // change title for each accordion pane
            accordionTranslated.forEach(function(pane){
                var translation = translatedStrings[pane.title];
                if (translation != null) 
                    pane.attr('title', translation);
            });
        }
        
        
    },
    
    /**
     * Create the HTML for the marker bubble
     * @param {Object} marker The marker where the HTML code is added
     */
    createMarkerHTML: function(marker){
        var markerName = marker.name;
        var markerNode = document.createElement('div');
        markerNode.id = "markerNode_" + markerName;
        markerNode.innerHTML = '<b>' + markerName + '</b> <br />';
        
        var streetViewNode = document.createElement('div');
        
        streetViewNode.style.textAlign = 'center';
        streetViewNode.style.width = '500px';
        streetViewNode.style.height = '300px';
        streetViewNode.innerHTML = translatedStrings['LOADING_PANORAMA'];
        if (streetViewNode.innerHTML == null) 
            streetViewNode.innerHTML = "Loading Panorama";
        // bind the HTML to the marker
        marker.bindInfoWindow(markerNode, {
            maxContent: streetViewNode,
            maxTitle: translatedStrings['STREET_VIEW'],
            maxWidth: 500
        });
        
        var streetView = new GStreetviewPanorama(streetViewNode);
        
        GEvent.addListener(streetView, 'error', function(code){
            var nopano = translatedStrings['NO_NEARBY_PANORAMA'];
            var noflash = translatedStrings['FLASH_NOT_INSTALLED'];
    
	        if (code == 600) {
                if (nopano != null) 
                    alert(nopano);
				else
					alert("No nearby panorama");
            }
            if (code == 603) {
                if (noflash != null) 
                    alert(noflash);
				else
					alert("No Flash installed")
            } 
        });
        
        streetView.setLocationAndPOV(marker.getLatLng());
        var iw = map.getInfoWindow();
        
        
        GEvent.addListener(iw, "maximizeend", function(){
            streetView.setContainer(streetViewNode);
            window.setTimeout(function(){
                streetView.checkResize()
            }, 5);
        });
        GEvent.addListener(marker, "infowindowbeforeclose", function(){
            streetView.remove();
        });
        // decorate marker structure with the node. This attribute is retrieved by
        // functions which inherit from this one
        marker.domNode = markerNode;
        
        
    },
    initGUI: function(){
    
        dijit.byId("changeThemeComboBox").onChange = dojo.hitch(this, this.changeTheme);
        
        // get all the children of the pane and get the maximum width for all widgets
        var operationsPane = dijit.byId('operationsPane');
        var operationPaneChilds = operationsPane.getDescendants();
        var maxWidth = -100;
        for (i = 0; i < operationPaneChilds.length; i++) {
            var btnWidth = dojo.style(operationPaneChilds[i].id, 'width');
            if (btnWidth > maxWidth) 
                maxWidth = btnWidth;
        }
        // set the pane and its childers width to the same size
        // 5 is the margin for the pane
        for (i = 0; i < operationPaneChilds.length; i++) {
            dojo.style(operationPaneChilds[i].id, 'width', maxWidth + 'px');
            
        }
        
        //var operationsContainer = dijit.byId('operationsContainer'); 	
        //dojo.style(operationsContainer.id, 'width', maxWidth + 40 + 'px');
        //operationsContainer.layout();
    
    },
    
    initHomePosition: function(lat, lon){
    
        var homeMarkerIcon = new GIcon();
        homeMarkerIcon.image = this.rootDir + '/icons/home.png';
        homeMarkerIcon.shadow = this.rootDir + '/icons/home_shadow.png';
        homeMarkerIcon.iconSize = new GSize(32, 32);
        homeMarkerIcon.shadowSize = new GSize(49, 32);
        homeMarkerIcon.iconAnchor = new GPoint(16.0, 16.0);
        var homeMarkerOpts = {
            draggable: false,
            icon: homeMarkerIcon
        };
        this.homeMarker = new GMarker(new GLatLng(lat, lon), homeMarkerOpts);
        map.addOverlay(this.homeMarker);
    },
    
    changeTheme: function(value){
        // load the .css file as a <link> element
        // and append it to the <head> element
        var cssRef = document.createElement("link");
        cssRef.href = this.rootDir + "/dojoroot/dijit/themes/" + value + "/" + value + ".css";
        cssRef.rel = 'stylesheet';
        cssRef.type = 'text/css';
        dojo.query('head')[0].appendChild(cssRef);
        // set the <body> tag to the new css class 
        dojo.body().setAttribute("class", value);
    },
    
    
    /**
     * Show the data loaded from ajax. The code that loads the data in the mapData structure
     * is in the controller part.
     */
    showData: function(){
    
        // if the home is valid set the center of the map on it, otherwise
        // if a safe area is available, set the home position in the center of the safe area 
        // If no safe area is available, the world map is shown, 
        // centering on the coordinate (0,0) at minimum zoom level
        var homePosition = mapData.getHomePosition();
        var zoomLevel = 15;
console.log("MAPFRAME.JS -> showData, antes de mapData.getArea");
        var safeArea = mapData.getArea(mapData.AREAS.SAFE_AREA);
console.log("MAPFRAME.JS -> showData, despues de mapData.getArea " + safeArea.getVertexCount() + " mapData.AREAS.SAFE_AREA " + mapData.AREAS.SAFE_AREA + " safeArea.length " + safeArea.length);
        var homeArea = mapData.getArea(mapData.AREAS.HOME_AREA);
        if (homePosition[0] == null || homePosition[1] == null) {
            // initializes the values to the Equator
            homePosition[0] = 0.0;
            homePosition[1] = 0.0;
                        
            if (safeArea != null && safeArea.getVertexCount() > 1) {
console.log("MAPFRAME.JS -> showData, antes de mapData.getPoints('safeArea')");
                var points = mapData.getPoints('safeArea');
               // gema var points = mapData.getPoints(mapData.AREAS.SAFE_AREA);
console.log("MAPFRAME.JS -> showData, despues de mapData.getPoints('safeArea') points...." + points);
                // add all the points on the safe area to find the center
                for (var i = 0; i < points.length; ++i) {
                console.log("home position i " + i + " lat " + points[i].lat + " lon " + points[i].lon);
                    homePosition[0] = homePosition[0] + parseFloat(points[i].lat);
                    homePosition[1] = homePosition[1] + parseFloat(points[i].lon);
                }
            
                // normalize the values
                homePosition[0] = homePosition[0] / points.length;
                homePosition[1] = homePosition[1] / points.length;
                console.log("homePosition[0]: " + homePosition[0]);
                console.log("homePosition[1]: " + homePosition[1]);
            }
            else {
                zoomLevel = 1;
                alert("Warning: the home position and the safe aren't set. Please set them in the Map Editor");
            }
        }
        map.setCenter(new GLatLng(homePosition[0], homePosition[1]), zoomLevel);
        this.initHomePosition(homePosition[0], homePosition[1]);
        var POIList = mapData.getPOIList();
        
        for (i = 0; i < POIList.length; i++) {
            console.debug("POI LIST i: " + i);
            var name = POIList[i].name;
            map.addOverlay(POIList[i]);
            this.createMarkerHTML(POIList[i]);
            
        }
        // show safe area and home area
        if (safeArea != null) {
            map.addOverlay(safeArea);
        }
        if (homeArea != null) {
            map.addOverlay(homeArea);
        }
       
    }
});
