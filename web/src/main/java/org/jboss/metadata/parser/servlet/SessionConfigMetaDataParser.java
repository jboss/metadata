/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.servlet;

import java.util.LinkedList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.parser.ee.IdMetaDataParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.web.spec.SessionConfigMetaData;
import org.jboss.metadata.web.spec.SessionTrackingModeType;

/**
 * @author Remy Maucherat
 */
public class SessionConfigMetaDataParser extends AbstractVersionedMetaDataParser<SessionConfigMetaData> {

    public SessionConfigMetaDataParser(Version version) {
        super(version);
    }

    @Override
    public SessionConfigMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        SessionConfigMetaData sessionConfig = new SessionConfigMetaData();

        IdMetaDataParser.parseAttributes(reader, sessionConfig);

        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case SESSION_TIMEOUT:
                    try {
                        sessionConfig.setSessionTimeout(Integer.valueOf(getElementText(reader, propertyReplacer)));
                    } catch (NumberFormatException e) {
                        throw unexpectedValue(reader, e);
                    }
                    break;
                case COOKIE_CONFIG:
                    sessionConfig.setCookieConfig(new CookieConfigMetaDataParser(this.getVersion()).parse(reader, propertyReplacer));
                    break;
                case TRACKING_MODE:
                    List<SessionTrackingModeType> trackingModes = sessionConfig.getSessionTrackingModes();
                    if (trackingModes == null) {
                        trackingModes = new LinkedList<>();
                        sessionConfig.setSessionTrackingModes(trackingModes);
                    }
                    try {
                        trackingModes.add(SessionTrackingModeType.valueOf(getElementText(reader, propertyReplacer)));
                    } catch (IllegalArgumentException e) {
                        throw unexpectedValue(reader, e);
                    }
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return sessionConfig;
    }
}
