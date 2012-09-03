package na.services.scheduler;

import java.util.LinkedList;

class Avisos {
	private LinkedList<ExtraAdvise> stackEventos = new LinkedList<ExtraAdvise>();
	private AvisosChecker checker = new AvisosChecker();

	protected synchronized boolean addItem(ExtraAdvise object) {
		if (checker.containsKey(object.advise.getID())) {
//			log.info("item exists: "+((ExtraAdvise)object).advise.getTitle());
			return true;
		} else {
//			log.info("item does not exist, let's add it: "+((ExtraAdvise)object).advise.getTitle());
			this.checker.put(object.advise.getID(), object);
			return this.stackEventos.add(object);
		}
		
	}

	public synchronized boolean isEmpty() {
		return this.stackEventos.isEmpty();
	}
	
	protected synchronized boolean contains(ExtraAdvise object) {
		if (checker.containsKey(object.advise.getID())) {
//			log.info("item exists: "+((ExtraAdvise)object).advise.getTitle());
			return true;
		} else {
//			log.info("item does not exist: "+((ExtraAdvise)object).advise.getTitle());
			return false;
		}
	}

	
	/**
	 * Adds an object at the end of the stack
	 * 
	 * @param o Object
	 */
	public synchronized void setLast(ExtraAdvise o) {
//		log.info("stack contiene: "+this.stackEventos.size() + " items antes del setLast.");
		if (!this.checker.containsKey(o.advise.getID())) { // insertar solo si no existe
//			log.info("inserto "+((Note)o).getTitle());
			this.checker.put(o.advise.getID(), o);
			this.stackEventos.addLast(o);
		}
//		else
//			log.info("item "+((Note)o).getTitle() + " already in the stack");
	}
	
	/**
	 * Removes the first object of the stack
	 * 
	 * @return the object
	 */
	protected synchronized ExtraAdvise removeFirst() {
//		log.info("stack contiene: "+this.stackEventos.size() + " items antes del removeFirst.");
		ExtraAdvise a = this.stackEventos.removeFirst();
		if (a!=null) {
			this.checker.remove(a.advise.getID());
		}
		return a; 
	}
	
	/**
	 * Gets the first object of the stack
	 * 
	 * @return the object
	 */
	protected synchronized ExtraAdvise getFirst() {
//		log.info("stack contiene: "+this.stackEventos.size() + " items antes del removeFirst.");
		ExtraAdvise a = this.stackEventos.getFirst();
		return a; 
	}
}
