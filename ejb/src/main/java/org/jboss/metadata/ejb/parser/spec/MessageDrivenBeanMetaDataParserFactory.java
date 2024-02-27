/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.ejb.parser.spec;

import org.jboss.metadata.ejb.spec.EjbJarVersion;

/**
 * Responsible for returning the correct parser, based on the ejb-jar version, for
 * parsing metadata out of ejb-jar.xml.
 *
 * @author <a href="mailto:istudens@redhat.com">Ivo Studensky</a>
 */
class MessageDrivenBeanMetaDataParserFactory {

    /**
     * Returns an appropriate instance of {@link org.jboss.metadata.ejb.parser.spec.AbstractMessageDrivenBeanParser} for the passed
     * {@link org.jboss.metadata.ejb.spec.EjbJarVersion ejb-jar version}
     *
     * @param ejbJarVersion The ejb-jar version
     * @return
     */
    static AbstractMessageDrivenBeanParser getParser(EjbJarVersion ejbJarVersion) {
        if (ejbJarVersion == null) {
            throw new IllegalArgumentException("ejb-jar version cannot be null. Can't return a parser");
        }
        switch (ejbJarVersion) {
            case EJB_1_1:
            case EJB_2_0:
                return new MessageDrivenBean20Parser();

            case EJB_2_1:
            case EJB_3_0:
            case EJB_3_1:
            case EJB_3_2:
            case EJB_4_0:
                return new MessageDrivenBean31Parser();

            default:
                throw new IllegalArgumentException("No parser available for ejb-jar version: " + ejbJarVersion.name());
        }
    }
}
