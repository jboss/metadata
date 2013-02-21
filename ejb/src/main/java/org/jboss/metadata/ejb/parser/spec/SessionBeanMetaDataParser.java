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

package org.jboss.metadata.ejb.parser.spec;

import javax.ejb.TransactionManagementType;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.AbstractGenericBeanMetaData;
import org.jboss.metadata.ejb.spec.NamedMethodMetaData;
import org.jboss.metadata.ejb.spec.SecurityIdentityMetaData;
import org.jboss.metadata.ejb.spec.SessionBeanMetaData;
import org.jboss.metadata.ejb.spec.SessionType;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.Environment;
import org.jboss.metadata.javaee.spec.EnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.LifecycleCallbackMetaData;
import org.jboss.metadata.javaee.spec.LifecycleCallbacksMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleRefMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleRefsMetaData;
import org.jboss.metadata.parser.ee.DescriptionGroupMetaDataParser;
import org.jboss.metadata.parser.ee.EnvironmentRefsGroupMetaDataParser;
import org.jboss.metadata.parser.ee.LifecycleCallbackMetaDataParser;
import org.jboss.metadata.parser.ee.SecurityRoleRefMetaDataParser;
import org.jboss.metadata.property.PropertyReplacer;

import static org.jboss.metadata.ejb.parser.spec.AttributeProcessorHelper.processAttributes;

/**
 * Parses and creates metadata out of the &lt;session&gt; element in the ejb-jar.xml
 * <p/>
 * This class parses the common ejb-jar.xml elements. Individual ejb-jar version specific implementations
 * should override the {@link #processElement(org.jboss.metadata.ejb.spec.SessionBeanMetaData, javax.xml.stream.XMLStreamReader, PropertyReplacer)}
 * method to parse the version specific ejb-jar.xml elements
 * <p/>
 * User: Jaikiran Pai
 */
public abstract class SessionBeanMetaDataParser<T extends AbstractGenericBeanMetaData> extends AbstractIdMetaDataParser<T> {

    /**
     * Create and return the correct version of {@link SessionBeanMetaData}
     * <p/>
     * Individual ejb-jar version specific implementations of {@link SessionBeanMetaDataParser this class} should
     * implement this method to return the appropriate version specific {@link SessionBeanMetaData}
     *
     * @return
     */
    protected abstract T createSessionBeanMetaData();

    /**
     * Creates and returns {@link SessionBeanMetaData} after parsing the session element.
     *
     * @param reader
     * @return
     * @throws XMLStreamException
     */
    @Override
    public T parse(XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        T sessionBean = createSessionBeanMetaData();
        processAttributes(sessionBean, reader, this);
        this.processElements(sessionBean, reader, propertyReplacer);
        // return the metadata created out of parsing
        return sessionBean;
    }

