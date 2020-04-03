package br.com.ronistone.feature.toggle.autofind.ip.generator;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public abstract class IPGenerator {

    protected Set<String> localAddresses = new HashSet<>();

    public IPGenerator() {
        try {
            processLocalAddresses();
        } catch (SocketException e) {
            throw new RuntimeException("IO exception on get the network interfaces", e);
        }
    }

    public abstract String next();


    public void processLocalAddresses() throws SocketException {
        Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
        while(interfaces != null && interfaces.hasMoreElements())
        {
            NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();
            Enumeration addresses = networkInterface.getInetAddresses();
            while (addresses.hasMoreElements())
            {
                InetAddress address = (InetAddress) addresses.nextElement();
                System.out.println(address.getHostAddress());
                localAddresses.add(address.getHostAddress());
            }
        }
    }
}
