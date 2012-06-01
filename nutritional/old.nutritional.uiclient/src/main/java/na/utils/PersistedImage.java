package na.utils;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PersistedImage implements Serializable {
	public byte[] data;
	public int id;
	public long time;
}
