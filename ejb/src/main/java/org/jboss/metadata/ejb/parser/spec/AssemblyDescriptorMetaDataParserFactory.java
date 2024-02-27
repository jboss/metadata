/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.ejb.parser.spec;

import org.jboss.metadata.ejb.spec.EjbJarVersion;

/**
 * User: jpai
 */
class AssemblyDescriptorMetaDataParserFactory {
    static AssemblyDescriptorMetaDataParser getParser(EjbJarVersion ejbJarVersion) {
        if (ejbJarVersion == null) {
            throw new IllegalArgumentException("ejb-jar version cannot be null");
        }
        switch (ejbJarVersion) {
            case EJB_1_1:
            case EJB_2_0:
                // TODO: Parser not yet implemented for EJB 1.x and EJB 2.x versions, fallback to generic
                return new AssemblyDescriptorMetaDataParser();
            case EJB_2_1:
                return new AssemblyDescriptor21MetaDataParser();
            case EJB_3_0:
            case EJB_3_1:
            case EJB_3_2:
            case EJB_4_0:
                return new AssemblyDescriptor30MetaDataParser();
            default:
                throw new IllegalArgumentException("No parser available for ejb-jar version: " + ejbJarVersion.name());
        }
    }
}
