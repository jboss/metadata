/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.parser.spec;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.AbstractEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.spec.SecurityIdentityMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleRefMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleRefsMetaData;
import org.jboss.metadata.parser.ee.EnvironmentRefsGroupMetaDataParser;
import org.jboss.metadata.parser.ee.SecurityRoleRefMetaDataParser;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public abstract class AbstractEnterpriseBeanMetaDataParser<MD extends AbstractEnterpriseBeanMetaData> extends AbstractNamedMetaDataWithDescriptionGroupParser<MD> {
    @Override
    protected void processElement(MD bean, XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        EnvironmentRefsGroupMetaData jndiEnvRefGroup = (EnvironmentRefsGroupMetaData) bean.getJndiEnvironmentRefsGroup();
        if (jndiEnvRefGroup == null) {
            jndiEnvRefGroup = new EnvironmentRefsGroupMetaData();
            bean.setEnvironmentRefsGroup(jndiEnvRefGroup);
        }
        if (EnvironmentRefsGroupMetaDataParser.parse(reader, jndiEnvRefGroup, propertyReplacer))
            return;

        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case EJB_CLASS:
                bean.setEjbClass(getElementText(reader, propertyReplacer));
                break;

            case EJB_NAME:
                bean.setEjbName(getElementText(reader, propertyReplacer));
                break;

            case MAPPED_NAME:
                bean.setMappedName(getElementText(reader, propertyReplacer));
                break;

            case SECURITY_IDENTITY:
                final SecurityIdentityMetaData securityIdentity = SecurityIdentityParser.INSTANCE.parse(reader, propertyReplacer);
                bean.setSecurityIdentity(securityIdentity);
                break;

            case SECURITY_ROLE_REF:
                SecurityRoleRefsMetaData securityRoleRefs = bean.getSecurityRoleRefs();
                if (securityRoleRefs == null) {
                    securityRoleRefs = new SecurityRoleRefsMetaData();
                    bean.setSecurityRoleRefs(securityRoleRefs);
                }
                SecurityRoleRefMetaData securityRoleRef = SecurityRoleRefMetaDataParser.parse(reader, propertyReplacer);
                securityRoleRefs.add(securityRoleRef);
                break;

            default:
                super.processElement(bean, reader, propertyReplacer);
                break;
        }
    }
}
