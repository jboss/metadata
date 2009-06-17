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
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.util.JBossObject;
import org.jboss.util.JBossStringBuilder;
import org.jboss.xb.annotations.JBossXmlSchema;

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
   private List<PersistenceUnitMetaData> persistenceUnits;

   public String getVersion()
   {
      return version;
   }

   @XmlAttribute(required = true)
   public void setVersion(String version)
   {
      this.version = version;
   }

   public List<PersistenceUnitMetaData> getPersistenceUnits()
   {
      return persistenceUnits;
   }

   @XmlElement(name = "persistence-unit")
   public void setPersistenceUnits(List<PersistenceUnitMetaData> persistenceUnits)
   {
      this.persistenceUnits = persistenceUnits;
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
      if (persistenceUnits != null)
      {
         List<PersistenceUnitMetaData> units = new ArrayList<PersistenceUnitMetaData>();
         for (PersistenceUnitMetaData unit : persistenceUnits)
            units.add(unit.clone());
         clone.setPersistenceUnits(units);
      }
      return clone;
   }
}
