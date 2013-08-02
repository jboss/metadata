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

package org.jboss.metadata.parser.jbossweb;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.logging.Logger;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.web.jboss.ReplicationConfig;
import org.jboss.metadata.web.jboss.ReplicationGranularity;

/**
 * @author Paul Ferraro
 */
public class ReplicationConfigParser extends MetaDataElementParser {
    private static final Logger log = Logger.getLogger(JBossWebMetaDataParser.class);
    private static final Set<String> DEPRECATED_ELEMENTS = new HashSet<>(Arrays.asList(
            "backups",
            "max-unreplicated-interval",
            "replication-mode",
            "replication-trigger",
            "snapshot-mode",
            "snapshot-interval",
            "session-notification-policy",
            "use-jk"
    ));

    public static ReplicationConfig parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        ReplicationConfig config = new ReplicationConfig();
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            String localName = reader.getLocalName();
            final Element element = Element.forName(localName);
            switch (element) {
                case CACHE_NAME: {
                    config.setCacheName(getElementText(reader, propertyReplacer));
                    break;
                }
                case REPLICATION_GRANULARITY: {
                    config.setReplicationGranularity(ReplicationGranularity.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                }
                default: {
                    if (DEPRECATED_ELEMENTS.contains(localName)) {
                        log.warnf("<%s/> is no longer supported and will be ignored", localName);
                        // Skip any content
                        reader.getElementText();
                    } else {
                        throw unexpectedElement(reader);
                    }
                }
            }
        }
        return config;
    }
}
