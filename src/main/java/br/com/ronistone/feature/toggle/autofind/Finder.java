package br.com.ronistone.feature.toggle.autofind;

import br.com.ronistone.feature.toggle.FeatureToggle;
import br.com.ronistone.feature.toggle.autofind.ip.generator.IPGenerator;
import br.com.ronistone.feature.toggle.autofind.server.FinderServer;

import java.io.IOException;

public abstract class Finder {

    protected IPGenerator generator;
    protected FeatureToggle featureToggle;
    protected FinderServer server;
    protected FeatureChangeObservable observable;

    public Finder(FeatureChangeObservable observable) {
        this.observable = observable;
    }

    public void startAsync() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    start();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "feature-toggle-single-thread-finder").start();
    }

    public void setGenerator(IPGenerator generator) {
        this.generator = generator;
    }

    public abstract void stop();

    public FeatureToggle getFeatureToggle() {
        return featureToggle;
    }

    public void setFeatureToggle(FeatureToggle featureToggle) {
        this.featureToggle = featureToggle;
    }

    public FeatureChangeObservable getObservable() {
        return observable;
    }

    protected abstract void start() throws IOException, InterruptedException;
}
