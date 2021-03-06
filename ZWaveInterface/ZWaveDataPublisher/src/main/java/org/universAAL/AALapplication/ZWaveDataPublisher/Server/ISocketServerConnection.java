package org.universAAL.AALapplication.ZWaveDataPublisher.Server;

import java.io.Closeable;
import java.net.InetAddress;
import java.nio.ByteBuffer;

/**
 * Interface to be implemented by the classes handling socket connections.
 * 
 * @author fraperod
 * @version $Rev: 5339 $ $Date: 2012-09-25 17:17:28 +0200 (mar, 25 sep 2012) $
 */
public interface ISocketServerConnection extends Closeable {

    /**
     * Set the listener.
     * 
     * @param listener listener.
     */
    void setConnectionLister(ISocketServerConnectionListener listener);


    /**
     * Gets the number of packets received through this connection.
     * 
     * @return Total number of packets received through this connection
     */
    int getConnectionReceivedPackets();


    /**
     * Gets the number of packets successfully sent.
     * 
     * @return Total number of packets sent by the connection. Which is the
     *         number of writeToConnection calls that finished successfully
     */
    int getConnectionSentPackets();


    /**
     * Gets the identifier number of the connection. Each connection will have
     * an unique identifier.
     * 
     * @return Identifier number of the connection
     */
    int getId();


    /**
     * Returns false if the connection connections is already closed, true if it
     * is still opened.
     * 
     * @return true if the connection is closed, false if it is open
     */
    boolean isConnectionClosed();


    /**
     * .
     * 
     * @param connectionId integer used to identify the connection.
     */
    void setId(int connectionId);


    /**
     * Sends the data passed in a ByteBuffer through the connection. This is an
     * asynchronous method, so it returns immediately. When the packet is passt
     * to the lower layer {@link ISocketServerConnectionListener packetSent} is
     * called back
     * 
     * @param data byte buffer containing the data to be sent
     */
    void writeToConnection(ByteBuffer data);


    /**
     * Returns the remote IP address.
     * 
     * @return the remote IP address.
     */
    InetAddress getRemoteAddress();


    /**
     * Returns the remote port.
     * 
     * @return the remote port.
     */
    int getRemotePort();

}
