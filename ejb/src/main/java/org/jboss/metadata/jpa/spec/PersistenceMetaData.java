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

import java.io.Serializable;
import java.util.List;

/**
 * The persistence metadata.
 * 
 * @author <a href="mailto:ales.justin@jboss.com">Ales Justin</a>
 */
public class PersistenceMetaData implements Serializable
{
   private String version;
   private ChildrenList<PersistenceUnitMetaData, PersistenceMetaData> persistenceUnits = new ChildrenList<PersistenceUnitMetaData, PersistenceMetaData>(this, PersistenceUnitMetaData.adapter);

   public String getVersion()
   {
      return version;
   }

   public void setVersion(String version)
   {
      this.version = version;
   }

   public List<PersistenceUnitMetaData> getPersistenceUnits()
   {
      return persistenceUnits;
   }
   
   public void setPersistenceUnits(List<PersistenceUnitMetaData> persistenceUnits)
   {
      this.persistenceUnits = new ChildrenList<PersistenceUnitMetaData, PersistenceMetaData>(this, PersistenceUnitMetaData.adapter);
      this.persistenceUnits.addAll(persistenceUnits);
   }

   public PersistenceMetaData clone() throws CloneNotSupportedException
   {
      PersistenceMetaData clone = (PersistenceMetaData)super.clone();
      clone.persistenceUnits = new ChildrenList<PersistenceUnitMetaData, PersistenceMetaData>(clone, PersistenceUnitMetaData.adapter);
      for (PersistenceUnitMetaData unit : persistenceUnits)
         clone.persistenceUnits.add(unit.clone());
      return clone;
   }
}
