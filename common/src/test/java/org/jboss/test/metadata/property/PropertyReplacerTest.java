/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.test.metadata.property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Properties;

import org.jboss.metadata.property.CompositePropertyResolver;
import org.jboss.metadata.property.PropertiesPropertyResolver;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.property.PropertyReplacers;
import org.jboss.metadata.property.PropertyResolver;
import org.jboss.metadata.property.SimpleExpressionResolver;
import org.junit.After;
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
            // ok
        }
    }

    @Test
    public void testBadProperty() throws Exception {
        final String initial = "Some ${test2 stuff";
        try {
            replacer.replaceProperties(initial);
            fail("Should have failed");
        } catch (IllegalStateException expected) {
            // ok
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
    public void testFullExpressionReplacementWithCompositeResolver() {
        final String vaultExpression =  "VAULT::1:2";
        final PropertyReplacer replacer = PropertyReplacers.resolvingReplacer(new CompositePropertyResolver(
                new SimpleExpressionResolver() {
                    @Override
                    public ResolutionResult resolveExpressionContent(String expressionContent) {
                        return vaultExpression.equals(expressionContent) ? new ResolutionResult("true", false) : null;
                    }
                },
                new PropertiesPropertyResolver(properties)
        ));
        String after = replacer.replaceProperties("${" + vaultExpression + "}");
        assertEquals("true", after);

        final String nonVaultExpression = "RANDOM::1:2";
        after = replacer.replaceProperties("${" + nonVaultExpression + "}");
        assertEquals(":1:2", after);
    }

    @Test
    public void testFullExpressionReplacementWithCompositeLegacyResolver() {
        final String vaultExpression =  "VAULT::1:2";
        final PropertyReplacer replacer = PropertyReplacers.resolvingReplacer(new CompositePropertyResolver(
                new PropertyResolver() {
                    @Override
                    public String resolve(String propertyName) {
                        return vaultExpression.equals(propertyName) ? "true" : null;
                    }
                },
                new PropertiesPropertyResolver(properties)
        ));
        String after = replacer.replaceProperties("${" + vaultExpression + "}");
        assertEquals("true", after);

        final String nonVaultExpression = "RANDOM::1:2";
        after = replacer.replaceProperties("${" + nonVaultExpression + "}");
        assertEquals(":1:2", after);
    }

    /** Test for JBMETA-371 */
    @Test
    public void testFullExpressionReplacementWithoutDefaultResolver() {
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

    @Test
    public void testNestedExpressions() {
        properties.setProperty("foo", "FOO");
        properties.setProperty("bar", "BAR");
        properties.setProperty("baz", "BAZ");
        properties.setProperty("FOO", "oof");
        properties.setProperty("BAR", "rab");
        properties.setProperty("BAZ", "zab");
        properties.setProperty("foo.BAZ.BAR", "FOO.baz.bar");
        properties.setProperty("foo.BAZBAR", "FOO.bazbar");
        properties.setProperty("bazBAR", "BAZbar");
        properties.setProperty("fooBAZbar", "FOObazBAR");
        try {
            assertEquals("FOO", replacer.replaceProperties("${foo:${bar}}"));

            assertEquals("rab", replacer.replaceProperties("${${bar}}"));

            assertEquals("FOO.baz.bar", replacer.replaceProperties("${foo.${baz}.${bar}}"));

            assertEquals("FOO.bazbar", replacer.replaceProperties("${foo.${baz}${bar}}"));

            assertEquals("aFOObazBARb", replacer.replaceProperties("a${foo${baz${bar}}}b"));

            assertEquals("aFOObazBAR", replacer.replaceProperties("a${foo${baz${bar}}}"));

            assertEquals("FOObazBARb", replacer.replaceProperties("${foo${baz${bar}}}b"));

            assertEquals("aFOO.b.BARc", replacer.replaceProperties("a${foo}.b.${bar}c"));

            properties.remove("foo");

            assertEquals("BAR", replacer.replaceProperties("${foo:${bar}}"));

            assertEquals("BAR.{}.$$", replacer.replaceProperties("${foo:${bar}.{}.$$}"));

            assertEquals("$BAR", replacer.replaceProperties("$$${bar}"));

        } finally {
            properties.remove("foo");
            properties.remove("bar");
            properties.remove("baz");
            properties.remove("FOO");
            properties.remove("BAR");
            properties.remove("BAZ");
            properties.remove("foo.BAZ.BAR");
            properties.remove("foo.BAZBAR");
            properties.remove("bazBAR");
            properties.remove("fooBAZbar");
        }
    }

    @Test
    public void testDollarEscaping() {
        properties.setProperty("$$", "FOO");
        try {
            assertEquals("$", replacer.replaceProperties("$$"));

            assertEquals("$$", replacer.replaceProperties("$$$"));

            assertEquals("$$", replacer.replaceProperties("$$$$"));

            assertEquals("$$", replacer.replaceProperties("${$$$$:$$}"));

            assertEquals("FOO", replacer.replaceProperties("${$$:$$}"));

            assertEquals("${bar}", replacer.replaceProperties("${foo:$${bar}}"));

            assertEquals("${bar}", replacer.replaceProperties("$${bar}"));
        } finally {
            properties.remove("$$");
        }
    }

    @Test
    public void testFileSeparator() {
        assertEquals(File.separator, replacer.replaceProperties("${/}"));
        assertEquals(File.separator + "a", replacer.replaceProperties("${/}a"));
        assertEquals("a" + File.separator, replacer.replaceProperties("a${/}"));
    }

    @Test
    public void testPathSeparator() {
        assertEquals(File.pathSeparator, replacer.replaceProperties("${:}"));
        assertEquals(File.pathSeparator + "a", replacer.replaceProperties("${:}a"));
        assertEquals("a" + File.pathSeparator, replacer.replaceProperties("a${:}"));
    }

    @Test
    public void testBlankExpression() {
        final String initial = "";
        final String after = replacer.replaceProperties(initial);
        assertEquals("", after);
    }

    /**
     * Test that a incomplete expression to a system property reference throws an ISE
     */
    @Test(expected = IllegalStateException.class)
    public void testIncompleteReference() {
        String resolved = replacer.replaceProperties("${test1");
        fail("Did not fail with ISE: " + resolved);
    }

    /**
     * Test that an incomplete expression is ignored if escaped
     */
    @Test
    public void testEscapedIncompleteReference() {
        assertEquals("${test1", replacer.replaceProperties("$${test1"));
    }

    /**
     * Test that a incomplete expression to a system property reference throws an ISE
     */
    @Test(expected = IllegalStateException.class)
    public void testIncompleteReferenceFollowingSuccessfulResolve() {
        String resolved = replacer.replaceProperties("${test1} ${test1");
        fail("Did not fail with OFE: " + resolved);
    }

    @Test
    public void testDefaultExpressionResolverWithRecursiveSystemPropertyResolutions() {
        // recursive example
        properties.setProperty("test.prop.prop", "${test.prop.prop.intermediate}");
        properties.setProperty("test.prop.prop.intermediate", "PROP");

        // recursive example with a property expression as the default
        properties.setProperty("test.prop.expr", "${NOTHERE:${ISHERE}}");
        properties.setProperty("ISHERE", "EXPR");

        //PROP
        try {
            assertEquals("PROP", replacer.replaceProperties("${test.prop.prop}"));
            assertEquals("EXPR", replacer.replaceProperties("${test.prop.expr}"));
        } finally {
            System.clearProperty("test.prop.prop");
            System.clearProperty("test.prop.prop.intermediate");
            System.clearProperty("test.prop.expr");
            System.clearProperty("ISHERE");
        }
    }

    @Test
    public void testCustomExpressionResolverRecursive() {
        final PropertyReplacer replacer = PropertyReplacers.resolvingExpressionReplacer(new CompositePropertyResolver(
                new SimpleExpressionResolver() {
                    @Override
                    public ResolutionResult resolveExpressionContent(String expressionContent) {

                        if (expressionContent.equals("test.prop.expr")) {
                            return new ResolutionResult("${test.prop.expr.inner}", false);
                        } else if (expressionContent.equals("test.prop.expr.inner")) {
                            return new ResolutionResult("${test1}", false);
                        }
                        return null;
                    }
                }, new PropertiesPropertyResolver(properties)));
        assertEquals("testValue1", replacer.replaceProperties("${test.prop.expr}"));
    }
}
