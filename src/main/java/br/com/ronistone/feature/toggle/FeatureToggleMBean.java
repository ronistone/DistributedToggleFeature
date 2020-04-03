package br.com.ronistone.feature.toggle;

import java.util.concurrent.ConcurrentMap;

public interface FeatureToggleMBean {

    ConcurrentMap<String, Object> getFeatures();


    Object getFeatureValue(Feature feature);

    String getFeatureStringValue(Feature feature);

    int getFeatureIntegerValue(Feature feature);

    boolean getFeatureBooleanValue(Feature feature);

    float getFeatureFloatValue(Feature feature);

    double getFeatureDoubleValue(Feature feature);

    Object getFeatureValue(String featureName);

    void setBooleanFeature(String featureName, boolean value);

    void setStringFeature(String featureName, String value);

    void setIntegerFeatures(String featureName, int value);

    void setFloatFeatures(String featureName, float value);

    void setDoubleFeatures(String featureName, double value);
}
