/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentRefsGroupMetaData;
import org.jboss.metadata.parser.ee.DescriptionGroupMetaDataParser;
import org.jboss.metadata.parser.ee.EnvironmentRefsGroupMetaDataParser;
import org.jboss.metadata.parser.ee.IdMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.web.spec.JspConfigMetaData;
import org.jboss.metadata.web.spec.TaglibMetaData;
import org.jboss.metadata.web.spec.WebMetaData;


/**
 * Stax parser for web metadata
 *
 * @author Remy Maucherat
 * @author Thomas.Diesler@jboss.com
 */
public class WebMetaDataParser extends MetaDataElementParser {

    public static WebMetaData parse(XMLStreamReader reader, DTDInfo info, PropertyReplacer propertyReplacer) throws XMLStreamException {
        return parse(reader, info, false, propertyReplacer);
    }

    public static WebMetaData parse(XMLStreamReader reader, DTDInfo info, boolean validation, PropertyReplacer propertyReplacer) throws XMLStreamException {
        if (reader == null)
            throw new IllegalArgumentException("Null reader");
        if (info == null)
            throw new IllegalArgumentException("Null info");

        reader.require(START_DOCUMENT, null, null);

        // Read until the first start element
        while (reader.hasNext() && reader.next() != START_ELEMENT) {}

        String schemaLocation = readSchemaLocation(reader);

        Version version = null;
        if (info.getPublicID() != null) {
            version = Version.fromPublicID(info.getPublicID());
        }
        if (version == null && info.getSystemID() != null) {
            version = Version.fromSystemID(info.getSystemID());
        }
        if (version == null && schemaLocation != null) {
            version = Version.fromSystemID(schemaLocation);
        }
        WebMetaData wmd = new WebMetaData();
        if (version == null) {
            String versionString = reader.getAttributeValue(null, Attribute.VERSION.getLocalName());
            if (versionString != null) {
                version = Version.fromString(versionString);
            }
        }
        if (version != null) {
            wmd.setVersion(version.toString());
        }
        // Set the publicId / systemId
        wmd.setDTD(info.getBaseURI(), info.getPublicID(), info.getSystemID());

        // Set the schema location if we have one
        if (schemaLocation != null)
            wmd.setSchemaLocation(schemaLocation);

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
                case METADATA_COMPLETE: {
                    if (Boolean.TRUE.equals(Boolean.valueOf(value))) {
                        wmd.setMetadataComplete(true);
                    }
                    break;
                }
                default:
                    IdMetaDataParser.parseAttribute(reader, i, wmd);
            }
        }

        DescriptionGroupMetaData descriptionGroup = new DescriptionGroupMetaData();
        EnvironmentRefsGroupMetaData env = new EnvironmentRefsGroupMetaData();
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            if (WebCommonMetaDataParser.parse(reader, version, wmd, propertyReplacer)) {
                continue;
            }
            if (EnvironmentRefsGroupMetaDataParser.parse(reader, env, propertyReplacer)) {
                if (wmd.getJndiEnvironmentRefsGroup() == null) {
                    wmd.setJndiEnvironmentRefsGroup(env);
                }
                continue;
            }
            if (DescriptionGroupMetaDataParser.parse(reader, descriptionGroup)) {
                if (wmd.getDescriptionGroup() == null) {
                    wmd.setDescriptionGroup(descriptionGroup);
                }
                continue;
            }
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case ABSOLUTE_ORDERING:
                    wmd.setAbsoluteOrdering(AbsoluteOrderingMetaDataParser.parse(reader, propertyReplacer));
                    break;
                case MODULE_NAME:
                    wmd.setModuleName(getElementText(reader, propertyReplacer));
                    break;
                case TAGLIB:
                    JspConfigMetaData jspConfig = wmd.getJspConfig();
                    if (jspConfig == null) {
                        jspConfig = new JspConfigMetaData();
                        wmd.setJspConfig(jspConfig);
                    }
                    List<TaglibMetaData> taglibs = jspConfig.getTaglibs();
                    if (taglibs == null) {
                        taglibs = new ArrayList<TaglibMetaData>();
                        jspConfig.setTaglibs(taglibs);
                    }
                    taglibs.add(TaglibMetaDataParser.parse(reader, propertyReplacer));

                    break;
                case DENY_UNCOVERED_HTTP_METHODS:
                    //https://www.oracle.com/webfolder/technetwork/jsc/xml/ns/javaee/web-app_4_0.xsd
                    //<xsd:element name="deny-uncovered-http-methods" type="javaee:emptyType">
                    wmd.setDenyUncoveredHttpMethods(Boolean.TRUE);
                    requireNoContent(reader);
                    break;
                case DEFAULT_CONTEXT_PATH:
                    wmd.setDefaultContextPath(getElementText(reader, propertyReplacer));
                    break;
                case REQUEST_CHARACTER_ENCODING:
                    wmd.setRequestCharacterEncoding(getElementText(reader, propertyReplacer));
                    break;
                case RESPONSE_CHARACTER_ENCODING:
                    wmd.setResponseCharacterEncoding(getElementText(reader, propertyReplacer));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }

        return wmd;
    }

}
