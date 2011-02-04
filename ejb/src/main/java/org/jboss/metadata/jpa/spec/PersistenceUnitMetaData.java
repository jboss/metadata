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
package org.jboss.metadata.jpa.spec;

import org.jboss.metadata.ejb.util.ChildClassAdapter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The persistence unit metadata.
 *
 * @author <a href="mailto:ales.justin@jboss.com">Ales Justin</a>
 */
public class PersistenceUnitMetaData implements Serializable
{
   private String description;
   private String provider;
   private String jtaDataSource;
   private String nonJtaDataSource;
   private Set<String> mappingFiles;
   private Set<String> jarFiles;
   private Set<String> classes;
   private boolean excludeUnlistedClasses;
   private Map<String, String> properties;
   private String name;
   private TransactionType transactionType;
   private SharedCacheMode sharedCacheMode;
   private ValidationMode validationMode;

   private PersistenceMetaData persistenceMetaData;

   static class Adapter implements ChildClassAdapter<PersistenceUnitMetaData, PersistenceMetaData>
   {
      @Override
      public PersistenceMetaData getParent(PersistenceUnitMetaData child)
      {
         return child.persistenceMetaData;
      }

      @Override
      public void setParent(PersistenceUnitMetaData child, PersistenceMetaData parent)
      {
         if(child.persistenceMetaData != null && parent != null)
            throw new IllegalArgumentException("Can't set parent " + parent + " already got " + child.persistenceMetaData);
         child.persistenceMetaData = parent;
      }
   }
   static Adapter adapter = new Adapter();

   public SharedCacheMode getSharedCacheMode()
   {
      return sharedCacheMode;
   }

   public void setSharedCacheMode(SharedCacheMode sharedCacheMode)
   {
      this.sharedCacheMode = sharedCacheMode;
   }

   public ValidationMode getValidationMode()
   {
      return validationMode;
   }

   public void setValidationMode(ValidationMode validationMode)
   {
      this.validationMode = validationMode;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription(String description)
   {
      this.description = description;
   }

   public String getProvider()
   {
      return provider;
   }

   public void setProvider(String provider)
   {
      this.provider = provider;
   }

   public String getJtaDataSource()
   {
      return jtaDataSource;
   }

   public void setJtaDataSource(String jtaDataSource)
   {
      this.jtaDataSource = jtaDataSource;
   }

   public String getNonJtaDataSource()
   {
      return nonJtaDataSource;
   }

   public void setNonJtaDataSource(String nonJtaDataSource)
   {
      this.nonJtaDataSource = nonJtaDataSource;
   }

   public Set<String> getMappingFiles()
   {
      return mappingFiles;
   }

   public void setMappingFiles(Set<String> mappingFiles)
   {
      this.mappingFiles = mappingFiles;
   }

   public Set<String> getJarFiles()
   {
      return jarFiles;
   }

   public void setJarFiles(Set<String> jarFiles)
   {
      this.jarFiles = jarFiles;
   }

   public Set<String> getClasses()
   {
      return classes;
   }

   public void setClasses(Set<String> classes)
   {
      this.classes = classes;
   }

   public boolean isExcludeUnlistedClasses()
   {
      return excludeUnlistedClasses;
   }

   public void setExcludeUnlistedClasses(boolean excludeUnlistedClasses)
   {
      this.excludeUnlistedClasses = excludeUnlistedClasses;
   }

   public Map<String, String> getProperties()
   {
      return properties;
   }

   public void setProperties(Map<String, String> properties)
   {
      this.properties = properties;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public TransactionType getTransactionType()
   {
      return transactionType;
   }

   public void setTransactionType(TransactionType transactionType)
   {
      this.transactionType = transactionType;
   }

   public PersistenceMetaData getPersistenceMetaData()
   {
      return persistenceMetaData;
   }

   public PersistenceUnitMetaData clone() throws CloneNotSupportedException
   {
      PersistenceUnitMetaData clone = (PersistenceUnitMetaData)super.clone();
      if (mappingFiles != null)
         clone.setMappingFiles(new HashSet<String>(mappingFiles));
      if (jarFiles != null)
         clone.setJarFiles(new HashSet<String>(jarFiles));
      if (classes != null)
         clone.setClasses(new HashSet<String>(classes));
      if (properties != null)
         clone.setProperties(new HashMap<String, String>(properties));
      // make the clone an orphan
      clone.persistenceMetaData = null;
      return clone;
   }
}
