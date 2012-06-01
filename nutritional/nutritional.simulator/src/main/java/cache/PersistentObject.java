package cache;


import java.io.Serializable;

@SuppressWarnings("serial")
public class PersistentObject implements Serializable {
	protected long time;
	protected Object object;
}