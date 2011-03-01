/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
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

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.EnvironmentRefsGroupMetaData;
import org.jboss.metadata.parser.ee.EnvironmentRefsGroupMetaDataParser;
import org.jboss.metadata.parser.servlet.SessionConfigMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.web.jboss.ContainerListenerMetaData;
import org.jboss.metadata.web.jboss.JBoss4xDTDWebMetaData;
import org.jboss.metadata.web.jboss.JBoss50DTDWebMetaData;
import org.jboss.metadata.web.jboss.JBoss50WebMetaData;
import org.jboss.metadata.web.jboss.JBoss60WebMetaData;
import org.jboss.metadata.web.jboss.JBossAnnotationsMetaData;
import org.jboss.metadata.web.jboss.JBossWebMetaData;
import org.jboss.metadata.web.jboss.ValveMetaData;


/**
 * @author Remy Maucherat
 */
public class JBossWebMetaDataParser extends MetaDataElementParser {

    public static JBossWebMetaData parse(XMLStreamReader reader) throws XMLStreamException {

        reader.require(START_DOCUMENT, null, null);
        // Read until the first start element
        Version version = null;
        while (reader.hasNext() && reader.next() != START_ELEMENT) {
            if (reader.getEventType() == DTD) {
                String dtdLocation = readDTDLocation(reader);
                if (dtdLocation != null) {
                    version = Location.getVersion(dtdLocation);
                }
                if (version == null) {
                    // DTD->getText() is incomplete and not parsable with Xerces from Sun JDK 6,
                    // so assume jboss-web 5.0
                    version = Version.JBOSS_WEB_5_0;
                }
            }
        }
        String schemaLocation = readSchemaLocation(reader);
        if (schemaLocation != null) {
            version = Location.getVersion(schemaLocation);
        }
        if (version == null) {
            version = Version.JBOSS_WEB_6_0;
        }
        JBossWebMetaData wmd = null;
        switch (version) {
            case JBOSS_WEB_3_0: wmd = new JBoss4xDTDWebMetaData(); break;
            case JBOSS_WEB_3_2: wmd = new JBoss4xDTDWebMetaData(); break;
            case JBOSS_WEB_4_0: wmd = new JBoss4xDTDWebMetaData(); break;
            case JBOSS_WEB_4_2: wmd = new JBoss4xDTDWebMetaData(); break;
            case JBOSS_WEB_5_0: wmd = new JBoss50DTDWebMetaData(); break;
            case JBOSS_WEB_5_1: wmd = new JBoss50WebMetaData(); break;
            case JBOSS_WEB_6_0: wmd = new JBoss60WebMetaData(); break;
        }

        // Handle attributes
        final int count = reader.getAttributeCount();
        for (int i = 0; i < count; i ++) {
            final String value = reader.getAttributeValue(i);
            if (reader.getAttributeNamespace(i) != null) {
                continue;
            }
            final Attribute attribute = Attribute.forName(reader.getAttributeLocalName(i));
            switch (attribute) {
                case VERSION: {
                    wmd.setVersion(value);
                    break;
                }
                default: throw unexpectedAttribute(reader, i);
            }
        }

        EnvironmentRefsGroupMetaData env = new EnvironmentRefsGroupMetaData();
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            if (EnvironmentRefsGroupMetaDataParser.parse(reader, env)) {
                if (wmd.getJndiEnvironmentRefsGroup() == null) {
                    wmd.setJndiEnvironmentRefsGroup(env);
                }
                continue;
            }
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case CONTEXT_ROOT:
                    wmd.setContextRoot(getElementText(reader));
                    break;
                case VIRTUAL_HOST:
                    // We only support one virtual host, at least for now
                    List<String> virtualHosts = wmd.getVirtualHosts();
                    if (virtualHosts == null) {
                        virtualHosts = new ArrayList<String>();
                        wmd.setVirtualHosts(virtualHosts);
                        virtualHosts.add(getElementText(reader));
                    } else {
                        throw duplicateNamedElement(reader, Element.VIRTUAL_HOST.toString());
                    }
                    break;
                case ANNOTATION:
                    JBossAnnotationsMetaData annotations = wmd.getAnnotations();
                    if (annotations == null) {
                        annotations = new JBossAnnotationsMetaData();
                        wmd.setAnnotations(annotations);
                    }
                    annotations.add(JBossAnnotationMetaDataParser.parse(reader));
                    break;
                case LISTENER:
                	List<ContainerListenerMetaData> listeners = wmd.getContainerListeners();
                	if (listeners == null) {
                		listeners = new ArrayList<ContainerListenerMetaData>();
                		wmd.setContainerListeners(listeners);
                	}
                	listeners.add(ContainerListenerMetaDataParser.parse(reader));
                	break;
                case SESSION_CONFIG:
                	wmd.setSessionConfig(SessionConfigMetaDataParser.parse(reader));
                	break;
                case VALVE:
                	List<ValveMetaData> valves = wmd.getValves();
                	if (valves == null) {
                		valves = new ArrayList<ValveMetaData>();
                		wmd.setValves(valves);
                	}
                	valves.add(ValveMetaDataParser.parse(reader));
                	break;
                case OVERLAY:
                	List<String> overlays = wmd.getOverlays();
                	if (overlays == null) {
                		overlays = new ArrayList<String>();
                		wmd.setOverlays(overlays);
                	}
                	overlays.add(getElementText(reader));
                	break;
                case SECURITY_DOMAIN:
                	wmd.setSecurityDomain(getElementText(reader));
                	break;
                case JACC_STAR_ROLE_ALLOW:
                	wmd.setJaccAllStoreRole(Boolean.valueOf(getElementText(reader)));
                	break;
                case CROSS_CONTEXT:
                	wmd.setCrossContext(Boolean.valueOf(getElementText(reader)));
                	break;
                default: throw unexpectedElement(reader);
            }
        }

        return wmd;
    }

}
