package br.com.ronistone.feature.toggle;

import br.com.ronistone.feature.toggle.autofind.FeatureChangeNotifyType;
import br.com.ronistone.feature.toggle.autofind.FeatureChangeObservable;
import br.com.ronistone.feature.toggle.autofind.FeatureValueHolder;
import br.com.ronistone.feature.toggle.autofind.Finder;
import br.com.ronistone.feature.toggle.exception.FeatureIncorrectTypeValue;
import br.com.ronistone.feature.toggle.exception.FeatureNotExist;

import java.util.Observable;
import java.util.Observer;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FeatureToggle implements FeatureToggleMBean, Observer {

    private static final Logger LOG = Logger.getLogger(FeatureToggle.class.getName());

    private ConcurrentMap<String, Object> features = new ConcurrentHashMap<String, Object>();
    private ConcurrentMap<String, UUID> idLastChange = new ConcurrentHashMap<String, UUID>();
    private Finder finder;
    private FeatureChangeObservable observable;


    FeatureToggle(Feature[] features, FeatureChangeObservable observable) {

        this.observable = observable;

        if( features != null ) {
            for(Feature feature: features) {
                this.features.put( feature.name(), feature.getValue() );
                this.idLastChange.put( feature.name(), UUID.randomUUID() );
            }
        }

        observable.addObserver(this);
    }

    @Override
    public ConcurrentMap<String, Object> getFeatures() {
        return features;
    }

    @Override
    public Object getFeatureValue(Feature feature) {
        return features.get( feature.name() );
    }

    @Override
    public String getFeatureStringValue(Feature feature) {
        return (String) features.get( feature.name() );
    }

    @Override
    public int getFeatureIntegerValue(Feature feature) {
        return (Integer) features.get( feature.name() );
    }

    @Override
    public boolean getFeatureBooleanValue(Feature feature) {
        return (Boolean) features.get( feature.name() );
    }

    @Override
    public float getFeatureFloatValue(Feature feature) {
        return (Float) features.get( feature.name() );
    }

    @Override
    public double getFeatureDoubleValue(Feature feature) {
        return (Double) features.get( feature.name() );
    }

    @Override
    public Object getFeatureValue(String featureName) {
        return features.get(featureName);
    }

    @Override
    public void setBooleanFeature(String featureName, boolean value) {
        setFeatureValue(featureName, value, Boolean.class);
    }

    @Override
    public void setStringFeature(String featureName, String value) {
        setFeatureValue(featureName, value, String.class);
    }

    @Override
    public void setIntegerFeatures(String featureName, int value) {
        setFeatureValue( featureName, value, Integer.class );
    }

    @Override
    public void setFloatFeatures(String featureName, float value) {
        setFeatureValue( featureName, value, Float.class );
    }

    @Override
    public void setDoubleFeatures(String featureName, double value) {
        setFeatureValue( featureName, value, Double.class );
    }

    protected void setFeatureValue(String featureName, Object value, Class valueClass) {
        setFeatureValue( featureName, value, valueClass, null );
    }

    private void setFeatureValue(String featureName, Object value, Class valueClass, UUID id) {
        if(LOG.isLoggable(Level.INFO)) {
            LOG.info("Trying change feature toggle[" + featureName + "] = " + value);
        }
        String message;
        if( !features.containsKey(featureName) ) {
            message = "Feature " + featureName + " does not exist";
            LOG.warning( message );
            throw new FeatureNotExist(message);
        } else if( features.get(featureName) != null && !valueClass.isAssignableFrom(features.get(featureName).getClass()) ){
            message = "Wrong type value the correct type is " + features.get(featureName).getClass().getName();
            LOG.warning( message );
            throw new FeatureIncorrectTypeValue( message );
        } else  {
            if(LOG.isLoggable(Level.INFO)) {
                LOG.info("Changing Feature Toggle[" + featureName + "] = " + value);
            }

            if( id == null || !id.equals(idLastChange.get(featureName)) ) {
                features.put(featureName, value);

                id = (id != null)? id : UUID.randomUUID();

                idLastChange.put(featureName, id);

                observable.notifyPeers(featureName, value, id);
            }
        }
    }

    public void updateFeatureFlag(String name, Object value, UUID id) {
        if( features.containsKey(name) && id != null ) {
            setFeatureValue(name, value, value.getClass(), id);
        }
    }

    public void setFinder(Finder finder) {
        this.finder = finder;
    }

    public void stop() {
        if(finder != null) {
            finder.stop();
        }
    }

    public void start() {
        if(finder != null) {
            finder.startAsync();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        FeatureValueHolder holder = (FeatureValueHolder) arg;

        LOG.info("FeatureToggle Received UPDATE from observable");
        if(FeatureChangeNotifyType.FEATURE_TOGGLE.equals(holder.getType())) {
            updateFeatureFlag(holder.getName(), holder.getValue(), holder.getIdLastChange());
        }
    }
}
