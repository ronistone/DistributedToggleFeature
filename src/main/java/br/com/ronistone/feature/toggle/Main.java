package br.com.ronistone.feature.toggle;


import javax.management.*;

import java.lang.management.ManagementFactory;
import java.net.UnknownHostException;

import static java.lang.Thread.sleep;

public class Main {

    static boolean stop = false;
    public static void main(String[] args) throws InterruptedException, MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, UnknownHostException {

        testFeatureToggle(FeatureToggleFactory.createFeatureToggle(FeatureExample.values()));

    }

    private static void testFeatureToggle(FeatureToggle featureToggle) throws MalformedObjectNameException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException, InterruptedException {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = new ObjectName("FeaturesToggle:00=FeaturesToggle,name=FeatureToggle");

        mBeanServer.registerMBean(featureToggle, objectName);

//        featureToggle.setBooleanFeature(FeatureExample.HOLY_DIVE.name(), false);

        Runtime.getRuntime().addShutdownHook(new Thread("feature-toggle-shutdown-hook") {
            @Override
            public void run() {
                stopLoop();
            }
        });

        while(!stop) {
            sleep(100);
        }
        featureToggle.stop();
    }

    private static void stopLoop(){
        stop = true;
    }
}
