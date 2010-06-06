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

import org.jboss.metadata.ejb.util.ChildrenList;
import org.jboss.util.JBossObject;
import org.jboss.util.JBossStringBuilder;
import org.jboss.xb.annotations.JBossXmlSchema;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * The persistence metadata.
 * 
 * @author <a href="mailto:ales.justin@jboss.com">Ales Justin</a>
 */
@XmlRootElement(name="persistence", namespace=PersistenceMetaDataConstants.PERSISTENCE_NS)
@JBossXmlSchema(namespace=PersistenceMetaDataConstants.PERSISTENCE_NS, elementFormDefault= XmlNsForm.QUALIFIED)
@XmlType(
      name="persistenceType",
      namespace=PersistenceMetaDataConstants.PERSISTENCE_NS,
      propOrder={"persistenceUnits"}
)
public class PersistenceMetaData extends JBossObject implements Serializable
{
   private String version;
   private ChildrenList<PersistenceUnitMetaData, PersistenceMetaData> persistenceUnits = new ChildrenList<PersistenceUnitMetaData, PersistenceMetaData>(this, PersistenceUnitMetaData.adapter);

   public String getVersion()
   {
      return version;
   }

   @XmlAttribute(required = true)
   public void setVersion(String version)
   {
      this.version = version;
   }

   @XmlElement(name = "persistence-unit")
   public List<PersistenceUnitMetaData> getPersistenceUnits()
   {
      return persistenceUnits;
   }
   
   public void setPersistenceUnits(List<PersistenceUnitMetaData> persistenceUnits)
   {
      this.persistenceUnits.addAll(persistenceUnits);
   }

   protected void toString(JBossStringBuilder builder)
   {
      builder.append("version=").append(version);
      if (persistenceUnits != null && persistenceUnits.isEmpty() == false)
         builder.append(", persistence units=").append(persistenceUnits);
   }

   public PersistenceMetaData clone()
   {
      PersistenceMetaData clone = (PersistenceMetaData)super.clone();
      clone.persistenceUnits = new ChildrenList<PersistenceUnitMetaData, PersistenceMetaData>(clone, PersistenceUnitMetaData.adapter);
      for (PersistenceUnitMetaData unit : persistenceUnits)
         clone.persistenceUnits.add(unit.clone());
      return clone;
   }
}
