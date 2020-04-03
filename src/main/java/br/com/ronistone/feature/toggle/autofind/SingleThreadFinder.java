package br.com.ronistone.feature.toggle.autofind;

import br.com.ronistone.feature.toggle.autofind.server.FinderServer;
import br.com.ronistone.feature.toggle.util.FinderUtil;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

public class SingleThreadFinder extends Finder {

    private static final Logger LOG = Logger.getLogger(SingleThreadFinder.class.getName());

    private boolean stop = false;
    private FinderServer server;

    public SingleThreadFinder(FeatureChangeObservable observable) {
        super(observable);
    }

    @Override
    public void stop() {
        stop = true;
        server.stop();
    }


    @Override
    protected void start() throws InterruptedException {
        LOG.info("Creating Server...");
        server = new FinderServer(this);
        new Thread(server, "feature-toggle-finder-server").start();

        LOG.info("Running find...");
        while(!stop) {
            String nextAddress = generator.next();
            try {
                if (nextAddress == null) {
                    sleep(10000);
                    continue;
                }
                LOG.info("next Address " + nextAddress);
                if (!server.isConnected(nextAddress)) {
                    Socket socket = FinderUtil.doSafeConnection(server, nextAddress);

                    if(socket != null) {
                        server.createNewConnectionHandler(socket);
                        FinderUtil.sendConnect(socket);
                    } else {
                        LOG.info("was not possible to connect with " + nextAddress);
                    }
                }
            } catch (IOException e) {
                LOG.info("Fail to connect with " + nextAddress);
//                e.printStackTrace();
            }
        }
    }

}
