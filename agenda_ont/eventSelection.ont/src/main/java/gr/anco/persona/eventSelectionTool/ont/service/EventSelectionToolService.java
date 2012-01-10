package gr.anco.persona.eventSelectionTool.ont.service;

import gr.anco.persona.eventSelectionTool.ont.EventSelectionTool;

import java.util.Hashtable;

import org.persona.ontology.Service;
import org.persona.ontology.expr.Restriction;

public class EventSelectionToolService extends Service
{
	public static final String MY_URI;
	public static final String PROP_CONTROLS;
	
		
	private static Hashtable EventSelectionToolRestrictions = new Hashtable(1);
	
	static 
	{	
		// URI = http://ontology.persona.anco.gr/EventSelectionTool.owl#EventSelectionToolService
		MY_URI = EventSelectionTool.EVENT_SELECTION_TOOL_NAMESPACE + "EventSelectionToolService";
		// URI = http://ontology.persona.anco.gr/EventSelectionTool.owl#controls
		PROP_CONTROLS = EventSelectionTool.EVENT_SELECTION_TOOL_NAMESPACE + "controls";
		
		
		
		//Register EventSelectionToolService class
		register(EventSelectionToolService.class);

		//EventSelctionToolService "controls" EventSelectionTool
		addRestriction(
				Restriction.getAllValuesRestriction(PROP_CONTROLS, EventSelectionTool.MY_URI),
					new String[] {PROP_CONTROLS},
					EventSelectionToolRestrictions);
	
	}
	
	public EventSelectionToolService(String uri) 
	{
		super(uri);
	}
	
	public static Restriction getClassRestrictionsOnProperty(String propURI) 
	{
		if (propURI == null)
			return null;
	
		Object r = EventSelectionToolRestrictions.get(propURI);
		
		if (r instanceof Restriction)
			return (Restriction) r;
		
		return Service.getClassRestrictionsOnProperty(propURI);
	}
	
	public static String getRDFSComment() 
	{
		return "The class of services controling the EventSelectionTool Service.";
	}
	
	public static String getRDFSLabel() 
	{
		return "EventSelectionToolService";
	}
		

	
	protected Hashtable getClassLevelRestrictions()
	{
		return EventSelectionToolRestrictions;
	}

	
	public int getPropSerializationType(String propURI)
	{
		return PROP_CONTROLS.equals(propURI)? PROP_SERIALIZATION_FULL
				: PROP_SERIALIZATION_OPTIONAL;
	}

	public boolean isWellFormed()
	{
		return true;
	}

}
