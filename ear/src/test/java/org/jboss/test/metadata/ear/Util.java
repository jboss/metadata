package org.jboss.test.metadata.ear;

import org.junit.Assert;

/**
 * @author John Bailey
 */
public class Util {
     public static void assertEqualsIgnoreOrder(final Object[] expecteds, final Object[] actuals) {
        Assert.assertEquals(expecteds.length, actuals.length);
        for (Object actual : actuals) {
            boolean found = false;
            for (Object expected : expecteds) {
                if (expected.equals(actual)) {
                    found = true;
                    break;
                }
            }
            Assert.assertTrue(found);
        }
    }
}
