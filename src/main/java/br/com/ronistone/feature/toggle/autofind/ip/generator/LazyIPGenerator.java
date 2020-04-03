package br.com.ronistone.feature.toggle.autofind.ip.generator;

import br.com.ronistone.feature.toggle.util.IPUtil;

import java.net.InetAddress;
import java.nio.ByteBuffer;

public class LazyIPGenerator extends IPGenerator {

    private int beginIp;
    private int endIp;
    private int currentIp;


    public LazyIPGenerator(InetAddress begin, InetAddress end) {
        super();
        ByteBuffer beginBuffer = ByteBuffer.wrap(begin.getAddress());
        ByteBuffer endBuffer = ByteBuffer.wrap(end.getAddress());

        beginIp = beginBuffer.getInt();
        endIp = endBuffer.getInt();
        currentIp = beginIp;
    }

    @Override
    public String next() {
        if(currentIp > endIp) {
            currentIp = beginIp;
            return null;
        }
        String currentAddress = IPUtil.getIpStringFromInt(currentIp++);
        if(localAddresses.contains(currentAddress)){
            return next();
        }
        return currentAddress;
    }
}
