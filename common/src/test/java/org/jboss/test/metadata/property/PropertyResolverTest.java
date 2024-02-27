/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.metadata.property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Properties;

import org.jboss.metadata.property.CompositePropertyResolver;
import org.jboss.metadata.property.PropertiesPropertyResolver;
import org.jboss.metadata.property.SimpleExpressionResolver;
import org.jboss.metadata.property.SystemPropertyResolver;
import org.junit.Test;

/**
 * @author John Bailey
 */
public class PropertyResolverTest {
    @Test
    public void testSystemPropertyResolver() throws Exception {
        System.setProperty("test1", "testValue1");
        System.setProperty("test2", "testValue2");
        System.setProperty("test3", "testValue3");


        final SimpleExpressionResolver propertyResolver = SystemPropertyResolver.INSTANCE;

        assertNull(propertyResolver.resolveExpressionContent("test"));
        assertEquals("testValue1", propertyResolver.resolveExpressionContent("test1").getValue());
        assertEquals("testValue2", propertyResolver.resolveExpressionContent("test2").getValue());
        assertEquals("testValue3", propertyResolver.resolveExpressionContent("test3").getValue());

        System.clearProperty("test1");
        System.clearProperty("test2");
        System.clearProperty("test3");
    }

    @Test
    public void testPropertiesPropertyResolver() throws Exception {
        final Properties properties = new Properties();
        properties.setProperty("test1", "testValue1");
        properties.setProperty("test2", "testValue2");
        properties.setProperty("test3", "testValue3");


        final SimpleExpressionResolver propertyResolver = new PropertiesPropertyResolver(properties);

        assertNull(propertyResolver.resolveExpressionContent("test"));
        assertEquals("testValue1", propertyResolver.resolveExpressionContent("test1").getValue());
        assertEquals("testValue2", propertyResolver.resolveExpressionContent("test2").getValue());
        assertEquals("testValue3", propertyResolver.resolveExpressionContent("test3").getValue());
    }


    @Test
    public void testCompositePropertyResolver() throws Exception {
        final Properties properties = new Properties();
        properties.setProperty("test1", "testValue1");
        properties.setProperty("test2", "testValue2");
        properties.setProperty("test3", "testValue3");
        final SimpleExpressionResolver resolver1 = new PropertiesPropertyResolver(properties);

        System.setProperty("test4", "testValue4");
        System.setProperty("test5", "testValue5");
        System.setProperty("test6", "testValue6");

        final SimpleExpressionResolver propertyResolver = new CompositePropertyResolver(resolver1, SystemPropertyResolver.INSTANCE);

        assertNull(propertyResolver.resolveExpressionContent("test"));
        assertEquals("testValue1", propertyResolver.resolveExpressionContent("test1").getValue());
        assertEquals("testValue2", propertyResolver.resolveExpressionContent("test2").getValue());
        assertEquals("testValue3", propertyResolver.resolveExpressionContent("test3").getValue());

        assertEquals("testValue4", propertyResolver.resolveExpressionContent("test4").getValue());
        assertEquals("testValue5", propertyResolver.resolveExpressionContent("test5").getValue());
        assertEquals("testValue6", propertyResolver.resolveExpressionContent("test6").getValue());


        System.clearProperty("test4");
        System.clearProperty("test5");
        System.clearProperty("test6");
    }

    @Test
    public void testCustomResolver() throws Exception {
        final SimpleExpressionResolver propertyResolver = (String propertyName) -> new SimpleExpressionResolver.ResolutionResult(propertyName.toUpperCase(), false);

        assertEquals("TEST1", propertyResolver.resolveExpressionContent("test1").getValue());
        assertEquals("TEST2", propertyResolver.resolveExpressionContent("test2").getValue());
        assertEquals("TEST3", propertyResolver.resolveExpressionContent("test3").getValue());
    }
}
