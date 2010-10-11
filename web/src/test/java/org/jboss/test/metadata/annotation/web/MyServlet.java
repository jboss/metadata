package org.jboss.test.metadata.annotation.web;

import java.net.URL;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.EJBs;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import javax.xml.ws.WebServiceRef;
import javax.xml.ws.WebServiceRefs;

import org.jboss.test.metadata.annotation.ws.TestEndpoint;
import org.jboss.test.metadata.annotation.ws.TestEndpointService;

@EJBs(
      value={
            @EJB(name="ejb/local1", beanInterface=MyStatelessLocal.class,
                  beanName="MyLocalSession1", mappedName="java:/MyLocalSession1",
                  description="A reference to MyLocalSession1"),
            @EJB(name="ejb/local2", beanInterface=MyStatelessLocal.class,
                  beanName="local.jar#MyLocalSession1", mappedName="java:/MyLocalSession2",
                  description="A reference to MyLocalSession2")
      }
)
@Resources({
   @Resource(description="url-resource-ref", name="googleHome", type=java.net.URL.class, mappedName="http://www.google.com"),
   @Resource(description="DataSource-resource-ref", name="jdbc/ds", type=DataSource.class, mappedName="java:/DefaultDS")
})
@DeclareRoles(value={"Role1", "Role2"})
@RunAs("InternalUser")
@WebServiceRefs({
   @WebServiceRef(name = "service2", value = TestEndpointService.class),
   @WebServiceRef(name = "port1", value = TestEndpointService.class, type = TestEndpoint.class)
})
@PersistenceContext(name="persistence/ABC", unitName="ABC")
public class MyServlet
{
   @Resource
   UserTransaction utx;
   @EJB
   private MyStatelessLocal injectedField;
   @PersistenceContext(unitName="../dd-web-ejbs.jar#tempdb")
   EntityManager injectedEntityManager;
   @PersistenceUnit(unitName="../dd-web-ejbs.jar#tempdb")
   EntityManagerFactory injectedEntityManagerFactory;

   @Resource(description="string-env-entry")
   private String sfield;
   private URL homePage;
   private URL googleHome;
   private Queue mailQueue;
   private double pi;
   @WebServiceRef
   private TestEndpointService service;
   @WebServiceRef
   private TestEndpoint endpoint;

   @EJB(name="overrideName")
   private MyStatelessLocal injectedFieldWithOverridenName;

   public URL getHomePage()
   {
      return homePage;
   }
   @Resource(description="url-resource-ref2")
   public void setHomePage(URL homePage)
   {
      this.homePage = homePage;
   }

   public Queue getMailQueue()
   {
      return mailQueue;
   }
   @Resource(description="message-destination-ref")
   public void setMailQueue(Queue mailQueue)
   {
      this.mailQueue = mailQueue;
   }

   public double getPi()
   {
      return pi;
   }
   @Resource(description="pi-env-entry", mappedName="3.14159")
   public void setPi(double pi)
   {
      this.pi = pi;
   }

   @PostConstruct
   public void setUp()
   {
      
   }
   @PreDestroy
   public void tearDown()
   {
   }
   
   @WebServiceRef
   public void setAnotherWebRef(TestEndpoint anotherEndpoint)
   {
      
   }
   
   @WebServiceRef(name = "method/service")
   public void setWebRef(TestEndpoint anotherEndpoint)
   {
      
   }
}
