/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.metadata.javaee.spec;

import javax.persistence.PersistenceContextType;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * PersistenceContextType enum type adapter
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 67164 $
 */
public class PersistenceContextTypeAdapter extends XmlAdapter<String, PersistenceContextType> {

    @Override
    public String marshal(PersistenceContextType type) throws Exception {
        String stype = null;
        switch (type) {
            case EXTENDED:
                stype = "Extended";
                break;
            case TRANSACTION:
                stype = "Transaction";
                break;
        }
        return stype;
    }

    @Override
    public PersistenceContextType unmarshal(String string) throws Exception {
        string = string.toUpperCase();
        return PersistenceContextType.valueOf(string);
    }

}
