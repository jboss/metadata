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

import org.jboss.metadata.ejb.spec.AroundInvokeMetaData;
import org.jboss.metadata.ejb.spec.AroundInvokesMetaData;
import org.jboss.metadata.ejb.spec.AroundTimeoutMetaData;
import org.jboss.metadata.ejb.spec.AroundTimeoutsMetaData;
import org.jboss.metadata.ejb.spec.InterceptorMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.LifecycleCallbackMetaData;
import org.jboss.metadata.javaee.spec.LifecycleCallbacksMetaData;
import org.jboss.metadata.javaee.support.IdMetaData;
import org.jboss.metadata.parser.ee.EnvironmentRefsGroupMetaDataParser;
import org.jboss.metadata.parser.ee.LifecycleCallbackMetaDataParser;
import org.jboss.metadata.property.PropertyReplacer;

import static org.jboss.metadata.ejb.parser.spec.AttributeProcessorHelper.processAttributes;

/**
 * Parses the &lt;interceptor&gt; element in a ejb-jar.xml and creates metadata out of it.
 * <p/>
 * <p/>
 * Author: Jaikiran Pai
 */
public class InterceptorMetaDataParser extends AbstractWithDescriptionsParser<InterceptorMetaData> {
    private static final AttributeProcessor<IdMetaData> ATTRIBUTE_PROCESSOR = new IdMetaDataAttributeProcessor<IdMetaData>(UnexpectedAttributeProcessor.instance());
    public static final InterceptorMetaDataParser INSTANCE = new InterceptorMetaDataParser();


    /**
     * Parses and creates InterceptorMetaData of the interceptor element
     *
     * @param reader
     * @return
     * @throws XMLStreamException
     */
    @Override
    public InterceptorMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        InterceptorMetaData interceptor = new InterceptorMetaData();
        processAttributes(interceptor, reader, ATTRIBUTE_PROCESSOR);
        this.processElements(interceptor, reader, propertyReplacer);
        return interceptor;
    }

    /**
     * Parses the child elements of &lt;interceptor&gt; element and updates the passed {@link InterceptorMetaData}
     * accordingly.
     *
     * @param interceptor
     * @param reader
     * @throws XMLStreamException
     */
    @Override
    protected void processElement(InterceptorMetaData interceptor, XMLStreamReader reader, final PropertyReplacer propertyReplacer) throws XMLStreamException {
        // get the element to process
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        // get the jndi environment ref group of this interceptor
        EnvironmentRefsGroupMetaData jndiEnvRefGroup = interceptor.getJndiEnvironmentRefsGroup();
        // create and set, if absent
        if (jndiEnvRefGroup == null) {
            jndiEnvRefGroup = new EnvironmentRefsGroupMetaData();
            interceptor.setJndiEnvironmentRefsGroup(jndiEnvRefGroup);
        }
        // parse any jndi ref group elements
        if (EnvironmentRefsGroupMetaDataParser.parse(reader, jndiEnvRefGroup, propertyReplacer)) {
            // it was jndi ref group element which was parsed successfully, so nothing more to do
            // than just return
            return;
        }

        switch (ejbJarElement) {
            case INTERCEPTOR_CLASS:
                String interceptorClass = getElementText(reader, propertyReplacer);
                interceptor.setInterceptorClass(interceptorClass);
                return;

            case AROUND_INVOKE:
                AroundInvokesMetaData aroundInvokes = interceptor.getAroundInvokes();
                if (aroundInvokes == null) {
                    aroundInvokes = new AroundInvokesMetaData();
                    interceptor.setAroundInvokes(aroundInvokes);
                }
                AroundInvokeMetaData aroundInvoke = AroundInvokeMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                aroundInvokes.add(aroundInvoke);
                return;

            case AROUND_TIMEOUT:
                AroundTimeoutsMetaData aroundTimeouts = interceptor.getAroundTimeouts();
                if (aroundTimeouts == null) {
                    aroundTimeouts = new AroundTimeoutsMetaData();
                    interceptor.setAroundTimeouts(aroundTimeouts);
                }
                AroundTimeoutMetaData aroundTimeout = AroundTimeoutMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                aroundTimeouts.add(aroundTimeout);
                return;

            case POST_ACTIVATE:
                LifecycleCallbacksMetaData postActivates = interceptor.getPostActivates();
                if (postActivates == null) {
                    postActivates = new LifecycleCallbacksMetaData();
                    interceptor.setPostActivates(postActivates);
                }
                LifecycleCallbackMetaData postActivate = LifecycleCallbackMetaDataParser.parse(reader, propertyReplacer);
                postActivates.add(postActivate);
                return;

            case PRE_PASSIVATE:
                LifecycleCallbacksMetaData prePassivates = interceptor.getPrePassivates();
                if (prePassivates == null) {
                    prePassivates = new LifecycleCallbacksMetaData();
                    interceptor.setPrePassivates(prePassivates);
                }
                LifecycleCallbackMetaData prePassivate = LifecycleCallbackMetaDataParser.parse(reader, propertyReplacer);
                prePassivates.add(prePassivate);
                return;


            default:
                super.processElement(interceptor, reader, propertyReplacer);
        }
    }
}
