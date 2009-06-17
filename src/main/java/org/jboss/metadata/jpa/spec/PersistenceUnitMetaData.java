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

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.jboss.util.JBossObject;
import org.jboss.util.JBossStringBuilder;
import org.jboss.xb.annotations.JBossXmlMapEntry;
import org.jboss.xb.annotations.JBossXmlMapKeyAttribute;
import org.jboss.xb.annotations.JBossXmlMapValueAttribute;

/**
 * The persistence unit metadata.
 *
 * @author <a href="mailto:ales.justin@jboss.com">Ales Justin</a>
 */
@XmlType(propOrder={"description", "provider", "jtaDataSource", "nonJtaDataSource", "mappingFiles", "jarFiles", "classes", "excludeUnlistedClasses", "properties"})
public class PersistenceUnitMetaData extends JBossObject implements Serializable
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

   public String getDescription()
   {
      return description;
   }

   @XmlElement
   public void setDescription(String description)
   {
      this.description = description;
   }

   public String getProvider()
   {
      return provider;
   }

   @XmlElement
   public void setProvider(String provider)
   {
      this.provider = provider;
   }

   public String getJtaDataSource()
   {
      return jtaDataSource;
   }

   @XmlElement(name = "jta-data-source")
   public void setJtaDataSource(String jtaDataSource)
   {
      this.jtaDataSource = jtaDataSource;
   }

   public String getNonJtaDataSource()
   {
      return nonJtaDataSource;
   }

   @XmlElement(name = "non-jta-data-source")
   public void setNonJtaDataSource(String nonJtaDataSource)
   {
      this.nonJtaDataSource = nonJtaDataSource;
   }

   public Set<String> getMappingFiles()
   {
      return mappingFiles;
   }

   @XmlElement(name = "mapping-file")
   public void setMappingFiles(Set<String> mappingFiles)
   {
      this.mappingFiles = mappingFiles;
   }

   public Set<String> getJarFiles()
   {
      return jarFiles;
   }

   @XmlElement(name = "jar-file")
   public void setJarFiles(Set<String> jarFiles)
   {
      this.jarFiles = jarFiles;
   }

   public Set<String> getClasses()
   {
      return classes;
   }

   @XmlElement(name = "class")
   public void setClasses(Set<String> classes)
   {
      this.classes = classes;
   }

   public boolean isExcludeUnlistedClasses()
   {
      return excludeUnlistedClasses;
   }

   @XmlElement(name = "exclude-unlisted-classes")
   public void setExcludeUnlistedClasses(boolean excludeUnlistedClasses)
   {
      this.excludeUnlistedClasses = excludeUnlistedClasses;
   }

   public Map<String, String> getProperties()
   {
      return properties;
   }

   @XmlElementWrapper(name="properties")
   @JBossXmlMapEntry(name="property")
   @JBossXmlMapKeyAttribute(name="name")
   @JBossXmlMapValueAttribute(name="value")
   public void setProperties(Map<String, String> properties)
   {
      this.properties = properties;
   }

   public String getName()
   {
      return name;
   }

   @XmlAttribute(required = true)
   public void setName(String name)
   {
      this.name = name;
   }

   public TransactionType getTransactionType()
   {
      return transactionType;
   }

   @XmlAttribute
   public void setTransactionType(TransactionType transactionType)
   {
      this.transactionType = transactionType;
   }

   protected void toString(JBossStringBuilder builder)
   {
      builder.append("provider=").append(provider);
      builder.append(", jta-data-source=").append(jtaDataSource);
      builder.append(", non-jta-data-source=").append(nonJtaDataSource);
      builder.append(", non-jta-data-source=").append(nonJtaDataSource);
      if (mappingFiles != null)
         builder.append(", mapping-files=").append(mappingFiles);
      if (jarFiles != null)
         builder.append(", jar-files=").append(jarFiles);
      if (classes != null)
         builder.append(", classes=").append(classes);
      builder.append(", excludeUnlistedClasses=").append(excludeUnlistedClasses);
      if (properties != null)
         builder.append(", properties=").append(properties);
      builder.append(", name=").append(name);
      builder.append(", transactionType=").append(transactionType);
   }

   public PersistenceUnitMetaData clone()
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
      return clone;
   }
}
