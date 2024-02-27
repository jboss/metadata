/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.metadata.common;

import static org.junit.runners.Parameterized.Parameters;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.jboss.metadata.parser.util.XMLResourceResolver;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.w3c.dom.Document;

/**
 * Validates the jboss-app_7_0.xsd
 *
 * @author Jaikiran Pai
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
@RunWith(Parameterized.class)
public class SchemaValidationTestCase extends AbstractXSDValidationTestCase {
    private final String xsd;

    protected static List<Object[]> getXSDFiles(String xsdFile) {
        List<Object[]> xsdList = new ArrayList<Object[]>();
        URL url = SchemaValidationTestCase.class.getClassLoader().getResource(xsdFile);
        if (url == null) {
            throw new RuntimeException("Can't load xsd file : " + xsdFile
                    + " in schema directory");
        }

        File filePath = new File(url.getPath());
        String parentPath = filePath.getParent();
        File directory = new File(parentPath);
        File[] files = directory.listFiles();
        String file;
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                file = files[i].getName();
                if (file.endsWith(".xsd") || file.endsWith(".XSD")) {
                    Object[] obj = new Object[1];
                    obj[0] = "schema".concat(System.getProperty("file.separator")).concat(file);
                    xsdList.add(obj);
                }
            }
        }
        return xsdList;

    }

    @Parameters
    public static List<Object[]> parameters() {
        // Each module, define one xsd file as getResource() parameter
        String xsdFile = "schema/jboss-common_6_0.xsd";
        return getXSDFiles(xsdFile);
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