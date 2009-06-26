/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.ejb3.metamodel;

import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;

/**
 * @author <a href="mailto:bdecoste@jboss.com">William DeCoste</a>
 * @version <tt>$Revision: 75470 $</tt>
 */
public class Consumer extends EnterpriseBean
{
   private static final Logger log = Logger.getLogger(Consumer.class);
   
   private String destination = null;
   private String destinationType = null;
   private CurrentMessage currentMessage = null;
   private MessageProperties messageProperties = null;
   private List producers = new ArrayList();
   private List localProducers = new ArrayList();
   
   public String getDestination()
   {
      return destination;
   }
   
   public void setDestination(String destination)
   {
      this.destination = destination;
   }
   
   public String getDestinationType()
   {
      return destinationType;
   }
   
   public void setDestinationType(String destinationType)
   {
      this.destinationType = destinationType;
   }
   
   public CurrentMessage getCurrentMessage()
   {
      return currentMessage;
   }
   
   public void setCurrentMessage(CurrentMessage currentMessage)
   {
      this.currentMessage = currentMessage;
   }
   
   public MessageProperties getMessageProperties()
   {
      return messageProperties;
   }
   
   public void setMessageProperties(MessageProperties messageProperties)
   {
      this.messageProperties = messageProperties;
   }
   
   public List getProducers()
   {
      return producers;
   }
   
   public void addProducer(Producer producer)
   {
      producers.add(producer);
   }
   
   public List getLocalProducers()
   {
      return localProducers;
   }
   
   public void addLocalProducer(Producer producer)
   {
      localProducers.add(producer);
   }

   public String toString()
   {
      StringBuffer sb = new StringBuffer(100);
      sb.append("[Consumer:");
      sb.append(super.toString());
      sb.append(", destination=" + destination);
      sb.append(", destinationType=" + destinationType);
      sb.append(']');
      return sb.toString();
   }
}
