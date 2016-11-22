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
package org.jboss.test.metadata.common.vdx;

import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by wmprice on 6/2/15.
 */
public class ParserHelper implements EntityResolver, ErrorHandler {

    private static final Logger logger = Logger.getLogger(ParserHelper.class.getName());
    private static final String DEFAULT_MAP_ID = "/org/jboss/metadata/ejb/test/common/system-map.json";
    private static final String[] MAP_SOURCES = {DEFAULT_MAP_ID};
    private static final Map<String, String> SYSTEM_MAP = new HashMap<>();

    static {
        for(String source: MAP_SOURCES) {
            loadSystemMap(source);
        }
    }

    private static void loadSystemMap(final String sourceName) {

        String ext = getSourceExtension(sourceName);
        Map<String, String> candidate = null;

        try(InputStream is = ValidationHelper.class.getResourceAsStream(sourceName)){

            if(ext.equalsIgnoreCase("JSON")) {
                candidate = new ValidationMapLoader.JsonMapLoader().loadMapSource(is);
            } else if(ext.equalsIgnoreCase("PROPERTIES")) {
                candidate = new ValidationMapLoader.PropertiesFileLoader().loadMapSource(is);
            }
            else {
                candidate = new ValidationMapLoader.NullMapLoader().loadMapSource(is);
            }

            SYSTEM_MAP.putAll(candidate);

            SYSTEM_MAP.put("file://" + new File(System.getProperty("user.dir")).toURI().getPath() + "cache-test.xsd", "/org/jboss/metadata/ejb/test/extension/cache-test.xsd");
            SYSTEM_MAP.put("file://" + new File(System.getProperty("user.dir")).toURI().getPath() + "tx-test.xsd", "/org/jboss/metadata/ejb/test/extension/tx-test.xsd");

        } catch(IOException ioex) {
            logger.severe("Error loading system map for source " + sourceName);
            throw new RuntimeException("Could not load system map");
        }

    }
    @Override public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        final String resource = SYSTEM_MAP.get(systemId);
        if (resource == null) {
            throw new SAXException("Can't resolve systemId " + systemId);
        }
        return inputSource(getClass(), systemId, resource);
    }

    @Override public void warning(SAXParseException exception) throws SAXException {
        logger.warning(exception.getMessage());
        throw exception;
    }

    @Override public void error(SAXParseException exception) throws SAXException {
        logger.severe(exception.getMessage());
        throw exception;
    }

    @Override public void fatalError(SAXParseException exception) throws SAXException {
        logger.severe(exception.getMessage());
        throw exception;
    }

    private static InputSource inputSource(final Class loader, final String systemId, final String resource) {
        final InputStream in = loader.getResourceAsStream(resource);

        if (in == null) {
            throw new IllegalArgumentException("Can't find resource " + resource);
        }

        final InputSource inputSource = new InputSource(in);
        inputSource.setSystemId(systemId);
        return inputSource;
    }

    private static String getSourceExtension(final String sourceName) {
        return (sourceName.contains(".")) ? sourceName.substring(sourceName.lastIndexOf(".") + 1,  sourceName.length()) : "";
    }
    public static void addToSysteMap(final String publicId, final String systemId) {
        logger.info("Adding " + publicId + "  " + systemId + " to system map");
        addToSystemMap(publicId, systemId, false);
    }
    public static void addToSystemMap(final String publicId, final String systemId, final boolean replace) {
        if(isInSystemMap(publicId) && replace) {
            SYSTEM_MAP.put(publicId, systemId);
        } else {
            logger.info("Schema already exists in map and replacement not specified");
        }
    }
    public static boolean isInSystemMap(final String publicId) {
        return SYSTEM_MAP.containsKey(publicId);
    }


}
