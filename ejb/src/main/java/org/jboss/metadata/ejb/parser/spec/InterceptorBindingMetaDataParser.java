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

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.InterceptorBindingMetaData;
import org.jboss.metadata.ejb.spec.InterceptorClassesMetaData;
import org.jboss.metadata.ejb.spec.InterceptorOrderMetaData;
import org.jboss.metadata.ejb.spec.NamedMethodMetaData;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * Author : Jaikiran Pai
 */
public class InterceptorBindingMetaDataParser extends AbstractWithDescriptionsParser<InterceptorBindingMetaData> {
    public static final InterceptorBindingMetaDataParser INSTANCE = new InterceptorBindingMetaDataParser();

    @Override
    public InterceptorBindingMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        InterceptorBindingMetaData interceptorBinding = new InterceptorBindingMetaData();
        this.processElements(interceptorBinding, reader, propertyReplacer);
        return interceptorBinding;
    }

    @Override
    protected void processElement(InterceptorBindingMetaData interceptorBinding, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case EJB_NAME:
                String ejbName = getElementText(reader, propertyReplacer);
                interceptorBinding.setEjbName(ejbName);
                return;

            case INTERCEPTOR_CLASS:
                InterceptorClassesMetaData interceptorClasses = interceptorBinding.getInterceptorClasses();
                if (interceptorClasses == null) {
                    interceptorClasses = new InterceptorClassesMetaData();
                    interceptorBinding.setInterceptorClasses(interceptorClasses);
                }
                String interceptorClass = getElementText(reader, propertyReplacer);
                interceptorClasses.add(interceptorClass);
                return;

            case EXCLUDE_DEFAULT_INTERCEPTORS:
                String excludeDefaultInterceptors = getElementText(reader, propertyReplacer);
                if (excludeDefaultInterceptors == null || excludeDefaultInterceptors.trim().isEmpty()) {
                    throw unexpectedValue(reader, new Exception("Unexpected null or empty value for <exclude-default-interceptors>"));
                }
                interceptorBinding.setExcludeDefaultInterceptors(Boolean.parseBoolean(excludeDefaultInterceptors));
                return;

            case EXCLUDE_CLASS_INTERCEPTORS:
                String excludeClassInterceptors = getElementText(reader, propertyReplacer);
                if (excludeClassInterceptors == null || excludeClassInterceptors.trim().isEmpty()) {
                    throw unexpectedValue(reader, new Exception("Unexpected null or empty value for <exclude-class-interceptors>"));
                }
                interceptorBinding.setExcludeClassInterceptors(Boolean.parseBoolean(excludeClassInterceptors));
                return;

            case METHOD:
                NamedMethodMetaData method = NamedMethodMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                interceptorBinding.setMethod(method);
                return;

            case INTERCEPTOR_ORDER:
                InterceptorOrderMetaData interceptorOrderMetaData = InterceptorOrderMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                interceptorBinding.setInterceptorOrder(interceptorOrderMetaData);
                return;
            default:
                super.processElement(interceptorBinding, reader, propertyReplacer);

        }
    }
}
