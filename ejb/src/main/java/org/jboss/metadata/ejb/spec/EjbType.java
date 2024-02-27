/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.spec;

/**
 * EJB 3.1 FR 2.4 2.4 Session, Entity, and Message-Driven Objects
 * The Enterprise JavaBeans architecture defines the following types of enterprise bean objects:
 * - A session object.
 * - A message-driven object.
 * - An entity object.
 *
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
public enum EjbType {
    ENTITY,
    MESSAGE_DRIVEN,
    SESSION,
}