    /**
     * Parses common (version indepndent) ejb-jar.xml elements and updates the passed {@link SessionBeanMetaData ejb metadata} appropriately
     *
     * @param sessionBean The session bean metadata
     * @param reader      The XMLStreamReader
     * @throws XMLStreamException
     */
    @Override
    protected void processElement(T sessionBean, XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        // Handle the description group elements
        DescriptionGroupMetaData descriptionGroup = new DescriptionGroupMetaData();
        if (DescriptionGroupMetaDataParser.parse(reader, descriptionGroup)) {
            if (sessionBean.getDescriptionGroup() == null) {
                sessionBean.setDescriptionGroup(descriptionGroup);
            }
            return;
        }

        // Handle jndi environment ref group
        // get the jndi environment ref group of this bean
        Environment jndiEnvRefGroup = sessionBean.getJndiEnvironmentRefsGroup();
        // create and set, if absent
        if (jndiEnvRefGroup == null) {
            jndiEnvRefGroup = new EnvironmentRefsGroupMetaData();
            sessionBean.setJndiEnvironmentRefsGroup(jndiEnvRefGroup);
        }
        // Not too good!
        if (jndiEnvRefGroup instanceof EnvironmentRefsGroupMetaData) {
            // parse any jndi ref group elements
            if (EnvironmentRefsGroupMetaDataParser.parse(reader, (EnvironmentRefsGroupMetaData) jndiEnvRefGroup, propertyReplacer)) {
                // it was jndi ref group element which was parsed successfully, so nothing more to do
                // than just return
                return;
            }
        }

        // get the element to process
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case EJB_NAME:
                sessionBean.setEjbName(getElementText(reader, propertyReplacer));
                return;

            case MAPPED_NAME:
                sessionBean.setMappedName(getElementText(reader, propertyReplacer));
                return;

            case HOME:
                sessionBean.setHome(getElementText(reader, propertyReplacer));
                return;

            case REMOTE:
                sessionBean.setRemote(getElementText(reader, propertyReplacer));
                return;

            case LOCAL_HOME:
                sessionBean.setLocalHome(getElementText(reader, propertyReplacer));
                return;

            case LOCAL:
                sessionBean.setLocal(getElementText(reader, propertyReplacer));
                return;

            case SERVICE_ENDPOINT:
                sessionBean.setServiceEndpoint(getElementText(reader, propertyReplacer));
                return;

            case EJB_CLASS:
                sessionBean.setEjbClass(getElementText(reader, propertyReplacer));
                return;

            case SESSION_TYPE:
                SessionType sessionType = this.processSessionType(getElementText(reader, propertyReplacer));
                if (sessionType == null) {
                    throw unexpectedValue(reader, new Exception("Unexpected value: " + sessionType + " for session-type"));
                } else {
                    sessionBean.setSessionType(sessionType);
                }
                return;

            case TIMEOUT_METHOD:
                NamedMethodMetaData timeoutMethod = NamedMethodMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                sessionBean.setTimeoutMethod(timeoutMethod);
                return;

            case TRANSACTION_TYPE:
                String txType = getElementText(reader, propertyReplacer);
                if (txType.equals("Bean")) {
                    sessionBean.setTransactionType(TransactionManagementType.BEAN);
                } else if (txType.equals("Container")) {
                    sessionBean.setTransactionType(TransactionManagementType.CONTAINER);
                } else {
                    throw unexpectedValue(reader, new Exception("Unexpected value: " + txType + " for transaction-type"));
                }
                return;

            case POST_ACTIVATE:
                LifecycleCallbacksMetaData postActivates = sessionBean.getPostActivates();
                if (postActivates == null) {
                    postActivates = new LifecycleCallbacksMetaData();
                    sessionBean.setPostActivates(postActivates);
                }
                LifecycleCallbackMetaData postActivate = LifecycleCallbackMetaDataParser.parse(reader, propertyReplacer);
                postActivates.add(postActivate);
                return;

            case PRE_PASSIVATE:
                LifecycleCallbacksMetaData prePassivates = sessionBean.getPrePassivates();
                if (prePassivates == null) {
                    prePassivates = new LifecycleCallbacksMetaData();
                    sessionBean.setPrePassivates(prePassivates);
                }
                LifecycleCallbackMetaData prePassivate = LifecycleCallbackMetaDataParser.parse(reader, propertyReplacer);
                prePassivates.add(prePassivate);
                return;

            case SECURITY_ROLE_REF:
                SecurityRoleRefsMetaData securityRoleRefs = sessionBean.getSecurityRoleRefs();
                if (securityRoleRefs == null) {
                    securityRoleRefs = new SecurityRoleRefsMetaData();
                    sessionBean.setSecurityRoleRefs(securityRoleRefs);
                }
                SecurityRoleRefMetaData securityRoleRef = SecurityRoleRefMetaDataParser.parse(reader, propertyReplacer);
                securityRoleRefs.add(securityRoleRef);
                return;

            case SECURITY_IDENTITY:
                final SecurityIdentityMetaData securityIdentity = SecurityIdentityParser.INSTANCE.parse(reader, propertyReplacer);
                sessionBean.setSecurityIdentity(securityIdentity);
                return;

            default:
                throw unexpectedElement(reader);
        }
    }

    /**
     * Returns the {@link SessionType} corresponding to the passed <code>sessionType</code> string.
     * <p/>
     * Returns null, if the passed <code>sessionType</code> isn't one of the allowed values for the
     * &lt;session-type&gt; element in ejb-jar.xml
     *
     * @param sessionType
     * @return
     */
    protected SessionType processSessionType(String sessionType) {
        if (sessionType.equals("Stateless")) {
            return SessionType.Stateless;
        }

        if (sessionType.equals("Stateful")) {
            return SessionType.Stateful;
        }
        return null;
    }
}
