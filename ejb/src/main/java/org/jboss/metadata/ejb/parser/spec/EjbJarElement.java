/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
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

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration of all XML elements that are allowed in a ejb-jar.xml
 * <p/>
 * Note that this enumeration contains all possible elements allowed in the ejb-jar.xml,
 * irrespective of its xsd/dtd version.
 * <p/>
 * User: Jaikiran Pai
 */
public enum EjbJarElement
{
   UNKNOWN(null),

   AFTER_BEGIN_METHOD("after-begin-method"),
   AFTER_COMPLETION_METHOD("after-completion-method"),
   AROUND_INVOKE("around-invoke"),
   AROUND_TIMEOUT("around-timeout"),
   ASSEMBLY_DESCRIPTOR("assembly-descriptor"),
   ASYNC_METHOD("async-method"),


   BEFORE_COMPLETION_METHOD("before-completion-method"),
   BUSINESS_LOCAL("business-local"),
   BUSINESS_REMOTE("business-remote"),

   CONCURRENCY_MANAGEMENT_TYPE("concurrency-management-type"),
   CONCURRENT_METHOD("concurrent-method"),

   DEPENDS_ON("depends-on"),
   

   EJB_CLASS("ejb-class"),
   EJB_CLIENT_JAR("ejb-client-jar"),
   EJB_NAME("ejb-name"),
   ENTERPRISE_BEANS("enterprise-beans"),
   ENTITY("entity"),

   HOME("home"),

   INIT_METHOD("init-method"),
   INIT_ON_STARTUP("init-on-startup"),
   INTERCEPTORS("interceptors"),

   LOCAL("local"),
   LOCAL_BEAN("local-bean"),
   LOCAL_HOME("local-home"),

   MAPPED_NAME("mapped-name"),
   MESSAGE_DRIVEN("message-driven"),
   MODULE_NAME("module-name"),

   POST_ACTIVATE("post-activate"),
   PRE_PASSIVATE("pre-passivate"),
   
   RELATIONSHIPS("relationships"),
   REMOTE("remote"),
   REMOVE_METHOD("remove-method"),

   SECURITY_IDENTITY("security-identity"),
   SECURITY_ROLE_REF("security-role-ref"),
   SERVICE_ENDPOINT("service-endpoint"),
   SESSION("session"),
   SESSION_TYPE("session-type"),
   STATEFUL_TIMEOUT("stateful-timeout"),

   TIMEOUT_METHOD("timeout-method"),
   TIMER("timer"),
   TRANSACTION_TYPE("transaction-type"),

   ;

   /**
    * Name of the element
    */
   private final String elementName;

   /**
    * Elements map
    */
   private static final Map<String, EjbJarElement> ELEMENT_MAP;

   static
   {
      final Map<String, EjbJarElement> map = new HashMap<String, EjbJarElement>();
      for (EjbJarElement element : values())
      {
         final String name = element.getLocalName();
         if (name != null)
         {
            map.put(name, element);
         }
      }
      ELEMENT_MAP = map;
   }


   /**
    * @param name
    */
   EjbJarElement(final String name)
   {
      this.elementName = name;
   }

   /**
    * Get the local name of this element.
    *
    * @return the local name
    */
   public String getLocalName()
   {
      return this.elementName;
   }

   /**
    * Returns the {@link EjbJarElement} corresponding to the passed <code>elementName</code>
    * <p/>
    * If no such element exists then {@link EjbJarElement#UNKNOWN} is returned.
    *
    * @param elementName
    * @return
    */
   public static EjbJarElement forName(String elementName)
   {
      final EjbJarElement element = ELEMENT_MAP.get(elementName);
      return element == null ? UNKNOWN : element;
   }
}
