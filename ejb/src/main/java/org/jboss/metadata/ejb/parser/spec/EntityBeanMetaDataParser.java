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

import org.jboss.metadata.ejb.spec.*;
import org.jboss.metadata.javaee.spec.*;
import org.jboss.metadata.parser.ee.DescriptionGroupMetaDataParser;
import org.jboss.metadata.parser.ee.EnvironmentRefsGroupMetaDataParser;
import org.jboss.metadata.parser.ee.SecurityRoleRefMetaDataParser;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import static org.jboss.metadata.ejb.parser.spec.AttributeProcessorHelper.processAttributes;

/**
 * Parses and creates metadata out of the &lt;entity&gt; element in the ejb-jar.xml
 *
 * @author Stuart Douglas
 */
public class EntityBeanMetaDataParser extends AbstractIdMetaDataParser<EntityBeanMetaData>
{

   /**
    * Creates and returns {@link org.jboss.metadata.ejb.spec.EntityBeanMetaData} after parsing the entity element.
    *
    * @param reader
    * @return
    * @throws javax.xml.stream.XMLStreamException
    *
    */
   @Override
   public EntityBeanMetaData parse(XMLStreamReader reader) throws XMLStreamException
   {
      EntityBeanMetaData bean = new EntityBeanMetaData();

      // Look at the id attribute
      final int count = reader.getAttributeCount();
      for (int i = 0; i < count; i++)
      {
         if (attributeHasNamespace(reader, i))
         {
            continue;
         }
         final EjbJarAttribute ejbJarVersionAttribute = EjbJarAttribute.forName(reader.getAttributeLocalName(i));
         if (ejbJarVersionAttribute == EjbJarAttribute.ID)
         {
            bean.setId(reader.getAttributeValue(i));
         }
      }

      processAttributes(bean, reader, this);
      this.processElements(bean, reader);
      // return the metadata created out of parsing
      return bean;
   }

   @Override
   protected void processElement(EntityBeanMetaData beanMetaData, XMLStreamReader reader) throws XMLStreamException
   {

      // Handle the description group elements
      DescriptionGroupMetaData descriptionGroup = new DescriptionGroupMetaData();
      if (DescriptionGroupMetaDataParser.parse(reader, descriptionGroup))
      {
         if (beanMetaData.getDescriptionGroup() == null)
         {
            beanMetaData.setDescriptionGroup(descriptionGroup);
         }
         return;
      }

      // Handle jndi environment ref group
      // get the jndi environment ref group of this bean
      Environment jndiEnvRefGroup = beanMetaData.getJndiEnvironmentRefsGroup();
      // create and set, if absent
      if (jndiEnvRefGroup == null)
      {
         jndiEnvRefGroup = new EnvironmentRefsGroupMetaData();
         beanMetaData.setJndiEnvironmentRefsGroup(jndiEnvRefGroup);
      }
      // Not too good!
      if (jndiEnvRefGroup instanceof EnvironmentRefsGroupMetaData)
      {
         // parse any jndi ref group elements
         if (EnvironmentRefsGroupMetaDataParser.parse(reader, (EnvironmentRefsGroupMetaData) jndiEnvRefGroup))
         {
            // it was jndi ref group element which was parsed successfully, so nothing more to do
            // than just return
            return;
         }
      }

      // get the element to process
      final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
      switch (ejbJarElement)
      {


         case EJB_NAME:
            beanMetaData.setEjbName(getElementText(reader));
            return;

         case MAPPED_NAME:
            beanMetaData.setMappedName(getElementText(reader));
            return;

         case HOME:
            beanMetaData.setHome(getElementText(reader));
            return;

         case REMOTE:
            beanMetaData.setRemote(getElementText(reader));
            return;

         case LOCAL_HOME:
            beanMetaData.setLocalHome(getElementText(reader));
            return;

         case LOCAL:
            beanMetaData.setLocal(getElementText(reader));
            return;

         case EJB_CLASS:
            beanMetaData.setEjbClass(getElementText(reader));
            return;

         case PERSISTENCE_TYPE:
            PersistenceType persistenceType = this.processPersistenceType(getElementText(reader));
            if (persistenceType == null)
            {
               throw unexpectedValue(reader, new Exception("Unexpected value: " + persistenceType + " for persistence-type"));
            } else
            {
               beanMetaData.setPersistenceType(persistenceType);
            }
            return;

         case PRIM_KEY_CLASS:
            beanMetaData.setPrimKeyClass(getElementText(reader));
            return;

         case REENTRANT:
            beanMetaData.setReentrant(Boolean.parseBoolean(getElementText(reader)));
            return;

         case CMP_VERSION:
            beanMetaData.setCmpVersion(getElementText(reader));
            return;

         case ABSTRACT_SCHEMA_NAME:
            beanMetaData.setAbstractSchemaName(getElementText(reader));
            return;


         case CMP_FIELD:
            CMPFieldMetaData field = CmpFieldMetaDataParser.INSTANCE.parse(reader);
            CMPFieldsMetaData fields = beanMetaData.getCmpFields();
            if (fields == null)
            {
               beanMetaData.setCmpFields(fields = new CMPFieldsMetaData());
            }
            fields.add(field);
            return;


         case PRIMKEY_FIELD:
            beanMetaData.setPrimKeyField(getElementText(reader));
            return;

         case SECURITY_ROLE_REF:
            SecurityRoleRefsMetaData securityRoleRefs = beanMetaData.getSecurityRoleRefs();
            if (securityRoleRefs == null)
            {
               securityRoleRefs = new SecurityRoleRefsMetaData();
               beanMetaData.setSecurityRoleRefs(securityRoleRefs);
            }
            SecurityRoleRefMetaData securityRoleRef = SecurityRoleRefMetaDataParser.parse(reader);
            securityRoleRefs.add(securityRoleRef);
            return;

         case SECURITY_IDENTITY:
            final SecurityIdentityMetaData securityIdentity = SecurityIdentityParser.INSTANCE.parse(reader);
            beanMetaData.setSecurityIdentity(securityIdentity);
            return;

         case QUERY:
            QueryMetaData query = QueryMetaDataParser.INSTANCE.parse(reader);
            QueriesMetaData queries = beanMetaData.getQueries();
            if (queries == null)
            {
               beanMetaData.setQueries(queries = new QueriesMetaData());
            }
            queries.add(query);
            return;

         default:
            throw unexpectedElement(reader);
      }
   }


   protected PersistenceType processPersistenceType(String persistenceType)
   {
      if (persistenceType.equals("Container"))
      {
         return PersistenceType.Container;
      }

      if (persistenceType.equals("Bean"))
      {
         return PersistenceType.Bean;
      }
      return null;
   }
}
