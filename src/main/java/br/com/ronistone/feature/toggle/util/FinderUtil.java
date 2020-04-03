package br.com.ronistone.feature.toggle.util;

import br.com.ronistone.feature.toggle.autofind.server.Message;
import br.com.ronistone.feature.toggle.autofind.server.MessageType;
import br.com.ronistone.feature.toggle.autofind.server.FinderServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;
import java.util.logging.Logger;

public class FinderUtil {

    private static final Logger LOG = Logger.getLogger(FinderUtil.class.getName());

    // TODO use property to define server port
    public static final int PEER_PORT = 12345;

    public static void sendConnected(Socket socket) {
        sendMessage(socket, MessageType.CONNECTED);
    }
    public static void sendConnect(Socket socket) {
        sendMessage(socket, MessageType.CONNECT);
    }

    public static void sendReconnected(Socket socket) {
        sendMessage(socket, MessageType.RECONNECT);
    }

    public static void sendChangeFeature(Socket socket, String name, Object value, UUID id) {
        sendMessage(socket, MessageType.CHANGE_FEATURE_VALUE, name, value, id);
    }

    private static void sendMessage(Socket socket, MessageType type) {
        sendMessage(socket, type, null, null, null);
    }

    private static void sendMessage(Socket socket, MessageType reconnect, String name, Object value, UUID id) {
        try {
            if(socket == null) {
                LOG.warning("Socket is null dropping message!");
            }
            ObjectOutputStream outputStream = getObjectOutputStreamFromSocket(socket);
            outputStream.writeObject(new Message(reconnect, name, value, id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ObjectInputStream getObjectInputStreamFromSocket(Socket socket) throws IOException {
        return new ObjectInputStream(socket.getInputStream());
    }

    public static ObjectOutputStream getObjectOutputStreamFromSocket(Socket socket) throws IOException {
        return new ObjectOutputStream(socket.getOutputStream());
    }

    public static synchronized Socket doSafeConnection(FinderServer server, String peerAddress) throws IOException {
        if(!server.isConnected(peerAddress)) {
            return new Socket(peerAddress, FinderUtil.PEER_PORT);
        }
        return null;
    }
}
