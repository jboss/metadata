/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat, Inc., and individual contributors
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

/**
 * <p>
 * This class implements a parser for the IIOP settings section of the jboss-ejb3.xml file.
 * </p>
 *
 * @author <a href="mailto:sguilhen@redhat.com">Stefan Guilhen</a>
 */
public class IIOPMetaDataParser extends AbstractEJBBoundMetaDataParser<IIOPMetaData> {

    @Override
    public IIOPMetaData parse(XMLStreamReader reader) throws XMLStreamException {
        IIOPMetaData metaData = new IIOPMetaData();
        super.processElements(metaData, reader);
        return metaData;
    }

    @Override
    protected void processElement(IIOPMetaData metaData, XMLStreamReader reader) throws XMLStreamException {

        Namespace namespace = Namespace.forUri(reader.getNamespaceURI());
        switch(namespace) {
            case EJB3_IIOP: {
                Element element = Element.forName(reader.getLocalName());
                switch (element) {
                    case BINDING_NAME: {
                        metaData.setBindingName(getElementText(reader));
                        break;
                    }
                    case IOR_SECURITY_CONFIG: {
                        IORSecurityConfigMetaData iorSecurityMetaData = this.processIORSecurityConfig(reader);
                        metaData.setIorSecurityConfigMetaData(iorSecurityMetaData);
                        break;
                    }
                }
                return;
            }
        }
        super.processElement(metaData, reader);
    }

