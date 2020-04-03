package br.com.ronistone.feature.toggle.autofind.server.handlers;

import br.com.ronistone.feature.toggle.autofind.server.Message;
import br.com.ronistone.feature.toggle.util.FinderUtil;
import br.com.ronistone.feature.toggle.autofind.server.FinderServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Logger;

public class FinderConnectionHandler implements Runnable {

    private static final Logger LOG = Logger.getLogger(FinderConnectionHandler.class.getName());

    private Socket socket;
    private FinderServer server;

    public FinderConnectionHandler(FinderServer server, Socket socket) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            if(socket == null) {
                return;
            }
            ObjectInputStream inputStream = FinderUtil.getObjectInputStreamFromSocket(socket);

            Message message = (Message) inputStream.readObject();


            LOG.info("Received Message with type = " + message.getType().name());

            switch (message.getType()) {
                case CONNECT:
                    server.createNewConnectionHandler(socket);
                    FinderUtil.sendConnected(socket);
                    break;
                case RECONNECT:
                    InetAddress peerAddress = socket.getInetAddress();
                    if(server.isConnected(peerAddress.getHostAddress())){
                        server.updatePeerSocket(peerAddress, socket);
                    } else {
                        server.createNewConnectionHandler(socket);
                        FinderUtil.sendConnected(socket);
                    }
                    break;
                default:
                    LOG.info("Message Type not expected!");
                    break;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }




}
