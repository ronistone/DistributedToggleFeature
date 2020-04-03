package br.com.ronistone.feature.toggle;

public enum TestFeature implements Feature {

    FEATURE_BOOLEAN_SAMPLE(true),
    FEATURE_BOOLEAN_DISABLE_SAMPLE(false),
    FEATURE_STRING_SAMPLE("TEST_STRING"),
    FEATURE_INT_SAMPLE(123),
    FEATURE_FLOAT_SAMPLE((float)321.123),
    FEATURE_DOUBLE_SAMPLE((double) 123.321);

    private final Object value;

    TestFeature(Object value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return value;
    }


}
