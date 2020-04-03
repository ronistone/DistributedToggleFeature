package br.com.ronistone.feature.toggle;

import br.com.ronistone.feature.toggle.autofind.SingleThreadFinder;
import br.com.ronistone.feature.toggle.autofind.ip.generator.FullIPGenerator;
import br.com.ronistone.feature.toggle.autofind.FeatureChangeObservable;
import br.com.ronistone.feature.toggle.autofind.Finder;
import br.com.ronistone.feature.toggle.autofind.ip.generator.LazyIPGenerator;
import br.com.ronistone.feature.toggle.util.IPUtil;
import br.com.ronistone.feature.toggle.util.FeatureToggleProperties;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class FeatureToggleFactory {

    private FeatureToggleFactory() {}


    public static FeatureToggle createFeatureToggle(Feature[] features) {
        FeatureToggleProperties.loadProperties();
        FeatureChangeObservable observable = new FeatureChangeObservable();

        FeatureToggle featureToggle = new FeatureToggle(features, observable);
        if( "true".compareToIgnoreCase(FeatureToggleProperties.getProperty("feature.toggle.finder.enable")) == 0) {
            featureToggle.setFinder(createFinder(featureToggle, observable));
        }
        featureToggle.start();
        return featureToggle;
    }

    private static Finder createFinder(FeatureToggle featureToggle, FeatureChangeObservable observable) {
        Finder finder = new SingleThreadFinder(observable);
        String pairs = FeatureToggleProperties.getProperty("feature.toggle.finder.pairs");
        String subnet = FeatureToggleProperties.getProperty("feature.toggle.finder.pairs.subnet");
        String subnetBegin = FeatureToggleProperties.getProperty("feature.toggle.finder.pairs.subnet.begin");
        String subnetEnd = FeatureToggleProperties.getProperty("feature.toggle.finder.pairs.subnet.end");

        InetAddress beginAddress;
        InetAddress endAddress;

        finder.setFeatureToggle(featureToggle);

        try {
            if (pairs != null) {
                finder.setGenerator(new FullIPGenerator(pairs.split(",")));
                return finder;
            } else if (subnet != null) {
                if (subnet.indexOf('/') > 0) {
                    String[] ipAndMask = subnet.split("/");
                    beginAddress = InetAddress.getByName(ipAndMask[0]);
                    endAddress = IPUtil.calculateEndIp(beginAddress, Short.parseShort(ipAndMask[1]));

                } else {
                    throw new RuntimeException("wrong subnet format (p.e: xxx.xxx.xxx.xxx/xx)");
                }
            } else if (subnetBegin != null && subnetEnd != null) {
                beginAddress = InetAddress.getByName(subnetBegin);
                endAddress = InetAddress.getByName(subnetEnd);
            } else {
                throw new RuntimeException("Invalid subnet, disable finder if toggle feature needs run on only one instance");
            }

            if( "full".compareToIgnoreCase( FeatureToggleProperties.getProperty("feature.toggle.finder.ip.iteration.strategy") ) == 0) {
                finder.setGenerator(new FullIPGenerator(beginAddress, endAddress));
            } else {
                finder.setGenerator(new LazyIPGenerator(beginAddress, endAddress));
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException("Host invalid", e);
        }

        return finder;
    }
}
