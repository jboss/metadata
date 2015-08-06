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

package org.jboss.metadata.ejb.parser.jboss.ejb3;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.jboss.IIOPMetaData;
import org.jboss.metadata.ejb.jboss.IORASContextMetaData;
import org.jboss.metadata.ejb.jboss.IORSASContextMetaData;
import org.jboss.metadata.ejb.jboss.IORSecurityConfigMetaData;
import org.jboss.metadata.ejb.jboss.IORTransportConfigMetaData;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * <p>
 * This class implements a parser for the IIOP settings section of the jboss-ejb3.xml file.
 * </p>
 *
 * @author <a href="mailto:sguilhen@redhat.com">Stefan Guilhen</a>
 * @author <a href="mailto:wprice@redhat.com">Weston M. Price</a>
 */
public class IIOPMetaDataParser extends AbstractEJBBoundMetaDataParser<IIOPMetaData> {

    @Override
    public IIOPMetaData parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        IIOPMetaData metaData = new IIOPMetaData();
        super.processElements(metaData, reader, propertyReplacer);
        return metaData;
    }

    @Override
    protected void processElement(IIOPMetaData metaData, XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {

        Namespace namespace = Namespace.forUri(reader.getNamespaceURI());
        switch (namespace) {
            case EJB3_IIOP: {
                Element element = Element.forName(reader.getLocalName());
                switch (element) {
                    case BINDING_NAME: {
                        metaData.setBindingName(getElementText(reader, propertyReplacer));
                        break;
                    }
                    case IOR_SECURITY_CONFIG: {
                        IORSecurityConfigMetaData iorSecurityMetaData = this.processIORSecurityConfig(reader, propertyReplacer);
                        metaData.setIorSecurityConfigMetaData(iorSecurityMetaData);
                        break;
                    }
                }
                return;
            }
        }
        super.processElement(metaData, reader, propertyReplacer);
    }

    protected IORSecurityConfigMetaData processIORSecurityConfig(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        IORSecurityConfigMetaData iorSecurityMetaData = new IORSecurityConfigMetaData();

        boolean inStart = false;
        int tag = 0;

        while (reader.hasNext() ) {
            tag = reader.nextTag();

            if(tag == START_ELEMENT) {
                inStart = true;
                Element element = Element.forName(reader.getLocalName());
                switch (element) {
                    case TRANSPORT_CONFIG: {
                        IORTransportConfigMetaData transportConfig = this.processTransportConfig(reader, propertyReplacer);
                        iorSecurityMetaData.setTransportConfig(transportConfig);
                        break;
                    }
                    case AS_CONTEXT: {
                        IORASContextMetaData asContext = this.processASContext(reader, propertyReplacer);
                        iorSecurityMetaData.setAsContext(asContext);
                        break;
                    }
                    case SAS_CONTEXT: {
                        IORSASContextMetaData sasContext = this.processSASContext(reader, propertyReplacer);
                        iorSecurityMetaData.setSasContext(sasContext);
                        break;
                    }
                    default: {
                        throw unexpectedElement(reader);
                    }
                }

            } else if(tag == END_ELEMENT) {
                if(inStart)
                    inStart = false;
                else
                    break;
            }

        }
        return iorSecurityMetaData;
    }

    protected IORTransportConfigMetaData processTransportConfig(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {

        EnumSet<Attribute> requiredAttributes = EnumSet.of(Attribute.INTEGRITY, Attribute.CONFIDENTIALITY,
                Attribute.ESTABLISH_TRUST_IN_CLIENT, Attribute.ESTABLISH_TRUST_IN_TARGET);

        IORTransportConfigMetaData transportConfig = new IORTransportConfigMetaData();

        int count = reader.getAttributeCount();

        for(int i = 0; i < count; i++) {

            Attribute attribute = Attribute.forName(reader.getAttributeLocalName(i));
            String attValue = reader.getAttributeValue(i);

            switch (attribute) {

                case INTEGRITY: {
                    transportConfig.setIntegrity(attValue);
                    break;
                }
                case CONFIDENTIALITY: {
                    transportConfig.setConfidentiality(attValue);
                    break;
                }
                case DETECT_MISORDERING: {
                    transportConfig.setDetectMisordering(attValue);
                    break;
                }
                case DETECT_REPLAY: {
                    transportConfig.setDetectReplay(attValue);
                    break;
                }
                case ESTABLISH_TRUST_IN_CLIENT: {
                    transportConfig.setEstablishTrustInClient(attValue);
                    break;
                }
                case ESTABLISH_TRUST_IN_TARGET: {
                    transportConfig.setEstablishTrustInTarget(attValue);
                    break;
                }
                default: {
                    unexpectedAttribute(reader, i);
                }
            }
            requiredAttributes.remove(attribute);
        }

        // throw an exception if a required attribute wasn't found.
        if (!requiredAttributes.isEmpty()) {
            throw missingRequiredAttributes(reader, requiredAttributes);
        }

        return transportConfig;
    }

    protected IORASContextMetaData processASContext(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {

        EnumSet<Attribute> requiredAttributes = EnumSet.of(Attribute.AUTH_METHOD, Attribute.REALM, Attribute.REQUIRED);

        IORASContextMetaData asContext = new IORASContextMetaData();

        int count = reader.getAttributeCount();

        for(int i = 0; i < count; i++) {

            Attribute attribute = Attribute.forName(reader.getAttributeLocalName(i));
            String attValue = reader.getAttributeValue(i);

            switch (attribute) {

                case AUTH_METHOD: {
                    asContext.setAuthMethod(attValue);
                    break;
                }
                case REALM: {
                    asContext.setRealm(attValue);
                    break;
                }
                case REQUIRED: {
                    asContext.setRequired(Boolean.valueOf(attValue));
                    break;
                }
                default: {
                    unexpectedAttribute(reader, i);
                }

            }
            requiredAttributes.remove(attribute);
        }

        // throw an exception if a required attribute wasn't found.
        if (!requiredAttributes.isEmpty()) {
            throw missingRequiredAttributes(reader, requiredAttributes);
        }
        return asContext;

    }

    protected IORSASContextMetaData processSASContext(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {

        EnumSet<Attribute> requiredAttributes = EnumSet.of(Attribute.CALLER_PROPAGATION);

        IORSASContextMetaData sasContext = new IORSASContextMetaData();

        int count = reader.getAttributeCount();

        for(int i = 0; i < count; i++) {

            Attribute attribute = Attribute.forName(reader.getAttributeLocalName(i));
            String attValue = reader.getAttributeValue(i);

            switch (attribute) {

                case CALLER_PROPAGATION: {
                    sasContext.setCallerPropagation(attValue);
                    break;
                }
                default: {
                    unexpectedAttribute(reader, i);
                }

            }
            requiredAttributes.remove(attribute);
        }

        // throw an exception if a required attribute wasn't found.
        if (!requiredAttributes.isEmpty()) {
            throw missingRequiredElement(reader, requiredAttributes);
        }

        return sasContext;

    }

    /**
     * <p>
     * Enumeration of EJB3/IIOP configuration namespaces.
     * </p>
     *
     * @author <a href="mailto:sguilhen@redhat.com">Stefan Guilhen</a>
     */
    enum Namespace {

        UNKNOWN(null),
        EJB3_IIOP("urn:iiop");

        private final String namespaceURI;

        /**
         * <p>
         * {@code Namespace} constructor. Sets the namespace {@code URI}.
         * </p>
         *
         * @param namespaceURI a {@code String} representing the namespace {@code URI}.
         */
        private Namespace(final String namespaceURI) {
            this.namespaceURI = namespaceURI;
        }

        /**
         * <p>
         * Obtains the {@code URI} of this namespace.
         * </p>
         *
         * @return a {@code String} representing the namespace {@code URI}.
         */
        String getUriString() {
            return namespaceURI;
        }

        // a map that caches all available namespaces by URI.
        private static final Map<String, Namespace> MAP;

        static {
            final Map<String, Namespace> map = new HashMap<String, Namespace>();
            for (final Namespace namespace : values()) {
                final String name = namespace.getUriString();
                if (name != null)
                    map.put(name, namespace);
            }
            MAP = map;
        }


        /**
         * <p>
         * Gets the {@code Namespace} identified by the specified {@code URI}.
         * </p>
         *
         * @param uri a {@code String} representing the namespace {@code URI}.
         * @return the {@code Namespace} identified by the {@code URI}. If no namespace can be found, the
         *         {@code Namespace.UNKNOWN} type is returned.
         */
        static Namespace forUri(final String uri) {
            final Namespace element = MAP.get(uri);
            return element == null ? UNKNOWN : element;
        }
    }

    enum Attribute {

        UNKNOWN(null),
        //transport attributes
        INTEGRITY("integrity"),
        CONFIDENTIALITY("confidentiality"),
        DETECT_MISORDERING("detect-misordering"),
        DETECT_REPLAY("detect-replay"),
        ESTABLISH_TRUST_IN_CLIENT("establish-trust-in-client"),
        ESTABLISH_TRUST_IN_TARGET("establish-trust-in-target"),
        // authentication context configuration attributes.
        AS_CONTEXT("as-context"),
        AUTH_METHOD("auth-method"),
        REALM("realm"),
        REQUIRED("required"),
        // secure attribute service context attributes.
        SAS_CONTEXT("sas-context"),
        CALLER_PROPAGATION("caller-propagation");



        private final String name;

        /**
         * <p>
         * {@code Element} constructor. Sets the element name.
         * </p>
         *
         * @param name a {@code String} representing the local name of the element.
         */
        Attribute(final String name) {
            this.name = name;
        }

        /**
         * <p>
         * Obtains the local name of this element.
         * </p>
         *
         * @return a {@code String} representing the element's local name.
         */
        public String getLocalName() {
            return name;
        }

        // a map that caches all available elements by name.
        private static final Map<String, Attribute> MAP;

        static {
            final Map<String, Attribute> map = new HashMap<String, Attribute>();
            for (Attribute attribute : values()) {
                final String name = attribute.getLocalName();
                if (name != null)
                    map.put(name, attribute);
            }

            MAP = map;
        }

        public static Attribute forName(String localName) {
            final Attribute attribute = MAP.get(localName);
            return attribute == null ? UNKNOWN : attribute;
        }
    }

    /**
     * <p>
     * Enumeration of the EJB3/IIOP configuration elements.
     * </p>
     *
     * @author <a href="mailto:sguilhen@redhat.com">Stefan Guilhen</a>
     */
    enum Element {

        UNKNOWN(null),

        BINDING_NAME("binding-name"),
        IOR_SECURITY_CONFIG("ior-security-config"),
        // transport configuration elements.
        TRANSPORT_CONFIG("transport-config"),
        // authentication context configuration elements.
        AS_CONTEXT("as-context"),
        // secure attribute service context configuration elements.
        SAS_CONTEXT("sas-context");

        private final String name;

        /**
         * <p>
         * {@code Element} constructor. Sets the element name.
         * </p>
         *
         * @param name a {@code String} representing the local name of the element.
         */
        Element(final String name) {
            this.name = name;
        }

        /**
         * <p>
         * Obtains the local name of this element.
         * </p>
         *
         * @return a {@code String} representing the element's local name.
         */
        public String getLocalName() {
            return name;
        }

        // a map that caches all available elements by name.
        private static final Map<String, Element> MAP;

        static {
            final Map<String, Element> map = new HashMap<String, Element>();
            for (Element element : values()) {
                final String name = element.getLocalName();
                if (name != null)
                    map.put(name, element);
            }
            MAP = map;
        }


        /**
         * <p>
         * Gets the {@code Element} identified by the specified name.
         * </p>
         *
         * @param localName a {@code String} representing the local name of the element.
         * @return the {@code Element} identified by the name. If no attribute can be found, the {@code Element.UNKNOWN}
         *         type is returned.
         */
        public static Element forName(String localName) {
            final Element element = MAP.get(localName);
            return element == null ? UNKNOWN : element;
        }

    }
}
