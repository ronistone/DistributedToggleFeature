package br.com.ronistone.feature.toggle;

public enum FeatureExample implements Feature {
    HOLY_DIVE(true)
    ;


    private final Object value;

    FeatureExample(Object value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return value;
    }
}