    protected IORSecurityConfigMetaData processIORSecurityConfig(XMLStreamReader reader) throws XMLStreamException {
        IORSecurityConfigMetaData iorSecurityMetaData = new IORSecurityConfigMetaData();

        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case TRANSPORT_CONFIG: {
                    IORTransportConfigMetaData transportConfig = this.processTransportConfig(reader);
                    iorSecurityMetaData.setTransportConfig(transportConfig);
                    break;
                }
                case AS_CONTEXT: {
                    IORASContextMetaData asContext = this.processASContext(reader);
                    iorSecurityMetaData.setAsContext(asContext);
                    break;
                }
                case SAS_CONTEXT: {
                    IORSASContextMetaData sasContext = this.processSASContext(reader);
                    iorSecurityMetaData.setSasContext(sasContext);
                    break;
                }
                default: {
                    throw unexpectedElement(reader);
                }
            }
        }
        return iorSecurityMetaData;
    }

    protected IORTransportConfigMetaData processTransportConfig(XMLStreamReader reader) throws XMLStreamException {

        EnumSet<Attribute> requiredAttributes = EnumSet.of(Attribute.INTEGRITY, Attribute.CONFIDENTIALITY,
                Attribute.ESTABLISH_TRUST_IN_CLIENT, Attribute.ESTABLISH_TRUST_IN_TARGET);

        IORTransportConfigMetaData transportConfig = new IORTransportConfigMetaData();

        // process the transport config attributes.
        for (int i = 0; i < reader.getAttributeCount(); i++) {
            final String attrValue = reader.getAttributeValue(i);
            final Attribute attribute = Attribute.forName(reader.getAttributeLocalName(i));

            // set the attribute in the transport config metadata.
            switch (attribute) {
                case INTEGRITY: {
                    transportConfig.setIntegrity(attrValue);
                    break;
                }
                case CONFIDENTIALITY: {
                    transportConfig.setConfidentiality(attrValue);
                    break;
                }
                case DETECT_MISORDERING: {
                    transportConfig.setDetectMisordering(attrValue);
                    break;
                }
                case DETECT_REPLAY: {
                    transportConfig.setDetectReplay(attrValue);
                    break;
                }
                case ESTABLISH_TRUST_IN_CLIENT: {
                    transportConfig.setEstablishTrustInClient(attrValue);
                    break;
                }
                case ESTABLISH_TRUST_IN_TARGET: {
                    transportConfig.setEstablishTrustInTarget(attrValue);
                    break;
                }
                default: {
                    throw unexpectedAttribute(reader, i);
                }
            }

            requiredAttributes.remove(attribute);
        }

        // throw an exception if a required attribute wasn't found.
        if (!requiredAttributes.isEmpty()) {
            throw missingRequired(reader, requiredAttributes);
        }

        // the transport-config element doesn't have sub-elements.
        requireNoContent(reader);

        return transportConfig;
    }

    protected IORASContextMetaData processASContext(XMLStreamReader reader) throws XMLStreamException {

        EnumSet<Attribute> requiredAttributes = EnumSet.of(Attribute.AUTH_METHOD, Attribute.REALM, Attribute.REQUIRED);

        IORASContextMetaData asContext = new IORASContextMetaData();

        // process the authentication service (AS) attributes.
        for (int i = 0; i < reader.getAttributeCount(); i++) {
            final String attrValue = reader.getAttributeValue(i);
            final Attribute attribute = Attribute.forName(reader.getAttributeLocalName(i));

            // set the attribute in the authentication service (AS) metadata.
            switch (attribute) {
                case AUTH_METHOD: {
                    asContext.setAuthMethod(attrValue);
                    break;
                }
                case REALM: {
                    asContext.setRealm(attrValue);
                    break;
                }
                case REQUIRED: {
                    asContext.setRequired(Boolean.valueOf(attrValue));
                    break;
                }
                default: {
                    throw unexpectedAttribute(reader, i);
                }
            }

            requiredAttributes.remove(attribute);
        }

        // throw an exception if a required attribute wasn't found.
        if (!requiredAttributes.isEmpty()) {
            throw missingRequired(reader, requiredAttributes);
        }

        // the sas-context element doesn't have sub-elements.
        requireNoContent(reader);

        return asContext;

    }

    protected IORSASContextMetaData processSASContext(XMLStreamReader reader) throws XMLStreamException {

        EnumSet<Attribute> requiredAttributes = EnumSet.of(Attribute.CALLER_PROPAGATION);

        IORSASContextMetaData sasContext = new IORSASContextMetaData();

        // process the security attribute service (SAS) attributes.
        for (int i = 0; i < reader.getAttributeCount(); i++) {
            final String attrValue = reader.getAttributeValue(i);
            final Attribute attribute = Attribute.forName(reader.getAttributeLocalName(i));

            // set the attribute in the security attribute service (SAS) metadata.
            switch (attribute) {
                case CALLER_PROPAGATION: {
                    sasContext.setCallerPropagation(attrValue);
                    break;
                }
                default: {
                    throw unexpectedAttribute(reader, i);
                }
            }

            requiredAttributes.remove(attribute);
        }

        // throw an exception if a required attribute wasn't found.
        if (!requiredAttributes.isEmpty()) {
            throw missingRequired(reader, requiredAttributes);
        }

        // the sas-context element doesn't have sub-elements.
        requireNoContent(reader);

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

    /**
      * <p>
      * Enumeration of the EJB3/IIOP configuration elements.
      * </p>
      *
      * @author <a href="mailto:sguilhen@redhat.com">Stefan Guilhen</a>
      */
     enum Element {

         UNKNOWN(null),

         // elements used to configure the IIOP settings.
         BINDING_NAME("binding-name"),
         IOR_SECURITY_CONFIG("ior-security-config"),

         // sub-elements of the ior-security-config element.
         TRANSPORT_CONFIG("transport-config"),
         AS_CONTEXT("as-context"),
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
          * type is returned.
          */
         public static Element forName(String localName) {
             final Element element = MAP.get(localName);
             return element == null ? UNKNOWN : element;
         }

     }

       /**
     * <p>
     * Enumeration of the EJB3/IIOP configuration attributes.
     * </p>
     *
     * @author <a href="mailto:sguilhen@redhat.com">Stefan Guilhen</a>
     */
    enum Attribute {

        UNKNOWN(null),

        // attributes of the transport-config element.
        INTEGRITY("integrity"),
        CONFIDENTIALITY("confidentiality"),
        DETECT_MISORDERING("detect-misordering"),
        DETECT_REPLAY("detect-replay"),
        ESTABLISH_TRUST_IN_CLIENT("establish-trust-in-client"),
        ESTABLISH_TRUST_IN_TARGET("establish-trust-in-target"),

        // attributes of the as-context element.
        AUTH_METHOD("auth-method"),
        REALM("realm"),
        REQUIRED("required"),

        // atributes of the sas-context element.
        CALLER_PROPAGATION("caller-propagation");

        private final String name;

        /**
         * <p>
         * {@code Attribute} constructor. Sets the attribute name.
         * </p>
         *
         * @param name a {@code String} representing the local name of the attribute.
         */
        Attribute(final String name) {
            this.name = name;
        }

        /**
         * <p>
         * Obtains the local name of this attribute.
         * </p>
         *
         * @return a {@code String} representing the attribute local name.
         */
        public String getLocalName() {
            return this.name;
        }

        // a map that caches all available attributes by name.
        private static final Map<String, Attribute> MAP;

        static {
            final Map<String, Attribute> map = new HashMap<String, Attribute>();
            for (Attribute attribute : values()) {
                final String name = attribute.name;
                if (name != null)
                    map.put(name, attribute);
            }
            MAP = map;
        }

        /**
         * <p>
         * Gets the {@code Attribute} identified by the specified name.
         * </p>
         *
         * @param localName a {@code String} representing the local name of the attribute.
         * @return the {@code Attribute} identified by the name. If no attribute can be found, the {@code Attribute.UNKNOWN}
         * type is returned.
         */
        public static Attribute forName(String localName) {
            final Attribute attribute = MAP.get(localName);
            return attribute == null ? UNKNOWN : attribute;
        }
    }
}
