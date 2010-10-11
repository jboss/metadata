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
package org.jboss.test.metadata.annotation.web.injection;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.persistence.PersistenceContext;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceRef;

/**
 * 
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */
public class RequestAttributeListener
{
   @EJB(name = "statefulTestBean", beanName="StatefulTestBean", beanInterface=StatefulIF.class)
   private StatefulIF statefulTestBean;

   @EJB(name = "statefulTestLocalBean", beanName="StatefulTestBean", beanInterface=StatefulLocalIF.class)
   private StatefulLocalIF statefulTestLocalBean;

   @WebServiceRef
   private StatefulIF webServiceRef;
   
   @Resource(name = "string" )
   private String string;
   
   @Resource(name = "webService")
   private Service webService;
   
   @Resource(name = "connectionFactory")
   private ConnectionFactory connectionFactory;
   
   @Resource(name = "queue")
   private Queue queue;
   
   @Resource(name = "somethingElse")
   private StatefulIF somethingElse;
   
   @PersistenceContext
   private StatefulIF persistenceContext;
}

