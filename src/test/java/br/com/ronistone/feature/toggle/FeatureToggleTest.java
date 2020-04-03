package br.com.ronistone.feature.toggle;

import br.com.ronistone.feature.toggle.mock.FeatureChangeObservableMock;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class FeatureToggleTest {

    private static final String STRING_VALUE = "TEST";
    private static final boolean BOOLEAN_VALUE = true;
    private static final int INT_VALUE = 666;
    private static final float FLOAT_VALUE = 666.333f;
    private static final double DOUBLE_VALUE = 666.333;
    public static final double DELTA = 0.1;

    @Test
    public void setBooleanFeature_wrongType() {
        try {
            FeatureToggle featureToggleService = new FeatureToggle( TestFeature.values(), new FeatureChangeObservableMock() );
            featureToggleService.setBooleanFeature(TestFeature.FEATURE_DOUBLE_SAMPLE.name(), BOOLEAN_VALUE);
            fail("This method must throw exception");

        } catch ( RuntimeException e ) {
            assertEquals("Expected exception because the type must be the same", "Wrong type value the correct type is java.lang.Double", e.getMessage());
        }
    }

    @Test
    public void setBooleanFeature_nonexistentBooleanFeature() {
        try {
            FeatureToggle featureToggleService = new FeatureToggle( TestFeature.values(), new FeatureChangeObservableMock());

            featureToggleService.setBooleanFeature("FEATURE_THAT_DOESNT_EXIST", BOOLEAN_VALUE);
            fail("This method must throw exception");

        } catch ( RuntimeException e ) {
            assertEquals("Expected exception because the this feature does not exist", "Feature FEATURE_THAT_DOESNT_EXIST does not exist", e.getMessage());
        }
    }

    @Test
    public void setBooleanFeature_success() {
        FeatureToggle featureToggleService = new FeatureToggle( TestFeature.values(), new FeatureChangeObservableMock() );
        featureToggleService.setBooleanFeature( TestFeature.FEATURE_BOOLEAN_DISABLE_SAMPLE.name(), BOOLEAN_VALUE );
        assertEquals( "The feature value must have changed", BOOLEAN_VALUE, featureToggleService.getFeatureBooleanValue(TestFeature.FEATURE_BOOLEAN_DISABLE_SAMPLE) );
    }

    @Test
    public void setStringFeature_wrongType() {
        try {
            FeatureToggle featureToggleService = new FeatureToggle( TestFeature.values(), new FeatureChangeObservableMock() );

            featureToggleService.setStringFeature(TestFeature.FEATURE_DOUBLE_SAMPLE.name(), STRING_VALUE);
            fail("This method must throw exception");

        } catch ( RuntimeException e ) {
            assertEquals("Expected exception because the type must be the same", "Wrong type value the correct type is java.lang.Double", e.getMessage());
        }
    }

    @Test
    public void setStringFeature_nonexistentStringFeature() {
        try {
            FeatureToggle featureToggleService = new FeatureToggle( TestFeature.values(), new FeatureChangeObservableMock() );
            featureToggleService.setStringFeature("FEATURE_THAT_DOESNT_EXIST", STRING_VALUE);
            fail("This method must throw exception");

        } catch ( RuntimeException e ) {
            assertEquals("Expected exception because the this feature does not exist", "Feature FEATURE_THAT_DOESNT_EXIST does not exist", e.getMessage());
        }
    }

    @Test
    public void setStringFeature_success() {
        FeatureToggle featureToggleService = new FeatureToggle( TestFeature.values(), new FeatureChangeObservableMock() );
        featureToggleService.setStringFeature( TestFeature.FEATURE_STRING_SAMPLE.name(), STRING_VALUE );
        assertEquals( "The feature value must have changed", STRING_VALUE, featureToggleService.getFeatureStringValue(TestFeature.FEATURE_STRING_SAMPLE) );
    }

    @Test
    public void setIntegerFeatures_wrongType() {
        try {
            FeatureToggle featureToggleService = new FeatureToggle( TestFeature.values(), new FeatureChangeObservableMock() );

            featureToggleService.setIntegerFeatures(TestFeature.FEATURE_DOUBLE_SAMPLE.name(), INT_VALUE);
            fail("This method must throw exception");

        } catch ( RuntimeException e ) {
            assertEquals("Expected exception because the type must be the same", "Wrong type value the correct type is java.lang.Double", e.getMessage());
        }
    }

    @Test
    public void setIntegerFeatures_nonexistentIntegerFeature() {
        try {
            FeatureToggle featureToggleService = new FeatureToggle( TestFeature.values(), new FeatureChangeObservableMock() );
            featureToggleService.setIntegerFeatures("FEATURE_THAT_DOESNT_EXIST", INT_VALUE);
            fail("This method must throw exception");

        } catch ( RuntimeException e ) {
            assertEquals("Expected exception because the this feature does not exist", "Feature FEATURE_THAT_DOESNT_EXIST does not exist", e.getMessage());
        }
    }

    @Test
    public void setIntegerFeatures_success() {
        FeatureToggle featureToggleService = new FeatureToggle( TestFeature.values(), new FeatureChangeObservableMock() );
        featureToggleService.setIntegerFeatures( TestFeature.FEATURE_INT_SAMPLE.name(), INT_VALUE);
        assertEquals( "The feature value must have changed", INT_VALUE, featureToggleService.getFeatureIntegerValue(TestFeature.FEATURE_INT_SAMPLE) );
    }

    @Test
    public void setFloatFeatures_wrongType() {
        try {
            FeatureToggle featureToggleService = new FeatureToggle( TestFeature.values(), new FeatureChangeObservableMock() );

            featureToggleService.setFloatFeatures(TestFeature.FEATURE_DOUBLE_SAMPLE.name(), FLOAT_VALUE);
            fail("This method must throw exception");

        } catch ( RuntimeException e ) {
            assertEquals("Expected exception because the type must be the same", "Wrong type value the correct type is java.lang.Double", e.getMessage());
        }
    }

    @Test
    public void setFloatFeatures_nonexistentFloatFeature() {
        try {
            FeatureToggle featureToggleService = new FeatureToggle( TestFeature.values(), new FeatureChangeObservableMock() );
            featureToggleService.setFloatFeatures("FEATURE_THAT_DOESNT_EXIST", FLOAT_VALUE);
            fail("This method must throw exception");

        } catch ( RuntimeException e ) {
            assertEquals("Expected exception because the this feature does not exist", "Feature FEATURE_THAT_DOESNT_EXIST does not exist", e.getMessage());
        }
    }

    @Test
    public void setFloatFeatures_success() {
        FeatureToggle featureToggleService = new FeatureToggle( TestFeature.values(), new FeatureChangeObservableMock() );
        featureToggleService.setFloatFeatures( TestFeature.FEATURE_FLOAT_SAMPLE.name(), FLOAT_VALUE);
        assertEquals( FLOAT_VALUE, featureToggleService.getFeatureFloatValue(TestFeature.FEATURE_FLOAT_SAMPLE), DELTA );
    }

    @Test
    public void setDoubleFeatures_wrongType() {
        try {
            FeatureToggle featureToggleService = new FeatureToggle( TestFeature.values(), new FeatureChangeObservableMock() );

            featureToggleService.setDoubleFeatures(TestFeature.FEATURE_BOOLEAN_SAMPLE.name(), DOUBLE_VALUE);
            fail("This method must throw exception");

        } catch ( RuntimeException e ) {
            assertEquals("Expected exception because the type must be the same", "Wrong type value the correct type is java.lang.Boolean", e.getMessage());
        }
    }

    @Test
    public void setDoubleFeatures_nonexistentDoubleFeature() {
        try {
            FeatureToggle featureToggleService = new FeatureToggle( TestFeature.values(), new FeatureChangeObservableMock() );
            featureToggleService.setDoubleFeatures("FEATURE_THAT_DOESNT_EXIST", DOUBLE_VALUE);
            fail("This method must throw exception");
        } catch ( RuntimeException e ) {
            assertEquals("Expected exception because the this feature does not exist", "Feature FEATURE_THAT_DOESNT_EXIST does not exist", e.getMessage());
        }
    }

    @Test
    public void setDoubleFeatures_success() {
        FeatureToggle featureToggleService = new FeatureToggle( TestFeature.values(), new FeatureChangeObservableMock() );
        featureToggleService.setDoubleFeatures( TestFeature.FEATURE_DOUBLE_SAMPLE.name(), DOUBLE_VALUE);
        assertEquals( DOUBLE_VALUE, featureToggleService.getFeatureDoubleValue(TestFeature.FEATURE_DOUBLE_SAMPLE), 0.1 );
    }

    @Test
    public void setFeature_usingGetClass_success() {
        FeatureToggle featureToggleService = new FeatureToggle( TestFeature.values(), new FeatureChangeObservableMock() );
        featureToggleService.setFeatureValue( TestFeature.FEATURE_INT_SAMPLE.name(), INT_VALUE, TestFeature.FEATURE_INT_SAMPLE.getValue().getClass() );
        assertEquals( "The feature value must have changed", INT_VALUE, featureToggleService.getFeatureIntegerValue(TestFeature.FEATURE_INT_SAMPLE) );
    }

    @Test
    public void setFeature_usingGetClass_fail() {
        try {
            FeatureToggle featureToggleService = new FeatureToggle( TestFeature.values(), new FeatureChangeObservableMock() );
            featureToggleService.setFeatureValue(TestFeature.FEATURE_INT_SAMPLE.name(), INT_VALUE, TestFeature.FEATURE_DOUBLE_SAMPLE.getValue().getClass());
            fail("This method must throw exception");
        } catch ( RuntimeException e ) {
            assertEquals("Expected exception because the type must be the same", "Wrong type value the correct type is java.lang.Integer", e.getMessage());
        }
    }

}
