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
import static org.junit.Assert.assertNull;

import java.util.Properties;

import org.jboss.metadata.property.CompositePropertyResolver;
import org.jboss.metadata.property.PropertiesPropertyResolver;
import org.jboss.metadata.property.PropertyResolver;
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


        final PropertyResolver propertyResolver = SystemPropertyResolver.INSTANCE;

        assertNull(propertyResolver.resolve("test"));
        assertEquals("testValue1", propertyResolver.resolve("test1"));
        assertEquals("testValue2", propertyResolver.resolve("test2"));
        assertEquals("testValue3", propertyResolver.resolve("test3"));

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


        final PropertyResolver propertyResolver = new PropertiesPropertyResolver(properties);

        assertNull(propertyResolver.resolve("test"));
        assertEquals("testValue1", propertyResolver.resolve("test1"));
        assertEquals("testValue2", propertyResolver.resolve("test2"));
        assertEquals("testValue3", propertyResolver.resolve("test3"));
    }


    @Test
    public void testCompositePropertyResolver() throws Exception {
        final Properties properties = new Properties();
        properties.setProperty("test1", "testValue1");
        properties.setProperty("test2", "testValue2");
        properties.setProperty("test3", "testValue3");
        final PropertyResolver resolver1 = new PropertiesPropertyResolver(properties);

        System.setProperty("test4", "testValue4");
        System.setProperty("test5", "testValue5");
        System.setProperty("test6", "testValue6");

        final PropertyResolver propertyResolver = new CompositePropertyResolver(resolver1, SystemPropertyResolver.INSTANCE);

        assertNull(propertyResolver.resolve("test"));
        assertEquals("testValue1", propertyResolver.resolve("test1"));
        assertEquals("testValue2", propertyResolver.resolve("test2"));
        assertEquals("testValue3", propertyResolver.resolve("test3"));

        assertEquals("testValue4", propertyResolver.resolve("test4"));
        assertEquals("testValue5", propertyResolver.resolve("test5"));
        assertEquals("testValue6", propertyResolver.resolve("test6"));


        System.clearProperty("test4");
        System.clearProperty("test5");
        System.clearProperty("test6");
    }

    @Test
    public void testCustomResolver() throws Exception {
        final PropertyResolver propertyResolver = new PropertyResolver() {
            public String resolve(String propertyName) {
                return propertyName.toUpperCase();
            }
        };

        assertEquals("TEST1", propertyResolver.resolve("test1"));
        assertEquals("TEST2", propertyResolver.resolve("test2"));
        assertEquals("TEST3", propertyResolver.resolve("test3"));
    }
}
