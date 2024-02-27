/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.metadata.javaee;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLResolver;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import junit.framework.TestCase;
import org.jboss.metadata.parser.util.NoopXMLResolver;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.property.PropertyReplacers;
import org.jboss.metadata.property.SystemPropertyResolver;

/**
 * A JavaEE metadata Test.
 *
 * @author jfclere
 * @version $Revision: 1.2 $
 */
public class AbstractJavaEEMetaDataTest extends TestCase {
    protected final PropertyReplacer propertyReplacer = PropertyReplacers.resolvingExpressionReplacer(SystemPropertyResolver.INSTANCE);

    public AbstractJavaEEMetaDataTest() {
        super();
    }

    public AbstractJavaEEMetaDataTest(String name) {
        super(name);
    }

    /**
     * Open some xml
     *
     * @return the XMLStreamReader
     * @throws Exception for any error
     */
    protected XMLStreamReader getReader() throws Exception {
        return getReader(getXmlFileName(), NoopXMLResolver.create());
    }

    private String getXmlFileName() {
        String name = getClass().getSimpleName();
        int index = name.lastIndexOf("UnitTestCase");
        if (index != -1) {
            name = name.substring(0, index);
        }
        return name + "_" + getName() + ".xml";
    }
    /**
     * Open a xml file
     *
     * @param name the name of the file
     * @return the XMLStreamReader
     * @throws Exception for any error
     */
    @Deprecated
    protected XMLStreamReader getReader(String name) throws Exception {
        return getReader(name, NoopXMLResolver.create());
    }

    protected XMLStreamReader getReader(String name, XMLResolver resolver) throws IOException, XMLStreamException {
        final XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        inputFactory.setXMLResolver(resolver);
        return inputFactory.createXMLStreamReader(findXML(name));
    }

    /**
     * Find the xml
     *
     * @param name the name
     * @return the url of the xml
     * @throws IOException
     */
    protected InputStream findXML(String name) throws IOException {
        URL url = getResource(name);
        if (url == null) { fail(name + " not found"); }
        return url.openStream();
    }

    /**
     * Find the xml
     */
    protected InputStream findXML() throws IOException {
        return findXML(getXmlFileName());
    }

    public URL getResource(final String name) {
        return findResource(getClass(), name);
    }

    public static URL findResource(final Class<?> clazz, final String name) {
        return clazz.getResource(name);
    }
}
