package org.jboss.ejb3.metamodel;

public class Ejb3PortComponent
{
    private static final long serialVersionUID = 1;

   private SessionEnterpriseBean sessionMetaData;

   private String portComponentName;
   private String portComponentURI;
   private String authMethod;
   private String transportGuarantee;
   private Boolean secureWSDLAccess;


   public Ejb3PortComponent()
   {
   }

   public Ejb3PortComponent(SessionEnterpriseBean sessionMetaData)
   {
      this.sessionMetaData = sessionMetaData;
   }

   public SessionEnterpriseBean getSessionMetaData()
   {
      return sessionMetaData;
   }

   public String getPortComponentName()
   {
      return portComponentName;
   }

   public void setPortComponentName(String portComponentName)
   {
      this.portComponentName = portComponentName;
   }

   public String getPortComponentURI()
   {
      return portComponentURI;
   }

   public void setPortComponentURI(String portComponentURI)
   {
      this.portComponentURI = portComponentURI;
   }

   public String getURLPattern()
   {
      String pattern = "/*";
      if (portComponentURI != null)
         pattern = portComponentURI;

      return pattern;
   }

   public String getAuthMethod()
   {
      return authMethod;
   }

   public void setAuthMethod(String authMethod)
   {
      this.authMethod = authMethod;
   }

   public String getTransportGuarantee()
   {
      return transportGuarantee;
   }

   public void setTransportGuarantee(String transportGuarantee)
   {
      this.transportGuarantee = transportGuarantee;
   }

   public Boolean getSecureWSDLAccess()
   {
      return secureWSDLAccess;
   }

   public void setSecureWSDLAccess(Boolean secureWSDLAccess)
   {
      this.secureWSDLAccess = secureWSDLAccess;
   }
}
