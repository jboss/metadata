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

package org.jboss.metadata.parser.jbossweb;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.web.jboss.ReplicationConfig;
import org.jboss.metadata.web.jboss.ReplicationGranularity;
import org.jboss.metadata.web.jboss.ReplicationMode;
import org.jboss.metadata.web.jboss.ReplicationTrigger;
import org.jboss.metadata.web.jboss.SnapshotMode;

/**
 * @author Paul Ferraro
 */
public class ReplicationConfigParser extends MetaDataElementParser {
    public static ReplicationConfig parse(XMLStreamReader reader) throws XMLStreamException {
        ReplicationConfig config = new ReplicationConfig();
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case CACHE_NAME: {
                    config.setCacheName(getElementText(reader));
                    break;
                }
                case REPLICATION_TRIGGER: {
                    config.setReplicationTrigger(ReplicationTrigger.valueOf(getElementText(reader)));
                    break;
                }
                case REPLICATION_GRANULARITY: {
                    config.setReplicationGranularity(ReplicationGranularity.valueOf(getElementText(reader)));
                    break;
                }
                case REPLICATION_MODE: {
                    config.setReplicationMode(ReplicationMode.valueOf(getElementText(reader)));
                    break;
                }
                case BACKUPS: {
                    config.setBackups(Integer.valueOf(getElementText(reader)));
                    break;
                }
                case USE_JK: {
                    config.setUseJK(Boolean.valueOf(getElementText(reader)));
                    break;
                }
                case MAX_UNREPLICATED_INTERVAL: {
                    config.setMaxUnreplicatedInterval(Integer.valueOf(getElementText(reader)));
                    break;
                }
                case SNAPSHOT_MODE: {
                    config.setSnapshotMode(SnapshotMode.valueOf(getElementText(reader)));
                    break;
                }
                case SNAPSHOT_INTERVAL: {
                    config.setSnapshotInterval(Integer.valueOf(getElementText(reader)));
                    break;
                }
                case SESSION_NOTIFICATION_POLICY: {
                    config.setSessionNotificationPolicy(getElementText(reader));
                    break;
                }
                default: {
                    throw unexpectedElement(reader);
                }
            }
        }
        return config;
    }
}
