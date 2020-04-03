package br.com.ronistone.feature.toggle.autofind.server.handlers;

import br.com.ronistone.feature.toggle.autofind.server.Message;
import br.com.ronistone.feature.toggle.autofind.server.MessageType;
import br.com.ronistone.feature.toggle.autofind.FeatureChangeNotifyType;
import br.com.ronistone.feature.toggle.autofind.FeatureChangeObservable;
import br.com.ronistone.feature.toggle.autofind.FeatureValueHolder;
import br.com.ronistone.feature.toggle.autofind.server.FinderServer;
import br.com.ronistone.feature.toggle.util.FinderUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;


public class PeerConnectionHandler implements Runnable, Observer {

    private static final Logger LOG = Logger.getLogger(PeerConnectionHandler.class.getName());

    private FinderServer server;
    private Socket socket;
    private String peerAddress;
    private FeatureChangeObservable observable;

    public PeerConnectionHandler(FinderServer server, Socket socket, FeatureChangeObservable observable) {
        this.server = server;
        this.socket = socket;
        this.peerAddress = socket.getInetAddress().getHostAddress();
        this.observable = observable;
        observable.addObserver(this);
    }


    @Override
    public void run() {
        try {

            while (true) {
                if(socket == null) {
                    break;
                }
                ObjectInputStream inputStream = FinderUtil.getObjectInputStreamFromSocket(socket);
                Message message = (Message) inputStream.readObject();

                switch (message.getType()) {
                    case CONNECTED:
                        LOG.info("Peer " + socket.getInetAddress().getHostAddress() + " Connected!");
                        break;
                    case CHANGE_FEATURE_VALUE:
                        LOG.info("Received CHANGE_FEATURE_VALUE!");
                        observable.notifyFeatureToggle(message.getName(), message.getValue(), message.getId());
                        break;
                    case NEW_FEATURE_VALUE:
                        break;
                    default:
                        LOG.info("Message Type not expected!");
                }
//                if(message.getType() == MessageType.CHANGE_FEATURE_VALUE) {
//                    break;
//                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        tryCloseSocket();
        server.connectionLost(peerAddress);
    }

    private void tryCloseSocket() {
        try {
            if(socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Observable o, Object arg) {
        LOG.info("PeerConnection Received UPDATE from observable");
        FeatureValueHolder holder = (FeatureValueHolder) arg;
        if(FeatureChangeNotifyType.PEERS.equals(holder.getType())){
            LOG.info("Sending feature change...");
            FinderUtil.sendChangeFeature(socket, holder.getName(), holder.getValue(), holder.getIdLastChange());
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
        this.peerAddress = socket.getInetAddress().getHostAddress();
    }
}
