package na.services.scheduler;

import java.util.HashMap;
import java.util.Map;


class AvisosChecker {
	private Map<Integer, ExtraAdvise> map = new HashMap<Integer, ExtraAdvise>();
	
	protected synchronized boolean containsKey(int key) {
		return this.map.containsKey(new Integer(key));
	}
	
	public synchronized Object get(Object key) {
		return this.map.get(key);
	}
	
	protected synchronized ExtraAdvise put(Integer key, ExtraAdvise value) {
		return this.map.put(key, value);
	}
	
	protected synchronized ExtraAdvise remove(Integer key) {
		return this.map.remove(key);
	}
	
	protected synchronized boolean addItem(ExtraAdvise object) {
		if (map.containsKey(object.advise.getID())) {
//			log.info("item exists: "+((ExtraAdvise)object).advise.getTitle());
			return true;
		} else {
//			log.info("item does not exist, let's add it: "+((ExtraAdvise)object).advise.getTitle());
			this.map.put(object.advise.getID(), object);
//			return this.stackEventos.add(object);
			return false;
		}
		
	}
}
