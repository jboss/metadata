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

package org.jboss.metadata.parser.ee;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.javaee.spec.AdministeredObjectsMetaData;
import org.jboss.metadata.javaee.spec.ConnectionFactoriesMetaData;
import org.jboss.metadata.javaee.spec.DataSourcesMetaData;
import org.jboss.metadata.javaee.spec.EJBLocalReferencesMetaData;
import org.jboss.metadata.javaee.spec.EJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntriesMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.JMSConnectionFactoriesMetaData;
import org.jboss.metadata.javaee.spec.JMSDestinationsMetaData;
import org.jboss.metadata.javaee.spec.LifecycleCallbacksMetaData;
import org.jboss.metadata.javaee.spec.MailSessionsMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferencesMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferencesMetaData;
import org.jboss.metadata.javaee.spec.PersistenceUnitReferencesMetaData;
import org.jboss.metadata.javaee.spec.RemoteEnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferencesMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;
import org.jboss.metadata.property.PropertyReplacer;
import org.jboss.metadata.property.PropertyReplacers;


/**
 * @author Remy Maucherat
 * @author Eduardo Martins
 */
public class EnvironmentRefsGroupMetaDataParser {

    public static boolean parse(XMLStreamReader reader, EnvironmentRefsGroupMetaData env) throws XMLStreamException {
        return parse(reader, env, PropertyReplacers.noop());
    }

    public static boolean parse(XMLStreamReader reader, EnvironmentRefsGroupMetaData env, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        // Only look at the current element, no iteration
        final Element element = Element.forName(reader.getLocalName());
        switch (element) {
            case EJB_LOCAL_REF:
                EJBLocalReferencesMetaData ejbLocalReferences = env.getEjbLocalReferences();
                if (ejbLocalReferences == null) {
                    ejbLocalReferences = new EJBLocalReferencesMetaData();
                    env.setEjbLocalReferences(ejbLocalReferences);
                }
                ejbLocalReferences.add(EJBLocalReferenceMetaDataParser.parse(reader, propertyReplacer));
                break;
            case PERSISTENCE_CONTEXT_REF:
                PersistenceContextReferencesMetaData pcReferences = env.getPersistenceContextRefs();
                if (pcReferences == null) {
                    pcReferences = new PersistenceContextReferencesMetaData();
                    env.setPersistenceContextRefs(pcReferences);
                }
                pcReferences.add(PersistenceContextReferenceMetaDataParser.parse(reader, propertyReplacer));
                break;
            default:
                return parseRemote(reader, env, propertyReplacer);
        }
        return true;
    }

    public static boolean parseRemote(XMLStreamReader reader, RemoteEnvironmentRefsGroupMetaData env) throws XMLStreamException {
        return parseRemote(reader, env, PropertyReplacers.noop());
    }

