package br.com.ronistone.feature.toggle.autofind.ip.generator;

import br.com.ronistone.feature.toggle.util.IPUtil;

import java.net.InetAddress;
import java.nio.ByteBuffer;

public class FullIPGenerator extends IPGenerator {

    private String[] ips;
    private int current;

    public FullIPGenerator(InetAddress begin, InetAddress end) {
        super();
        ips = getAllAddress(begin, end);
        current = 0;
    }

    public FullIPGenerator(String[] ips) {
        super();
        this.ips = ips;
        current = 0;
    }

    @Override
    public String next() {
        if(current >= ips.length) {
            current = 0;
            return null;
        }
        if(localAddresses.contains(ips[current])){
            current++;
            return next();
        }
        return ips[current++];
    }

    private String[] getAllAddress(InetAddress begin, InetAddress end) {
        ByteBuffer beginBuffer = ByteBuffer.wrap(begin.getAddress());
        ByteBuffer endBuffer = ByteBuffer.wrap(end.getAddress());

        int beginIp = beginBuffer.getInt();
        int endIp = endBuffer.getInt();

        String[] ips = new String[endIp - beginIp + 1];

        int i = 0;
        for (int ip = beginIp; ip <= endIp ; ip++) {
            ips[i] = IPUtil.getIpStringFromInt(ip);
            i++;
        }

        return ips;
    }
}
