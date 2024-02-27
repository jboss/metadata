/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.parser.spec;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.AbstractEnterpriseBeanMetaData;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.Environment;
import org.jboss.metadata.javaee.spec.EnvironmentRefsGroupMetaData;
import org.jboss.metadata.parser.ee.DescriptionGroupMetaDataParser;
import org.jboss.metadata.parser.ee.EnvironmentRefsGroupMetaDataParser;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public abstract class EnterpriseBeanMetaDataParser<MD extends AbstractEnterpriseBeanMetaData> extends AbstractIdMetaDataParser<MD> {
    @Override
    protected void processElement(MD metaData, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        // Handle the description group elements
        DescriptionGroupMetaData descriptionGroup = new DescriptionGroupMetaData();
        if (DescriptionGroupMetaDataParser.parse(reader, descriptionGroup)) {
            if (metaData.getDescriptionGroup() == null) {
                metaData.setDescriptionGroup(descriptionGroup);
            }
            return;
        }

        // Handle jndi environment ref group
        // get the jndi environment ref group of this bean
        Environment jndiEnvRefGroup = metaData.getJndiEnvironmentRefsGroup();
        // create and set, if absent
        if (jndiEnvRefGroup == null) {
            jndiEnvRefGroup = new EnvironmentRefsGroupMetaData();
            metaData.setJndiEnvironmentRefsGroup(jndiEnvRefGroup);
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

        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case EJB_CLASS:
                metaData.setEjbClass(getElementText(reader, propertyReplacer));
                return;
            case EJB_NAME:
                metaData.setEjbName(getElementText(reader, propertyReplacer));
                return;
            case MAPPED_NAME:
                metaData.setMappedName(getElementText(reader, propertyReplacer));
                return;
            default:
                super.processElement(metaData, reader, propertyReplacer);
        }
    }
}
