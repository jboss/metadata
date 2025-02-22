/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.EmptyMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationsMetaData;
import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;
import org.jboss.metadata.parser.ee.MessageDestinationMetaDataParser;
import org.jboss.metadata.parser.ee.ParamValueMetaDataParser;
import org.jboss.metadata.parser.ee.SecurityRoleMetaDataParser;
import org.jboss.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.web.spec.ErrorPageMetaData;
import org.jboss.metadata.web.spec.FilterMappingMetaData;
import org.jboss.metadata.web.spec.FiltersMetaData;
import org.jboss.metadata.web.spec.ListenerMetaData;
import org.jboss.metadata.web.spec.MimeMappingMetaData;
import org.jboss.metadata.web.spec.SecurityConstraintMetaData;
import org.jboss.metadata.web.spec.ServletMappingMetaData;
import org.jboss.metadata.web.spec.ServletsMetaData;
import org.jboss.metadata.web.spec.WebCommonMetaData;


/**
 * @author Remy Maucherat
 */
public class WebCommonMetaDataParser extends MetaDataElementParser {

    public static boolean parse(XMLStreamReader reader, Version version, WebCommonMetaData wmd, PropertyReplacer propertyReplacer) throws XMLStreamException {
        // Only look at the current element, no iteration
        final Element element = Element.forName(reader.getLocalName());
        switch (element) {
            case DISTRIBUTABLE:
                wmd.setDistributable(new EmptyMetaData());
                requireNoContent(reader);
                break;
            case CONTEXT_PARAM:
                List<ParamValueMetaData> contextParams = wmd.getContextParams();
                if (contextParams == null) {
                    contextParams = new ArrayList<ParamValueMetaData>();
                    wmd.setContextParams(contextParams);
                }
                contextParams.add(ParamValueMetaDataParser.parse(reader, propertyReplacer));
                break;
            case FILTER:
                FiltersMetaData filters = wmd.getFilters();
                if (filters == null) {
                    filters = new FiltersMetaData();
                    wmd.setFilters(filters);
                }
                filters.add(FilterMetaDataParser.parse(reader, propertyReplacer));
                break;
            case FILTER_MAPPING:
                List<FilterMappingMetaData> filterMappings = wmd.getFilterMappings();
                if (filterMappings == null) {
                    filterMappings = new ArrayList<FilterMappingMetaData>();
                    wmd.setFilterMappings(filterMappings);
                }
                filterMappings.add(FilterMappingMetaDataParser.parse(reader, propertyReplacer));
                break;
            case LISTENER:
                List<ListenerMetaData> listeners = wmd.getListeners();
                if (listeners == null) {
                    listeners = new ArrayList<ListenerMetaData>();
                    wmd.setListeners(listeners);
                }
                listeners.add(ListenerMetaDataParser.parse(reader, propertyReplacer));
                break;
            case SERVLET:
                ServletsMetaData servlets = wmd.getServlets();
                if (servlets == null) {
                    servlets = new ServletsMetaData();
                    wmd.setServlets(servlets);
                }
                servlets.add(ServletMetaDataParser.parse(reader, propertyReplacer));
                break;
            case SERVLET_MAPPING:
                List<ServletMappingMetaData> servletMappings = wmd.getServletMappings();
                if (servletMappings == null) {
                    servletMappings = new ArrayList<ServletMappingMetaData>();
                    wmd.setServletMappings(servletMappings);
                }
                servletMappings.add(ServletMappingMetaDataParser.parse(reader, propertyReplacer));
                break;
            case SESSION_CONFIG:
                if (wmd.getSessionConfig() != null)
                    throw new XMLStreamException("Multiple session-config elements detected", reader.getLocation());
                wmd.setSessionConfig(new SessionConfigMetaDataParser(version).parse(reader, propertyReplacer));
                break;
            case MIME_MAPPING:
                List<MimeMappingMetaData> mimeMappings = wmd.getMimeMappings();
                if (mimeMappings == null) {
                    mimeMappings = new ArrayList<MimeMappingMetaData>();
                    wmd.setMimeMappings(mimeMappings);
                }
                mimeMappings.add(MimeMappingMetaDataParser.parse(reader, propertyReplacer));
                break;
            case WELCOME_FILE_LIST:
                wmd.setWelcomeFileList(WelcomeFileListMetaDataParser.parse(reader, propertyReplacer));
                break;
            case ERROR_PAGE:
                List<ErrorPageMetaData> errorPages = wmd.getErrorPages();
                if (errorPages == null) {
                    errorPages = new ArrayList<ErrorPageMetaData>();
                    wmd.setErrorPages(errorPages);
                }
                errorPages.add(ErrorPageMetaDataParser.parse(reader, propertyReplacer));
                break;
            case JSP_CONFIG:
                if (wmd.getJspConfig() != null)
                    throw new XMLStreamException("Multiple jsp-config elements detected", reader.getLocation());
                wmd.setJspConfig(JspConfigMetaDataParser.parse(reader, propertyReplacer));
                break;
            case SECURITY_CONSTRAINT:
                List<SecurityConstraintMetaData> securityConstraints = wmd.getSecurityConstraints();
                if (securityConstraints == null) {
                    securityConstraints = new ArrayList<SecurityConstraintMetaData>();
                    wmd.setSecurityConstraints(securityConstraints);
                }
                securityConstraints.add(SecurityConstraintMetaDataParser.parse(reader, propertyReplacer));
                break;
            case LOGIN_CONFIG:
                if (wmd.getLoginConfig() != null)
                    throw new XMLStreamException("Multiple login-config elements detected", reader.getLocation());
                wmd.setLoginConfig(LoginConfigMetaDataParser.parse(reader, propertyReplacer));
                break;
            case SECURITY_ROLE:
                SecurityRolesMetaData securityRoles = wmd.getSecurityRoles();
                if (securityRoles == null) {
                    securityRoles = new SecurityRolesMetaData();
                    wmd.setSecurityRoles(securityRoles);
                }
                securityRoles.add(SecurityRoleMetaDataParser.parse(reader));
                break;
            case MESSAGE_DESTINATION:
                MessageDestinationsMetaData messageDestinations = wmd.getMessageDestinations();
                if (messageDestinations == null) {
                    messageDestinations = new MessageDestinationsMetaData();
                    wmd.setMessageDestinations(messageDestinations);
                }
                messageDestinations.add(MessageDestinationMetaDataParser.parse(reader, propertyReplacer));
                break;
            case LOCALE_ENCODING_MAPPING_LIST:
                wmd.setLocalEncodings(LocaleEncodingsMetaDataParser.parse(reader, propertyReplacer));
                break;
            default:
                return false;
        }
        return true;
    }

}
