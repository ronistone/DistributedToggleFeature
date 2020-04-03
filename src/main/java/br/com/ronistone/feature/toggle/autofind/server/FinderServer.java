package br.com.ronistone.feature.toggle.autofind.server;

import br.com.ronistone.feature.toggle.autofind.Finder;
import br.com.ronistone.feature.toggle.autofind.server.handlers.FinderConnectionHandler;
import br.com.ronistone.feature.toggle.autofind.server.handlers.PeerConnectionHandler;
import br.com.ronistone.feature.toggle.util.FinderUtil;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class FinderServer implements Runnable {

    private static final Logger LOG = Logger.getLogger(FinderServer.class.getName());

    // TODO use property to define timeout
    private static final int SOCKET_TIMEOUT = 2000;
    private Executor executor;
    private boolean stop = false;
    private Map<String, PeerConnectionHandler> peers = new HashMap();
    private Finder finder;
    private static int peerId = 0;

    public FinderServer(Finder finder) {
        this.finder = finder;
    }

    @Override
    public void run() {
        LOG.info("Starting Server...");
        try (ServerSocket server = new ServerSocket(FinderUtil.PEER_PORT)){
            LOG.info("Server started!");

            // TODO use property to define thread pool size
            executor = Executors.newFixedThreadPool(10);

            server.setSoTimeout(SOCKET_TIMEOUT);
            while (!stop) {
                try {
                    //LOG.info("Waiting for connection...");
                    Socket socket = server.accept();
                    LOG.info("Connected!");

                    executor.execute(new FinderConnectionHandler(this, socket));
                } catch (SocketTimeoutException ignored) {}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addPeer(InetAddress address, PeerConnectionHandler peerHandler) {
        peers.put(address.getHostAddress(), peerHandler);
    }

    public void stop() {
        stop = true;
    }

    public boolean isConnected(String nextAddress) {
        LOG.info("isConnected [" + nextAddress + "] = " + peers.containsKey(nextAddress));
        return peers.containsKey( nextAddress );
    }

    public void updatePeerSocket(InetAddress peerAddress, Socket socket) {
        PeerConnectionHandler peerHandler = peers.get(peerAddress.getHostAddress());
        Socket oldSocket = peerHandler.getSocket();

        peerHandler.setSocket(socket);

        if(!oldSocket.isClosed()) {
            try {
                oldSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void connectionLost(String peerAddress) {
        removePeer(peerAddress);

        Socket newConnection = retryConnection(peerAddress);

        if(newConnection != null) {
            createNewConnectionHandler(newConnection);
        }
    }

    private Socket retryConnection(String peerAddress) {
        try {
            LOG.info("Trying connect to " + peerAddress);
            Socket socket = FinderUtil.doSafeConnection(this, peerAddress);

            FinderUtil.sendReconnected(socket);

            return socket;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void removePeer(String peerAddress) {
        peers.remove(peerAddress);
    }

    public void createNewConnectionHandler(Socket socket) {
        PeerConnectionHandler peerHandler = new PeerConnectionHandler(this, socket, finder.getObservable());
        addPeer(socket.getInetAddress(), peerHandler);

        new Thread(peerHandler, "feature-toggle-peer-connection-handler-" + peerId++).start();
    }

}
