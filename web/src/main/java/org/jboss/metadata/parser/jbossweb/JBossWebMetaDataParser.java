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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.logging.Logger;
import org.jboss.metadata.javaee.spec.EnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;
import org.jboss.metadata.parser.ee.EnvironmentRefsGroupMetaDataParser;
import org.jboss.metadata.parser.ee.SecurityRoleMetaDataParser;
import org.jboss.metadata.parser.servlet.SessionConfigMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.web.jboss.ContainerListenerMetaData;
import org.jboss.metadata.web.jboss.HttpHandlerMetaData;
import org.jboss.metadata.web.jboss.JBossAnnotationsMetaData;
import org.jboss.metadata.web.jboss.JBossServletsMetaData;
import org.jboss.metadata.web.jboss.JBossWebMetaData;
import org.jboss.metadata.web.jboss.ValveMetaData;

/**
 * @author Remy Maucherat
 */
public class JBossWebMetaDataParser extends MetaDataElementParser {
    private static final Logger log = Logger.getLogger(JBossWebMetaDataParser.class);
    private static final Set<String> DEPRECATED_ELEMENTS = new HashSet<>(Arrays.asList(
            "passivation-config"
    ));

    public static JBossWebMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {

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
        JBossWebMetaData wmd = new JBossWebMetaData();

        // Handle attributes
        final int count = reader.getAttributeCount();
        for (int i = 0; i < count; i++) {
            final String value = reader.getAttributeValue(i);
            if (attributeHasNamespace(reader, i)) {
                continue;
            }
            final Attribute attribute = Attribute.forName(reader.getAttributeLocalName(i));
            switch (attribute) {
                case VERSION: {
                    wmd.setVersion(value);
                    break;
                }
                default:
                    throw unexpectedAttribute(reader, i);
            }
        }

        EnvironmentRefsGroupMetaData env = new EnvironmentRefsGroupMetaData();
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            if (EnvironmentRefsGroupMetaDataParser.parse(reader, env, propertyReplacer)) {
                if (wmd.getJndiEnvironmentRefsGroup() == null) {
                    wmd.setJndiEnvironmentRefsGroup(env);
                }
                continue;
            }
            String localName = reader.getLocalName();
            final Element element = Element.forName(localName);
            switch (element) {
                case CONTEXT_ROOT:
                    wmd.setContextRoot(getElementText(reader, propertyReplacer));
                    break;
                case VIRTUAL_HOST:
                    // We only support one virtual host, at least for now
                    List<String> virtualHosts = wmd.getVirtualHosts();
                    if (virtualHosts == null) {
                        virtualHosts = new ArrayList<String>();
                        wmd.setVirtualHosts(virtualHosts);
                        virtualHosts.add(getElementText(reader, propertyReplacer));
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
                    annotations.add(JBossAnnotationMetaDataParser.parse(reader, propertyReplacer));
                    break;
                case LISTENER:
                    List<ContainerListenerMetaData> listeners = wmd.getContainerListeners();
                    if (listeners == null) {
                        listeners = new ArrayList<ContainerListenerMetaData>();
                        wmd.setContainerListeners(listeners);
                    }
                    listeners.add(ContainerListenerMetaDataParser.parse(reader, propertyReplacer));
                    break;
                case SESSION_CONFIG:
                    wmd.setSessionConfig(SessionConfigMetaDataParser.parse(reader, propertyReplacer));
                    break;
                case VALVE:
                    List<ValveMetaData> valves = wmd.getValves();
                    if (valves == null) {
                        valves = new ArrayList<ValveMetaData>();
                        wmd.setValves(valves);
                    }
                    valves.add(ValveMetaDataParser.parse(reader, propertyReplacer));
                    break;
                case HTTP_HANDLER:
                    List<HttpHandlerMetaData> handlers = wmd.getHandlers();
                    if (handlers == null) {
                        handlers = new ArrayList<HttpHandlerMetaData>();
                        wmd.setHandlers(handlers);
                    }
                    handlers.add(HttpHandlerMetaDataParser.parse(reader, propertyReplacer));
                    break;
                case OVERLAY:
                    List<String> overlays = wmd.getOverlays();
                    if (overlays == null) {
                        overlays = new ArrayList<String>();
                        wmd.setOverlays(overlays);
                    }
                    overlays.add(getElementText(reader, propertyReplacer));
                    break;
                case SECURITY_DOMAIN:
                    // Handle security domain attributes
                    final int k = reader.getAttributeCount();
                    for (int i = 0; i < k; i++) {
                        final String value = reader.getAttributeValue(i);
                        if (attributeHasNamespace(reader, i)) {
                            continue;
                        }
                        final org.jboss.metadata.parser.jbossweb.Attribute attribute = org.jboss.metadata.parser.jbossweb.Attribute.forName(reader.getAttributeLocalName(i));
                        switch (attribute) {
                            case FLUSH_ON_SESSION_INVALIDATION: {
                                wmd.setFlushOnSessionInvalidation(Boolean.valueOf(value));
                                break;
                            }
                            default:
                                throw unexpectedAttribute(reader, i);
                        }
                    }
                    // Handle security domain name
                    wmd.setSecurityDomain(getElementText(reader, propertyReplacer));
                    break;
                case SECURITY_ROLE:
                    SecurityRolesMetaData securityRoles = wmd.getSecurityRoles();
                    if (securityRoles == null) {
                        securityRoles = new SecurityRolesMetaData();
                        wmd.setSecurityRoles(securityRoles);
                    }
                    securityRoles.add(SecurityRoleMetaDataParser.parse(reader, propertyReplacer));
                    break;
                case JACC_STAR_ROLE_ALLOW:
                    wmd.setJaccAllStoreRole(Boolean.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                case JACC_CONTEXT_ID:
                    wmd.setJaccContextID(getElementText(reader, propertyReplacer));
                    break;
                case DISABLE_CROSS_CONTEXT:
                    wmd.setDisableCrossContext(Boolean.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                case ENABLE_WEBSOCKETS:
                    wmd.setEnableWebSockets(Boolean.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                case USE_JBOSS_AUTHORIZATION:
                    wmd.setUseJBossAuthorization(Boolean.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                case DISABLE_AUDIT:
                    wmd.setDisableAudit(Boolean.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                case SERVLET:
                    JBossServletsMetaData servlets = wmd.getServlets();
                    if (servlets == null) {
                        servlets = new JBossServletsMetaData();
                        wmd.setServlets(servlets);
                    }
                    servlets.add(JBossServletMetaDataParser.parse(reader, propertyReplacer));
                    break;
                case MAX_ACTIVE_SESSIONS:
                    wmd.setMaxActiveSessions(Integer.valueOf(getElementText(reader, propertyReplacer)));
                    break;
                case REPLICATION_CONFIG:
                    if (version.compareTo(Version.JBOSS_WEB_13_0) >= 0) {
                        throw unexpectedElement(reader);
                    }
                    wmd.setReplicationConfig(ReplicationConfigParser.parse(reader, propertyReplacer));
                    break;
                case DISTINCT_NAME:
                    final String val = getElementText(reader, propertyReplacer);
                    wmd.setDistinctName(val);
                    break;
                case SYMBOLIC_ENABLED:
                    wmd.setSymbolicLinkingEnabled(Boolean.parseBoolean(getElementText(reader, propertyReplacer)));
                    break;
                case SERVLET_CONTAINER:
                    wmd.setServletContainerName(getElementText(reader, propertyReplacer));
                    break;
                case DEFAULT_ENCODING:
                    wmd.setDefaultEncoding(getElementText(reader, propertyReplacer));
                    break;
                case SERVER_INSTANCE:
                    wmd.setServerInstanceName(getElementText(reader, propertyReplacer));
                    break;
                case DENY_UNCOVERED_HTTP_METHODS:
                    wmd.setDenyUncoveredHttpMethods(Boolean.parseBoolean(getElementText(reader, propertyReplacer)));
                    break;
                case EXECUTOR_NAME:
                    wmd.setExecutorName(getElementText(reader, propertyReplacer));
                    break;
                case PROACTIVE_AUTHENTICATION:
                    wmd.setProactiveAuthentication(Boolean.parseBoolean(getElementText(reader, propertyReplacer)));
                    break;
                default: {
                    if (DEPRECATED_ELEMENTS.contains(localName)) {
                        log.warnf("<%s/> is no longer supported and will be ignored", localName);
                        // Skip any nested content
                        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
                            reader.getElementText();
                        }
                    } else {
                        throw unexpectedElement(reader);
                    }
                }
            }
        }

        return wmd;
    }

}
