/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
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
package org.jboss.metadata.ejb.spec;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.spec.SecurityRoleRefsMetaData;
import org.jboss.xb.annotations.JBossXmlConstants;
import org.jboss.xb.annotations.JBossXmlType;

/**
 * EntityBeanMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="entity-beanType", propOrder={"descriptionGroup", "ejbName", "mappedName", "home", "remote", "localHome", "local",
      "ejbClass", "persistenceType", "primKeyClass", "reentrant", "cmpVersion", "abstractSchemaName", "cmpFields", "primKeyField",
      "environmentRefsGroup", "securityRoleRefs", "securityIdentity", "queries"})
@JBossXmlType(modelGroup=JBossXmlConstants.MODEL_GROUP_UNORDERED_SEQUENCE)
public class EntityBeanMetaData extends EnterpriseBeanMetaData
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 1538890391418490043L;
   
   /** The home interface */
   private String home;

   /** The remote interface */
   private String remote;

   /** The local home */
   private String localHome;

   /** The local */
   private String local;
   
   /** The persistence type */
   private PersistenceType persistenceType;
   
   /** The primary key class */
   private String primKeyClass;
   
   /** The reentrant */
   private boolean reentrant;

   /** The cmp version */
   private String cmpVersion;

   /** The abstract schema name */
   private String abstractSchemaName;
   
   /** The cmp fields */
   private CMPFieldsMetaData cmpFields;
   
   /** The primary key field */
   private String primKeyField;
   
   /** The security role refs */
   private SecurityRoleRefsMetaData securityRoleRefs;
   
   /** The queries */
   private QueriesMetaData queries;

   /**
    * Create a new EntityBeanMetaData.
    */
   public EntityBeanMetaData()
   {
      // For serialization
   }

   @Override
   public boolean isEntity()
   {
      return true;
   }

   /**
    * Get the home.
    * 
    * @return the home.
    */
   public String getHome()
   {
      return home;
   }

   /**
    * Set the home.
    * 
    * @param home the home.
    * @throws IllegalArgumentException for a null home
    */
   public void setHome(String home)
   {
      if (home == null)
         throw new IllegalArgumentException("Null home");
      this.home = home;
   }

   /**
    * Get the remote.
    * 
    * @return the remote.
    */
   public String getRemote()
   {
      return remote;
   }

   /**
    * Set the remote.
    * 
    * @param remote the remote.
    * @throws IllegalArgumentException for a null remote
    */
   public void setRemote(String remote)
   {
      if (remote == null)
         throw new IllegalArgumentException("Null remote");
      this.remote = remote;
   }

   /**
    * Get the localHome.
    * 
    * @return the localHome.
    */
   public String getLocalHome()
   {
      return localHome;
   }

   /**
    * Set the localHome.
    * 
    * @param localHome the localHome.
    * @throws IllegalArgumentException for a null localHome
    */
   public void setLocalHome(String localHome)
   {
      if (localHome == null)
         throw new IllegalArgumentException("Null localHome");
      this.localHome = localHome;
   }

   /**
    * Get the local.
    * 
    * @return the local.
    */
   public String getLocal()
   {
      return local;
   }

   /**
    * Set the local.
    * 
    * @param local the local.
    * @throws IllegalArgumentException for a null local
    */
   public void setLocal(String local)
   {
      if (local == null)
         throw new IllegalArgumentException("Null local");
      this.local = local;
   }

   /**
    * Is this container managed persistence
    * 
    * @return true for cmp
    */
   public boolean isCMP()
   {
      if (persistenceType == null)
         return true;
      return persistenceType == PersistenceType.Container;
   }

   /**
    * Is this bean managed persistence
    * 
    * @return true for bmp
    */
   public boolean isBMP()
   {
      return isCMP() == false;
   }

   /**
    * Get the persistenceType.
    * 
    * @return the persistenceType.
    */
   public PersistenceType getPersistenceType()
   {
      return persistenceType;
   }

   /**
    * Set the persistenceType.
    * 
    * @param persistenceType the persistenceType.
    * @throws IllegalArgumentException for a null persistenceType
    */
   public void setPersistenceType(PersistenceType persistenceType)
   {
      if (persistenceType == null)
         throw new IllegalArgumentException("Null persistenceType");
      this.persistenceType = persistenceType;
   }

   /**
    * Get the primKeyClass.
    * 
    * @return the primKeyClass.
    */
   public String getPrimKeyClass()
   {
      return primKeyClass;
   }

   /**
    * Set the primKeyClass.
    * 
    * @param primKeyClass the primKeyClass.
    * @throws IllegalArgumentException for a null primKeyClass
    */
   public void setPrimKeyClass(String primKeyClass)
   {
      if (primKeyClass == null)
         throw new IllegalArgumentException("Null primKeyClass");
      this.primKeyClass = primKeyClass;
   }

   /**
    * Get the reentrant.
    * 
    * @return the reentrant.
    */
   public boolean isReentrant()
   {
      return reentrant;
   }

   /**
    * Set the reentrant.
    * 
    * @param reentrant the reentrant.
    */
   public void setReentrant(boolean reentrant)
   {
      this.reentrant = reentrant;
   }

   /**
    * Whether it is CMP1x
    * 
    * @return true for cmp1x
    */
   public boolean isCMP1x()
   {
      if(cmpVersion == null)
      {
         if(getEjbJarMetaData().isEJB2x() || getEjbJarMetaData().isEJB3x())
            return false;
         else
            return true;
      }
      return "1.x".equals(cmpVersion);
   }
   
   /**
    * Get the cmpVersion.
    * 
    * @return the cmpVersion.
    */
   public String getCmpVersion()
   {
      return cmpVersion;
   }

   /**
    * Set the cmpVersion.
    * 
    * @param cmpVersion the cmpVersion.
    * @throws IllegalArgumentException for a null cmpVersion
    */
   public void setCmpVersion(String cmpVersion)
   {
      if (cmpVersion == null)
         throw new IllegalArgumentException("Null cmpVersion");
      this.cmpVersion = cmpVersion;
   }

   /**
    * Get the abstractSchemaName.
    * 
    * @return the abstractSchemaName.
    */
   public String getAbstractSchemaName()
   {
      return abstractSchemaName;
   }

   /**
    * Set the abstractSchemaName.
    * 
    * @param abstractSchemaName the abstractSchemaName.
    * @throws IllegalArgumentException for a null abstractSchemaName
    */
   public void setAbstractSchemaName(String abstractSchemaName)
   {
      if (abstractSchemaName == null)
         throw new IllegalArgumentException("Null abstractSchemaName");
      this.abstractSchemaName = abstractSchemaName;
   }

   /**
    * Get the primKeyField.
    * 
    * @return the primKeyField.
    */
   public String getPrimKeyField()
   {
      return primKeyField;
   }

   /**
    * Set the primKeyField.
    * 
    * @param primKeyField the primKeyField.
    * @throws IllegalArgumentException for a null primKeyField
    */
   @XmlElement(name="primkey-field")
   public void setPrimKeyField(String primKeyField)
   {
      if (primKeyField == null)
         throw new IllegalArgumentException("Null primKeyField");
      this.primKeyField = primKeyField;
   }

   /**
    * Get the cmpFields.
    * 
    * @return the cmpFields.
    */
   public CMPFieldsMetaData getCmpFields()
   {
      return cmpFields;
   }

   /**
    * Set the cmpFields.
    * 
    * @param cmpFields the cmpFields.
    * @throws IllegalArgumentException for a null cmpFields
    */
   @XmlElement(name="cmp-field")
   public void setCmpFields(CMPFieldsMetaData cmpFields)
   {
      if (cmpFields == null)
         throw new IllegalArgumentException("Null cmpFields");
      this.cmpFields = cmpFields;
   }

   /**
    * Get the securityRoleRefs.
    * 
    * @return the securityRoleRefs.
    */
   public SecurityRoleRefsMetaData getSecurityRoleRefs()
   {
      return securityRoleRefs;
   }

   /**
    * Set the securityRoleRefs.
    * 
    * @param securityRoleRefs the securityRoleRefs.
    * @throws IllegalArgumentException for a null securityRoleRefs
    */
   @XmlElement(name="security-role-ref")
   public void setSecurityRoleRefs(SecurityRoleRefsMetaData securityRoleRefs)
   {
      if (securityRoleRefs == null)
         throw new IllegalArgumentException("Null securityRoleRefs");
      this.securityRoleRefs = securityRoleRefs;
   }

   /**
    * Get the queries.
    * 
    * @return the queries.
    */
   public QueriesMetaData getQueries()
   {
      return queries;
   }

   /**
    * Set the queries.
    * 
    * @param queries the queries.
    * @throws IllegalArgumentException for a null queries
    */
   @XmlElement(name="query")
   public void setQueries(QueriesMetaData queries)
   {
      if (queries == null)
         throw new IllegalArgumentException("Null queries");
      this.queries = queries;
   }
}
