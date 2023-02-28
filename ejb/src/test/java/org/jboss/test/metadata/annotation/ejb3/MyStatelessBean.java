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
package org.jboss.test.metadata.annotation.ejb3;

import java.net.URL;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import jakarta.annotation.Resources;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.annotation.security.RunAs;
import jakarta.ejb.EJB;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateless;
import jakarta.ejb.Timeout;
import jakarta.ejb.Timer;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.ExcludeClassInterceptors;
import jakarta.interceptor.ExcludeDefaultInterceptors;
import jakarta.interceptor.Interceptors;
import jakarta.interceptor.InvocationContext;
import jakarta.jms.Queue;
import javax.sql.DataSource;

/**
 * Comment
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 81533 $
 */
@SuppressWarnings("unused")
@Stateless
@TransactionAttribute(TransactionAttributeType.NEVER)
@Resources({
        @Resource(description = "url-resource-ref", name = "googleHome", type = java.net.URL.class, mappedName = "http://www.google.com"),
        @Resource(description = "DataSource-resource-ref", name = "jdbc/ds", type = DataSource.class, mappedName = "java:/DefaultDS")
})
@DeclareRoles(value = {"Role1", "Role2"})
@RunAs("InternalUser")
@Interceptors(TestClassInterceptor.class)
@ExcludeDefaultInterceptors
public class MyStatelessBean implements MyStatelessLocal, MyStatelessRemote {
    @EJB(name = "injectedField")
    private MyStatelessLocal injectedField;
    @Resource(description = "SessionContext-resource-env-ref")
    private SessionContext context;

    @Resource(description = "string-env-entry")
    private String sfield;
    private URL homePage;
    private URL googleHome;
    private Queue mailQueue;
    private double pi;

    @EJB(name = "overrideName")
    private MyStatelessLocal injectedFieldWithOverridenName;

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void transactionAttributeMandatory() {

    }

    public URL getHomePage() {
        return homePage;
    }

    @Resource(description = "url-resource-ref2")
    public void setHomePage(URL homePage) {
        this.homePage = homePage;
    }

    public Queue getMailQueue() {
        return mailQueue;
    }

    @Resource(description = "message-destination-ref")
    public void setMailQueue(Queue mailQueue) {
        this.mailQueue = mailQueue;
    }

    public double getPi() {
        return pi;
    }

    @Resource(description = "pi-env-entry", mappedName = "3.14159")
    public void setPi(double pi) {
        this.pi = pi;
    }

    @PostConstruct
    public void setUp() {

    }

    @PreDestroy
    public void tearDown() {
    }

    @DenyAll
    public void denyAll() {
    }

    @DenyAll
    public void excluded() {
    }

    @PermitAll
    public void permitAll() {
    }

    @RolesAllowed({"AccessRole1", "AccessRole2"})
    public void rolesAllowed() {
    }

    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception {
        System.out.println("**** intercept ****" + ctx.getMethod().getName());
        return ctx.proceed();
    }

    @ExcludeClassInterceptors
    @ExcludeDefaultInterceptors
    @Interceptors(TestMethodInterceptor.class)
    public Object intercept2(InvocationContext ctx) throws Exception {
        System.out.println("**** intercept2 ****" + ctx.getMethod().getName());
        return ctx.proceed();
    }

    @Timeout
    public void timeout(Timer timer) {

    }
}
