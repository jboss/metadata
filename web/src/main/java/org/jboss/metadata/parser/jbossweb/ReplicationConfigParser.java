/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
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
@Deprecated
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
