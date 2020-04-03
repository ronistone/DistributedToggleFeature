package br.com.ronistone.feature.toggle.exception;

public class FeatureIncorrectTypeValue extends RuntimeException {

    public FeatureIncorrectTypeValue(String message) {
        super(message);
    }

    public FeatureIncorrectTypeValue(String message, Throwable cause) {
        super(message, cause);
    }

    public FeatureIncorrectTypeValue(Throwable cause) {
        super(cause);
    }

    public FeatureIncorrectTypeValue(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
