package org.jboss.test.metadata.property;

import java.util.Properties;
import org.jboss.metadata.property.PropertiesPropertyResolver;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.property.PropertyReplacers;
import org.jboss.metadata.property.PropertyResolver;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 * @author John Bailey
 */
public class PropertyReplacerTest {

    final Properties properties = new Properties();
    final PropertyReplacer replacer = PropertyReplacers.resolvingReplacer(new PropertiesPropertyResolver(properties));

    @Before
    public void setupProperties() {
        properties.setProperty("test1", "testValue1");
        properties.setProperty("test2", "testValue2");
        properties.setProperty("test3", "testValue3");
    }

    @After

    @Test
    public void testNoProperties() throws Exception {
        final String initial = "Some string of stuff";
        final String after = replacer.replaceProperties(initial);
        assertEquals(initial, after);
    }

    @Test
    public void testSingle() throws Exception {
        final String initial = "Some ${test1} stuff";
        final String after = replacer.replaceProperties(initial);
        assertEquals("Some testValue1 stuff", after);
    }

    @Test
    public void testMultiple() throws Exception {
        final String initial = "${test1} ${test2} ${test3}";
        final String after = replacer.replaceProperties(initial);
        assertEquals("testValue1 testValue2 testValue3", after);
    }

    @Test
    public void testPropertyNotFound() throws Exception {
        final String initial = "Some ${test4} stuff";
        try {
            replacer.replaceProperties(initial);
            fail("Should have failed");
        } catch (IllegalStateException expected) {
        }
    }

    @Test
    public void testBadProperty() throws Exception {
        final String initial = "Some ${test2 stuff";
        try {
            replacer.replaceProperties(initial);
            fail("Should have failed");
        } catch (IllegalStateException expected) {
        }
    }

    @Test
    public void testCustomReplacer() {
        final PropertyReplacer replacer = new PropertyReplacer() {
            public String replaceProperties(String text) {
                return text.toUpperCase();
            }
        };
        final String initial = "Some string of stuff";
        final String after = replacer.replaceProperties(initial);
        assertEquals("SOME STRING OF STUFF", after);
    }

    /** Test for JBMETA-371 */
    @Test
    public void testFullExpressionReplacement() {
        final String vaultExpression =  "VAULT::1:2";
        final PropertyReplacer replacer = PropertyReplacers.resolvingReplacer(new PropertyResolver() {
            @Override
            public String resolve(String propertyName) {
                return vaultExpression.equals(propertyName) ? "true" : null;
            }
        });
        String after = replacer.replaceProperties("${" + vaultExpression + "}");
        assertEquals("true", after);

        final String nonVaultExpression = "RANDOM::1:2";
        after = replacer.replaceProperties("${" + nonVaultExpression + "}");
        assertEquals(":1:2", after);
    }
}
