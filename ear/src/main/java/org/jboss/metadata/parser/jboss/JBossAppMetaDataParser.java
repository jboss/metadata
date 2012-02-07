package org.jboss.metadata.parser.jboss;

import org.jboss.logging.Logger;
import org.jboss.metadata.common.jboss.LoaderRepositoryConfigMetaData;
import org.jboss.metadata.common.jboss.LoaderRepositoryMetaData;
import org.jboss.metadata.ear.jboss.JBossAppMetaData;
import org.jboss.metadata.ear.jboss.ServiceModuleMetaData;
import org.jboss.metadata.ear.spec.EarEnvironmentRefsGroupMetaData;
import org.jboss.metadata.ear.spec.ModuleMetaData;
import org.jboss.metadata.ear.spec.ModulesMetaData;
import org.jboss.metadata.ear.spec.WebModuleMetaData;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationsMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;
import org.jboss.metadata.parser.ee.SecurityRoleMetaDataParser;
import org.jboss.metadata.parser.spec.EarMetaDataParser;
import org.jboss.metadata.parser.util.PropertiesValueResolver;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * @author John Bailey
 */
public class JBossAppMetaDataParser extends EarMetaDataParser {

    private static final Logger logger = Logger.getLogger(JBossAppMetaDataParser.class);

    public static final JBossAppMetaDataParser INSTANCE = new JBossAppMetaDataParser();

    public JBossAppMetaData parse(final XMLStreamReader reader) throws XMLStreamException {
        final EarMetaDataParser earMetaDataParser = new EarMetaDataParser();
        reader.require(START_DOCUMENT, null, null);

        // Read until the first start element
        Version version = null;
        while (reader.hasNext() && reader.next() != START_ELEMENT) {
            if (reader.getEventType() == DTD) {
                final String dtdLocation = readDTDLocation(reader);
                if (dtdLocation != null) {
                    version = Version.forLocation(dtdLocation);
                }
            }
        }
        final String schemaLocation = readSchemaLocation(reader);
        if (schemaLocation != null) {
            version = Version.forLocation(schemaLocation);
        }

        if (version == null || Version.UNKNOWN.equals(version)) {
            version = Version.APP_7_0;
        }

        JBossAppMetaData appMetaData = new JBossAppMetaData();

        // Handle attributes
        final int count = reader.getAttributeCount();
        for (int i = 0; i < count; i++) {
            final String value = reader.getAttributeValue(i);
            if (attributeHasNamespace(reader, i)) {
                continue;
            }
            final Attribute attribute = Attribute.forName(reader.getAttributeLocalName(i));
            switch (attribute) {
                case ID: {
                    appMetaData.setId(value);
                    break;
                }
                case VERSION: {
                    break;
                }
                default:
                    throw unexpectedAttribute(reader, i);
            }
        }

        //TODO: hn

        final EarEnvironmentRefsGroupMetaData environmentRefsGroupMetaData = new EarEnvironmentRefsGroupMetaData();
        appMetaData.setDescriptionGroup(new DescriptionGroupMetaData());
        appMetaData.setModules(new ModulesMetaData());
        appMetaData.setSecurityRoles(new SecurityRolesMetaData());
        environmentRefsGroupMetaData.setMessageDestinations(new MessageDestinationsMetaData());
        appMetaData.setEarEnvironmentRefsGroup(environmentRefsGroupMetaData);
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            Namespace namespace = Namespace.forUri(reader.getNamespaceURI());
            if(namespace == Namespace.SPEC) {
               super.handleElement(reader, appMetaData);
            } else {
               switch (element) {
                   case DISTINCT_NAME: {
                       final String val = getElementText(reader);
                       appMetaData.setDistinctName(PropertiesValueResolver.replaceProperties(val));
                       break;
                   }
                   case MODULE_ORDER: {
                       logger.warn("module-order element in jboss-app.xml is deprecated and has been ignored");
                       //depricated
                       break;
                   }
                   case SECURITY_DOMAIN: {
                       appMetaData.setSecurityDomain(getElementText(reader));
                       break;
                   }
                   case UNAUTHENTICATED_PRINCIPAL: {
                       appMetaData.setUnauthenticatedPrincipal(getElementText(reader));
                       break;
                   }
                   case JMX_NAME: {
                       logger.warn("jmx-name element in jboss-app.xml is deprecated and has been ignored");
                       break;
                   }
                   case LIBRARY_DIRECTORY: {
                       appMetaData.setLibraryDirectory(getElementText(reader));
                       break;
                   }
                   case LOADER_REPOSITORY: {
                       parseLoaderRepository(reader);
                       logger.warn("loader-repository element in jboss-app.xml is deprecated and has been ignored");
                       break;
                   }
                   case MODULE: {
                       appMetaData.getModules().add(parseModule(reader));
                       break;
                   }
                   case SECURITY_ROLE: {
                       appMetaData.getSecurityRoles().add(SecurityRoleMetaDataParser.parse(reader));
                       break;
                   }
                   default:
                      throw unexpectedElement(reader);
               }
            }
        }