    public static boolean parseRemote(XMLStreamReader reader, RemoteEnvironmentRefsGroupMetaData env, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        // Only look at the current element, no iteration
        final Element element = Element.forName(reader.getLocalName());
        switch (element) {
            case ENV_ENTRY:
                EnvironmentEntriesMetaData envEntries = env.getEnvironmentEntries();
                if (envEntries == null) {
                    envEntries = new EnvironmentEntriesMetaData();
                    env.setEnvironmentEntries(envEntries);
                }
                envEntries.add(EnvironmentEntryMetaDataParser.parse(reader, propertyReplacer));
                break;
            case EJB_REF:
                EJBReferencesMetaData ejbReferences = env.getEjbReferences();
                if (ejbReferences == null) {
                    ejbReferences = new EJBReferencesMetaData();
                    env.setEjbReferences(ejbReferences);
                }
                ejbReferences.add(EJBReferenceMetaDataParser.parse(reader, propertyReplacer));
                break;
            case SERVICE_REF:
                ServiceReferencesMetaData serviceReferences = env.getServiceReferences();
                if (serviceReferences == null) {
                    serviceReferences = new ServiceReferencesMetaData();
                    env.setServiceReferences(serviceReferences);
                }
                serviceReferences.add(ServiceReferenceMetaDataParser.parse(reader, propertyReplacer));
                break;
            case RESOURCE_REF:
                ResourceReferencesMetaData resourceReferences = env.getResourceReferences();
                if (resourceReferences == null) {
                    resourceReferences = new ResourceReferencesMetaData();
                    env.setResourceReferences(resourceReferences);
                }
                resourceReferences.add(ResourceReferenceMetaDataParser.parse(reader, propertyReplacer));
                break;
            case RESOURCE_ENV_REF:
                ResourceEnvironmentReferencesMetaData resourceEnvReferences = env.getResourceEnvironmentReferences();
                if (resourceEnvReferences == null) {
                    resourceEnvReferences = new ResourceEnvironmentReferencesMetaData();
                    env.setResourceEnvironmentReferences(resourceEnvReferences);
                }
                resourceEnvReferences.add(ResourceEnvironmentReferenceMetaDataParser.parse(reader, propertyReplacer));
                break;
            case MESSAGE_DESTINATION_REF:
                MessageDestinationReferencesMetaData mdReferences = env.getMessageDestinationReferences();
                if (mdReferences == null) {
                    mdReferences = new MessageDestinationReferencesMetaData();
                    env.setMessageDestinationReferences(mdReferences);
                }
                mdReferences.add(MessageDestinationReferenceMetaDataParser.parse(reader, propertyReplacer));
                break;
            case PERSISTENCE_UNIT_REF:
                PersistenceUnitReferencesMetaData puReferences = env.getPersistenceUnitRefs();
                if (puReferences == null) {
                    puReferences = new PersistenceUnitReferencesMetaData();
                    env.setPersistenceUnitRefs(puReferences);
                }
                puReferences.add(PersistenceUnitReferenceMetaDataParser.parse(reader, propertyReplacer));
                break;
            case POST_CONSTRUCT:
                LifecycleCallbacksMetaData postConstructs = env.getPostConstructs();
                if (postConstructs == null) {
                    postConstructs = new LifecycleCallbacksMetaData();
                    env.setPostConstructs(postConstructs);
                }
                postConstructs.add(LifecycleCallbackMetaDataParser.parse(reader, propertyReplacer));
                break;
            case PRE_DESTROY:
                LifecycleCallbacksMetaData preDestroys = env.getPreDestroys();
                if (preDestroys == null) {
                    preDestroys = new LifecycleCallbacksMetaData();
                    env.setPreDestroys(preDestroys);
                }
                preDestroys.add(LifecycleCallbackMetaDataParser.parse(reader, propertyReplacer));
                break;
            case DATA_SOURCE:
                DataSourcesMetaData dataSources = env.getDataSources();
                if (dataSources == null) {
                    dataSources = new DataSourcesMetaData();
                    env.setDataSources(dataSources);
                }
                dataSources.add(DataSourceMetaDataParser.parse(reader, propertyReplacer));
                break;
            case ADMINISTERED_OBJECT:
                AdministeredObjectsMetaData administeredObjectsMetaData = env.getAdministeredObjects();
                if (administeredObjectsMetaData == null) {
                    administeredObjectsMetaData = new AdministeredObjectsMetaData();
                    env.setAdministeredObjects(administeredObjectsMetaData);
                }
                administeredObjectsMetaData.add(AdministeredObjectMetaDataParser.parse(reader, propertyReplacer));
                break;
            case CONNECTION_FACTORY:
                ConnectionFactoriesMetaData connectionFactoriesMetaData = env.getConnectionFactories();
                if (connectionFactoriesMetaData == null) {
                    connectionFactoriesMetaData = new ConnectionFactoriesMetaData();
                    env.setConnectionFactories(connectionFactoriesMetaData);
                }
                connectionFactoriesMetaData.add(ConnectionFactoryMetaDataParser.parse(reader, propertyReplacer));
                break;
            case JMS_CONNECTION_FACTORY:
                JMSConnectionFactoriesMetaData jmsConnectionFactoriesMetaData = env.getJmsConnectionFactories();
                if (jmsConnectionFactoriesMetaData == null) {
                    jmsConnectionFactoriesMetaData = new JMSConnectionFactoriesMetaData();
                    env.setJmsConnectionFactories(jmsConnectionFactoriesMetaData);
                }
                jmsConnectionFactoriesMetaData.add(JMSConnectionFactoryMetaDataParser.parse(reader, propertyReplacer));
                break;
            case JMS_DESTINATION:
                JMSDestinationsMetaData jmsDestinationsMetaData = env.getJmsDestinations();
                if (jmsDestinationsMetaData == null) {
                    jmsDestinationsMetaData = new JMSDestinationsMetaData();
                    env.setJmsDestinations(jmsDestinationsMetaData);
                }
                jmsDestinationsMetaData.add(JMSDestinationMetaDataParser.parse(reader, propertyReplacer));
                break;
            case MAIL_SESSION:
                MailSessionsMetaData mailSessionsMetaData = env.getMailSessions();
                if (mailSessionsMetaData == null) {
                    mailSessionsMetaData = new MailSessionsMetaData();
                    env.setMailSessions(mailSessionsMetaData);
                }
                mailSessionsMetaData.add(MailSessionMetaDataParser.parse(reader, propertyReplacer));
                break;
            default:
                return false;
        }
        return true;
    }
}
