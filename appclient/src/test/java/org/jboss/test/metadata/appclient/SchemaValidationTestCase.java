/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat, Inc., and individual contributors
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

package org.jboss.test.metadata.appclient;

import static org.junit.runners.Parameterized.Parameters;

import org.jboss.metadata.parser.util.XMLResourceResolver;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Validates the jboss-app_7_0.xsd
 *
 * @author Jaikiran Pai
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
@RunWith(Parameterized.class)
public class SchemaValidationTestCase extends AbstractXSDValidationTestCase {
    private final String xsd;

    @Parameters
    public static List<Object[]> parameters() {
    	
        return Arrays.asList(new Object[][] { { "schema/application-client_6.xsd" }, { "schema/jboss-client_6_0.xsd" } });
    }

    public SchemaValidationTestCase(final String xsd) {
        this.xsd = xsd;
    }

    private URL resource(final String name) {
        final ClassLoader classLoader = getClass().getClassLoader();
        final URL resource = classLoader.getResource(name);
        if (resource == null) {
            throw new IllegalArgumentException("Can't locate resource " + name + " on " + classLoader);
        }
        return resource;
    }

    /**
     * Test that the xsd is valid
     *
     * @throws Exception
     */
    @Test
    public void testXSD() throws Exception {
        final InputStream in = this.getClass().getClassLoader().getResourceAsStream(xsd);
        Assert.assertNotNull("Could not locate " + xsd + " using classloader " + this.getClass().getClassLoader(), in);
        this.validateXsd(in);
    }

    private void validateXsd(final InputStream xsd) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder parser = factory.newDocumentBuilder();
        Document document = parser.parse(xsd);

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        schemaFactory.setErrorHandler(new ErrorHandlerImpl());
        schemaFactory.setResourceResolver(new XMLResourceResolver());
        //Schema schema = schemaFactory.newSchema(new URL("http://www.w3.org/2001/XMLSchema.xsd"));
        // make sure we do not use any internet resources
        Schema schema = schemaFactory.newSchema(resource("schema/XMLSchema.xsd"));
        Validator validator = schema.newValidator();
        validator.validate(new DOMSource(document));
    }
}