        return appMetaData;
    }

    private static LoaderRepositoryMetaData parseLoaderRepository(XMLStreamReader reader) throws XMLStreamException {
        final LoaderRepositoryMetaData repositoryMetaData = new LoaderRepositoryMetaData();
        final int count = reader.getAttributeCount();
        for (int i = 0; i < count; i++) {
            final String value = reader.getAttributeValue(i);
            if (attributeHasNamespace(reader, i)) {
                continue;
            }
            final Attribute attribute = Attribute.forName(reader.getAttributeLocalName(i));
            switch (attribute) {
                case ID: {
                    repositoryMetaData.setId(value);
                    break;
                }
                case LOADER_REPOSITORY_CLASS: {
                    repositoryMetaData.setLoaderRepositoryClass(value);
                }
                default:
                    throw unexpectedAttribute(reader, i);
            }
        }

        while (reader.hasNext() && reader.next() != END_ELEMENT) {
            if (reader.hasText()) {
                final StringBuilder builder = new StringBuilder();
                int event = reader.getEventType();
                // while (event != END_ELEMENT) {
                    if (event == XMLStreamConstants.CHARACTERS
                            || event == XMLStreamConstants.CDATA
                            || event == XMLStreamConstants.SPACE
                            || event == XMLStreamConstants.ENTITY_REFERENCE) {
                        builder.append(reader.getText());
                    } else if (event == XMLStreamConstants.PROCESSING_INSTRUCTION || event == XMLStreamConstants.COMMENT) {
                        // Skips (not kept).
                    } else if (event == XMLStreamConstants.END_DOCUMENT) {
                        throw new XMLStreamException(
                                "Unexpected end of document when reading element text content",
                                reader.getLocation());
                    } else if (event == XMLStreamConstants.START_ELEMENT) {
                        throw new XMLStreamException(
                                "Element text content may not contain START_ELEMENT",
                                reader.getLocation());
                    } else {
                        throw new XMLStreamException("Unexpected event type "
                                + event, reader.getLocation());
                    }
                    repositoryMetaData.setName(builder.toString());
                // }
            } else if (reader.isStartElement()) {
                final Set<LoaderRepositoryConfigMetaData> loaderConfigs = new HashSet<LoaderRepositoryConfigMetaData>();
                final Element element = Element.forName(reader.getLocalName());
                switch (element) {
                    case LOADER_REPOSITORY_CONFIG: {
                        loaderConfigs.add(parseLoaderRepositoryConfig(reader));
                        break;
                    }
                }
                repositoryMetaData.setLoaderRepositoryConfig(loaderConfigs);
            } else {
                throw unexpectedElement(reader);
            }
        }
        return repositoryMetaData;
    }

    private static LoaderRepositoryConfigMetaData parseLoaderRepositoryConfig(XMLStreamReader reader) throws XMLStreamException {
        final LoaderRepositoryConfigMetaData configMetaData = new LoaderRepositoryConfigMetaData();
        final int count = reader.getAttributeCount();
        for (int i = 0; i < count; i++) {
            final String value = reader.getAttributeValue(i);
            if (attributeHasNamespace(reader, i)) {
                continue;
            }
            final Attribute attribute = Attribute.forName(reader.getAttributeLocalName(i));
            switch (attribute) {
                case ID: {
                    configMetaData.setId(value);
                    break;
                }
                case CONFIG_PARSER_CLASS: {
                    configMetaData.setConfigParserClass(value);
                }
                default:
                    throw unexpectedAttribute(reader, i);
            }
        }
        configMetaData.setConfig(getElementText(reader));
        return configMetaData;
    }


    private static ModuleMetaData parseModule(XMLStreamReader reader) throws XMLStreamException {
        final ModuleMetaData moduleMetaData = new ModuleMetaData();

        final int count = reader.getAttributeCount();
        for (int i = 0; i < count; i++) {
            final String value = reader.getAttributeValue(i);
            if (attributeHasNamespace(reader, i)) {
                continue;
            }
            final Attribute attribute = Attribute.forName(reader.getAttributeLocalName(i));
            switch (attribute) {
                case ID: {
                    moduleMetaData.setId(value);
                    break;
                }
                default:
                    throw unexpectedAttribute(reader, i);
            }
        }

        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case HAR:
                    moduleMetaData.setValue(parseService(reader));
                    break;
                case SERVICE:
                    moduleMetaData.setValue(parseService(reader));
                    break;
                case WEB:
                    moduleMetaData.setValue(parseWeb(reader));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }
        return moduleMetaData;
    }

    private static ServiceModuleMetaData parseService(final XMLStreamReader reader) throws XMLStreamException {
        final ServiceModuleMetaData module = new ServiceModuleMetaData();
        module.setFileName(getElementText(reader));
        return module;
    }

    private static WebModuleMetaData parseWeb(final XMLStreamReader reader) throws XMLStreamException {
        final WebModuleMetaData webModuleMetaData = new WebModuleMetaData();
        final int count = reader.getAttributeCount();
        for (int i = 0; i < count; i++) {
            final String value = reader.getAttributeValue(i);
            if (attributeHasNamespace(reader, i)) {
                continue;
            }
            final Attribute attribute = Attribute.forName(reader.getAttributeLocalName(i));
            switch (attribute) {
                case ID: {
                    webModuleMetaData.setId(value);
                    break;
                }
                default:
                    throw unexpectedAttribute(reader, i);
            }
        }

        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            final org.jboss.metadata.parser.spec.Element element = org.jboss.metadata.parser.spec.Element.forName(reader.getLocalName());
            switch (element) {
                case CONTEXT_ROOT:
                    webModuleMetaData.setContextRoot(getElementText(reader));
                    break;
                case WEB_URI:
                    webModuleMetaData.setWebURI(getElementText(reader));
                    break;
                default:
                    throw unexpectedElement(reader);
            }
        }
        return webModuleMetaData;
    }
}
