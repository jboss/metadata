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
package org.jboss.metadata.ejb.test.common;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 * @author <a href="mailto:wprice@redhat.com">Weston M. Price</a>
 */
public class ValidationHelper {

    private static final Logger logger = Logger.getLogger(ValidationHelper.class.getName());

    public static Document parse(final InputSource is, final Class loader) throws ParserConfigurationException, IOException, SAXException {
        // parse an XML document into a DOM tree
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setAttribute(
                "http://java.sun.com/xml/jaxp/properties/schemaLanguage",
                "http://www.w3.org/2001/XMLSchema");
        factory.setNamespaceAware(true);
        factory.setValidating(true);
        DocumentBuilder parser = factory.newDocumentBuilder();
        ParserHelper helper = new ParserHelper();
        parser.setEntityResolver(helper);
        parser.setErrorHandler(helper);
        return parser.parse(is);
    }


    public static void compileCatalog(String pathRoot) throws Exception {

        SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        Schema schema = null;
        int count = 0;
        boolean compile = true;
        List<Schema> catalog = new ArrayList<>();
        try(DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(pathRoot), "jboss*.xsd")) {
            for(Path entry: stream) {
                System.out.println(entry);
                count++;
                if(compile) {
                    schema = factory.newSchema(entry.toFile());
                    catalog.add(schema);
                }

            } System.out.println("The final count is " + count);
        }
    }
    public static void main(String[] args) throws Exception {
        compileCatalog("/Users/wmprice/Desktop/schema");
    }
}
