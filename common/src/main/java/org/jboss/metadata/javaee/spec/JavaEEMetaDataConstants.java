/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
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
package org.jboss.metadata.javaee.spec;

/**
 * Constants.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Eduardo Martins
 * @version $Revision: 8.0 $
 */
public interface JavaEEMetaDataConstants {
    /**
     * The javaee namespace
     */
    String JAVAEE_NS = "http://java.sun.com/xml/ns/javaee";
    String JAVAEE_7_NS = "http://xmlns.jcp.org/xml/ns/javaee";
    /**
     * The j2ee namespace
     */
    String J2EE_NS = "http://java.sun.com/xml/ns/j2ee";

    /**
     * The j2ee 1.3 public Ids
     */
    String J2EE_13_APP = "-//Sun Microsystems, Inc.//DTD J2EE Application 1.3//EN";
    String J2EE_13_CLIENT = "-//Sun Microsystems, Inc.//DTD J2EE Application Client 1.3//EN";
    String J2EE_13_EJB = "-//Sun Microsystems, Inc.//DTD Enterprise JavaBeans 2.0//EN";
    String J2EE_13_WEB = "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN";
    String J2EE_13_TLD = "-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.2//EN";

    /**
     * The jboss javaee namespaces
     */
    String JBOSS_NS = "http://www.jboss.com/xml/ns/javaee";

    /**
     * The jbosscmp-jdbc namespace
     */
    String JBOSS_CMP2X_NS = "http://www.jboss.com/xml/ns/javaee/cmp2x";

    String XSD_NS = "http://www.w3.org/2001/XMLSchema";
}
