package br.com.ronistone.feature.toggle.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPUtil {

    public static String getIpStringFromInt(int ip) {
        return getOctact(ip, 1) + "." + getOctact(ip, 2) + "." + getOctact(ip, 3) + "." + getOctact(ip, 4);
    }

    public static String getOctact(int ip, int position) {
        return String.valueOf((ip >> (32 - (position * 8))) & 0xff );
    }

    public static InetAddress calculateEndIp(InetAddress begin, short mask) throws UnknownHostException {

        byte[] ip = begin.getAddress();

        short octat = (short) (mask/8);
        short bits = (short) (mask % 8);

        byte[] finalBytes = ip.clone();

        if(bits > 0) {
            finalBytes[octat] = (byte)(((short)finalBytes[octat]) | (0xff >> bits));
            octat++;
        }

        for( int o = octat; o < 4; o++ ) {
            finalBytes[o] = (byte) 0xff;
        }

        // removing broadcast ip
        finalBytes[3] = (byte)(((short)finalBytes[3]) & 0xfe);

        return InetAddress.getByAddress(finalBytes);
    }

}
