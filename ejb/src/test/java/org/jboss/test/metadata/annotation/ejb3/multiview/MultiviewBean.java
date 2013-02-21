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
package org.jboss.test.metadata.annotation.ejb3.multiview;

import javax.ejb.Init;
import javax.ejb.RemoteHome;
import javax.ejb.Stateful;

/**
 * MultiviewBean provides both an EJB 3 and an EJB 2.1
 * view. It is however a (EJB 3) POJO and not an EJB 2.1 bean.
 * <p/>
 * EJB 3 4.6.2
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: 73696 $
 */
@Stateful
@RemoteHome(MultiviewHome.class)
public class MultiviewBean implements Multiview3Remote {
    @Init
    public void create(String state) {

    }

    public String sayHi(String name) {
        return "Hi " + name;
    }
}
