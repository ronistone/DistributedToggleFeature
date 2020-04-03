package br.com.ronistone.feature.toggle.exception;

public class FeatureNotExist extends RuntimeException {

    public FeatureNotExist(String message) {
        super(message);
    }

    public FeatureNotExist(String message, Throwable cause) {
        super(message, cause);
    }

    public FeatureNotExist(Throwable cause) {
        super(cause);
    }

    public FeatureNotExist(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
