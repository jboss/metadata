/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.test.metadata.annotation.ejb3.ext.api;

import javax.ejb.ActivationConfigProperty;

import org.jboss.ejb3.annotation.AspectDomain;
import org.jboss.ejb3.annotation.Consumer;
import org.jboss.ejb3.annotation.Pool;
import org.jboss.ejb3.annotation.Producer;
import org.jboss.ejb3.annotation.Producers;
import org.jboss.ejb3.annotation.SecurityDomain;

/**
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */
@Consumer(name = "testConsumer",
   activationConfig = {
      @ActivationConfigProperty(propertyName="prop1", propertyValue="value1"),
      @ActivationConfigProperty(propertyName="prop2", propertyValue="value2")
   }
)
@SecurityDomain(value = "consumerSecurity",
      unauthenticatedPrincipal = "guest")
@AspectDomain("testConsumerDomain")
@Pool(maxSize = 2, timeout = 1, value = "value")
@Producers(
   value = {
         @Producer(producer = MyProducer.class, acknowledgeMode = 1, connectionFactory = "MyProducerConnectionFactory", transacted = true ),
         @Producer(producer = TestInterface.class)         
   }
)
public class MyConsumerBean
{

}

