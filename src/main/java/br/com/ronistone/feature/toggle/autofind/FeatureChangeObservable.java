package br.com.ronistone.feature.toggle.autofind;

import java.util.Observable;
import java.util.UUID;

public class FeatureChangeObservable extends Observable {

    public FeatureChangeObservable() {
        super();
    }

    public void notifyPeers(String name, Object value, UUID id) {
        notify(name, value, id, FeatureChangeNotifyType.PEERS);
    }

    public void notifyFeatureToggle(String name, Object value, UUID id) {
        notify(name, value, id, FeatureChangeNotifyType.FEATURE_TOGGLE);
    }

    private void notify(String name, Object value, UUID id, FeatureChangeNotifyType featureToggle) {
        FeatureValueHolder holder = FeatureValueHolder.build()
                .name(name)
                .value(value)
                .type(featureToggle)
                .idLastValue(id)
                .create();
        setChanged();
        notifyObservers(holder);
    }


}
