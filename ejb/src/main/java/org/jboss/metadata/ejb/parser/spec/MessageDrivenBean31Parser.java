/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.parser.spec;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.AbstractGenericBeanMetaData;
import org.jboss.metadata.ejb.spec.AroundTimeoutMetaData;
import org.jboss.metadata.ejb.spec.AroundTimeoutsMetaData;
import org.jboss.metadata.ejb.spec.EjbType;
import org.jboss.metadata.ejb.spec.GenericBeanMetaData;
import org.jboss.metadata.ejb.spec.TimerMetaData;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class MessageDrivenBean31Parser extends AbstractMessageDrivenBeanParser<AbstractGenericBeanMetaData> {
    @Override
    protected void processElement(AbstractGenericBeanMetaData bean, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case AROUND_TIMEOUT:
                AroundTimeoutsMetaData aroundTimeouts = bean.getAroundTimeouts();
                if (aroundTimeouts == null) {
                    aroundTimeouts = new AroundTimeoutsMetaData();
                    bean.setAroundTimeouts(aroundTimeouts);
                }
                AroundTimeoutMetaData aroundInvoke = AroundTimeoutMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                aroundTimeouts.add(aroundInvoke);
                break;

            case TIMER:
                List<TimerMetaData> timers = bean.getTimers();
                if (timers == null) {
                    timers = new ArrayList<TimerMetaData>();
                    bean.setTimers(timers);
                }
                TimerMetaData timerMetaData = TimerMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                timers.add(timerMetaData);
                return;

            default:
                super.processElement(bean, reader, propertyReplacer);
                break;
        }
    }

    @Override
    protected AbstractGenericBeanMetaData createMessageDrivenBeanMetaData() {
        return new GenericBeanMetaData(EjbType.MESSAGE_DRIVEN);
    }
}
