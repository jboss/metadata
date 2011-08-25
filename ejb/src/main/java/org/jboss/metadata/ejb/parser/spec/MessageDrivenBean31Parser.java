/*
 * JBoss, Home of Professional Open Source.
 * Copyright (c) 2011, Red Hat, Inc., and individual contributors
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

import org.jboss.metadata.ejb.spec.*;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public class MessageDrivenBean31Parser extends AbstractMessageDrivenBeanParser<MessageDrivenBean31MetaData>
{
   @Override
   public MessageDrivenBean31MetaData parse(XMLStreamReader reader) throws XMLStreamException
   {
      MessageDrivenBean31MetaData bean = new MessageDrivenBean31MetaData();
      processElements(bean, reader);
      return bean;
   }

   @Override
   protected void processElement(MessageDrivenBean31MetaData bean, XMLStreamReader reader) throws XMLStreamException
   {
      final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
      switch (ejbJarElement)
      {
         case AROUND_TIMEOUT:
            AroundTimeoutsMetaData aroundTimeouts = bean.getAroundTimeouts();
            if (aroundTimeouts == null)
            {
               aroundTimeouts = new AroundTimeoutsMetaData();
               bean.setAroundTimeouts(aroundTimeouts);
            }
            AroundTimeoutMetaData aroundInvoke = AroundTimeoutMetaDataParser.INSTANCE.parse(reader);
            aroundTimeouts.add(aroundInvoke);
            break;

         case TIMER:
            List<TimerMetaData> timers = bean.getTimers();
            if (timers == null)
            {
               timers = new ArrayList<TimerMetaData>();
               bean.setTimers(timers);
            }
            TimerMetaData timerMetaData = TimerMetaDataParser.INSTANCE.parse(reader);
            timers.add(timerMetaData);
            return;

         default:
            super.processElement(bean, reader);
            break;
      }
   }
}
