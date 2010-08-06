/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.metadata;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.ejb.jboss.IORASContextMetaData;
import org.jboss.metadata.ejb.jboss.IORSASContextMetaData;
import org.jboss.metadata.ejb.jboss.IORSecurityConfigMetaData;
import org.jboss.metadata.ejb.jboss.IORTransportConfigMetaData;

/**
 * Describes the security configuration information for the IOR.
 *
 * @author <a href="mailto:alex@jboss.org">Alexey Loubyansky</a>
 * @author <a href="mailto:dimitris@jboss.org">Dimitris Andreadis</a>
 * @version <tt>$Revision: 57209 $</tt>
 */
public class IorSecurityConfigMetaData implements Serializable
{
   /** @since 1.7 */
   private static final long serialVersionUID = -3341898910508715334L;

   /**
    * The root element for security between the end points.
    * Optional element.
    */
   private TransportConfig transportConfig;

   /**
    * as-context (CSIv2 authentication service) is the element describing the authentication
    * mechanism that will be used to authenticate the client. If specified it will be the
    * username-password mechanism.
    * Optional element.
    */
   private AsContext asContext;

   /**
    * sas-context (related to CSIv2 security attribute service) element describes the sas-context fields.
    */
   private SasContext sasContext;

   /** Create a default security configuration.
    * TransportConfig[integrity=supported, confidentiality=supported,
    * establish-trust-in-target=supported,establish-trust-in-client=supported,
    * detect-misordering=supported, detect-replay=supported]
    * AsContext[auth-method=USERNAME_PASSWORD, realm=default, required=false]
    * SasContext[caller-propagation=NONE]
    */
   public IorSecurityConfigMetaData()
   {
      transportConfig = new TransportConfig();
      asContext = new AsContext();
      sasContext = new SasContext();
   }

   /**
    * Create a new IorSecurityConfigMetaData.
    * 
    * @param securityConfigMetaData the security config
    */
   IorSecurityConfigMetaData(IORSecurityConfigMetaData securityConfigMetaData)
   {
      if (securityConfigMetaData == null)
         throw new IllegalArgumentException("Null security config metadata");
      transportConfig = new TransportConfig(securityConfigMetaData.getTransportConfig());
      asContext = new AsContext(securityConfigMetaData.getAsContext());
      sasContext = new SasContext(securityConfigMetaData.getSasContext());
   }
   
   /**
    * Get the transport config
    * 
    * @return the transport config
    */
   public TransportConfig getTransportConfig()
   {
      return transportConfig;
   }
   
   /**
    * Set the transport config
    * 
    * @param config the transport config
    */
   public void setTransportConfig(TransportConfig config)
   {
      this.transportConfig = config;
   }

   /**
    * Get the as context
    * 
    * @return the as context
    */
   public AsContext getAsContext()
   {
      return asContext;
   }
   
   /**
    * Set the as context
    * 
    * @param context the as context
    */
   public void setAsContext(AsContext context)
   {
      this.asContext = context;
   }

   /**
    * Get the Sas context
    * 
    * @return the sas context
    */
   public SasContext getSasContext()
   {
      return sasContext;
   }
   
   /**
    * Set the sas context
    * 
    * @param context the sas context
    */
   public void setSasContext(SasContext context)
   {
      this.sasContext = context;
   }

   @Override
   public String toString()
   {
      return
         "[transport-config=" + transportConfig +
         ", as-context=" + asContext +
         ", sas-context=" + sasContext + "]";
   }

   /**
    * The root element for security between the end points
    */
   public class TransportConfig
   {
      public static final String INTEGRITY_NONE = "NONE";
      public static final String INTEGRITY_SUPPORTED = "SUPPORTED";
      public static final String INTEGRITY_REQUIRED = "REQUIRED";

      public static final String CONFIDENTIALITY_NONE = "NONE";
      public static final String CONFIDENTIALITY_SUPPORTED = "SUPPORTED";
      public static final String CONFIDENTIALITY_REQUIRED = "REQUIRED";

      public static final String DETECT_MISORDERING_NONE = "NONE";
      public static final String DETECT_MISORDERING_SUPPORTED = "SUPPORTED";
      public static final String DETECT_MISORDERING_REQUIRED = "REQUIRED";

      public static final String DETECT_REPLAY_NONE = "NONE";
      public static final String DETECT_REPLAY_SUPPORTED = "SUPPORTED";
      public static final String DETECT_REPLAY_REQUIRED = "REQUIRED";

      public static final String ESTABLISH_TRUST_IN_TARGET_NONE = "NONE";
      public static final String ESTABLISH_TRUST_IN_TARGET_SUPPORTED = "SUPPORTED";

      public static final String ESTABLISH_TRUST_IN_CLIENT_NONE = "NONE";
      public static final String ESTABLISH_TRUST_IN_CLIENT_SUPPORTED = "SUPPORTED";
      public static final String ESTABLISH_TRUST_IN_CLIENT_REQUIRED = "REQUIRED";

      /**
       * integrity element indicates if the server (target) supports integrity protected messages.
       * The valid values are NONE, SUPPORTED or REQUIRED.
       * Required element.
       */
      private final String integrity;

      /**
       * confidentiality element indicates if the server (target) supports privacy protected
       * messages. The values are NONE, SUPPORTED or REQUIRED.
       * Required element.
       */
      private final String confidentiality;

      /**
       * detect-misordering indicates if the server (target) supports detection
       * of message sequence errors. The values are NONE, SUPPORTED or REQUIRED.
       * Optional element.
       */
      private final String detectMisordering;

      /**
       * detect-replay indicates if the server (target) supports detection
       * of message replay attempts. The values are NONE, SUPPORTED or REQUIRED.
       * Optional element.
       */
      private final String detectReplay;

      /**
       * establish-trust-in-target element indicates if the target is capable of authenticating to a client.
       * The values are NONE or SUPPORTED.
       * Required element.
       */
      private final String establishTrustInTarget;

      /**
       * establish-trust-in-client element indicates if the target is capable of authenticating a client. The
       * values are NONE, SUPPORTED or REQUIRED.
       * Required element.
       */
      private final String establishTrustInClient;

      /**
       * Create a new TransportConfig.
       */
      private TransportConfig()
      {
         integrity = INTEGRITY_SUPPORTED;
         confidentiality = CONFIDENTIALITY_SUPPORTED;
         establishTrustInTarget = ESTABLISH_TRUST_IN_TARGET_SUPPORTED;
         establishTrustInClient = ESTABLISH_TRUST_IN_CLIENT_SUPPORTED;
         this.detectMisordering = DETECT_MISORDERING_SUPPORTED;
         this.detectReplay = DETECT_REPLAY_SUPPORTED;
      }

      /**
       * Create a new TransportConfig.
       * 
       * @param tranportConfig the delegate
       */
      private TransportConfig(IORTransportConfigMetaData tranportConfig)
      {
         if (tranportConfig == null)
            throw new IllegalArgumentException("Null transport config");
         integrity = tranportConfig.getIntegrity();
         confidentiality = tranportConfig.getIntegrity();
         establishTrustInClient = tranportConfig.getEstablishTrustInClient();
         establishTrustInTarget = tranportConfig.getEstablishTrustInTarget();
         detectReplay = tranportConfig.getDetectReplay();
         detectMisordering = tranportConfig.getDetectMisordering();
      }
      
      /**
       * Create a new {@code TransportConfig} with the specified properties.
       *
       * @param integrity a {@code String} representing the transport integrity level.
       * @param confidentiality a {@code String} representing the transport integrity level.
       * @param establishTrustInTarget a {@code String} that indicates if target trust must be established or not.
       * @param establishTrustInClient a {@code String} that indicates if client trust must be established or not.
       * @param detectMisordering a {@code String} representing the transport detect-misordering level.
       * @param detectReplay a {@code String} representing the transport detect-replay level.
       */
      public TransportConfig(String integrity, String confidentiality, String establishTrustInTarget,
            String establishTrustInClient, String detectMisordering, String detectReplay)
      {
         // set the transport integrity property.
         if (integrity == null)
            throw new IllegalArgumentException("Null integrity");
         if (INTEGRITY_NONE.equalsIgnoreCase(integrity))
            this.integrity = INTEGRITY_NONE;
         else if (INTEGRITY_SUPPORTED.equalsIgnoreCase(integrity))
            this.integrity = INTEGRITY_SUPPORTED;
         else if (INTEGRITY_REQUIRED.equalsIgnoreCase(integrity))
            this.integrity = INTEGRITY_REQUIRED;
         else
            throw new IllegalArgumentException("Unknown transport integrity: " + integrity);

         // set the transport confidentiality property.
         if (confidentiality == null)
            throw new IllegalArgumentException("Null confidentiality");
         if (CONFIDENTIALITY_NONE.equalsIgnoreCase(confidentiality))
            this.confidentiality = CONFIDENTIALITY_NONE;
         else if (CONFIDENTIALITY_SUPPORTED.equalsIgnoreCase(confidentiality))
            this.confidentiality = CONFIDENTIALITY_SUPPORTED;
         else if (CONFIDENTIALITY_REQUIRED.equalsIgnoreCase(confidentiality))
            this.confidentiality = CONFIDENTIALITY_REQUIRED;
         else
            throw new IllegalArgumentException("Unknown transport confidentiality: " + confidentiality);

         // set the transport establish-trust-in-target property.
         if (establishTrustInTarget == null)
            throw new IllegalArgumentException("Null establishTrustInTarget");
         if (ESTABLISH_TRUST_IN_TARGET_NONE.equalsIgnoreCase(establishTrustInTarget))
            this.establishTrustInTarget = ESTABLISH_TRUST_IN_TARGET_NONE;
         else if (ESTABLISH_TRUST_IN_TARGET_SUPPORTED.equalsIgnoreCase(establishTrustInTarget))
            this.establishTrustInTarget = ESTABLISH_TRUST_IN_TARGET_SUPPORTED;
         else
            throw new IllegalArgumentException("Unknown transport establishTrustInTarget: " + establishTrustInTarget);

         // set the transport establish-trust-in-client property.
        if (establishTrustInClient == null)
            throw new IllegalArgumentException("Null establishTrustInClient");
         if (ESTABLISH_TRUST_IN_CLIENT_NONE.equalsIgnoreCase(establishTrustInClient))
            this.establishTrustInClient = ESTABLISH_TRUST_IN_CLIENT_NONE;
         else if (ESTABLISH_TRUST_IN_CLIENT_SUPPORTED.equalsIgnoreCase(establishTrustInClient))
            this.establishTrustInClient = ESTABLISH_TRUST_IN_CLIENT_SUPPORTED;
         else if (ESTABLISH_TRUST_IN_CLIENT_REQUIRED.equalsIgnoreCase(establishTrustInClient))
            this.establishTrustInClient = ESTABLISH_TRUST_IN_CLIENT_REQUIRED;
         else
            throw new IllegalArgumentException("Unknown transport establishTrustInClient: " + establishTrustInClient);

         // set the transport detect-misordering optional property - default value is SUPPORTED.
         if (detectMisordering == null || DETECT_MISORDERING_SUPPORTED.equalsIgnoreCase(detectMisordering))
            this.detectMisordering = DETECT_MISORDERING_SUPPORTED;
         else if (DETECT_MISORDERING_NONE.equalsIgnoreCase(detectMisordering))
            this.detectMisordering = DETECT_MISORDERING_NONE;
         else if (DETECT_MISORDERING_REQUIRED.equalsIgnoreCase(detectMisordering))
            this.detectMisordering = DETECT_MISORDERING_REQUIRED;
         else
            throw new IllegalArgumentException("Unknown transport detectMisordering: " + detectMisordering);

         // set the transport detect-replay optional property - default value is SUPPORTED.
         if (detectReplay == null || DETECT_REPLAY_SUPPORTED.equalsIgnoreCase(detectReplay))
            this.detectReplay = DETECT_REPLAY_SUPPORTED;
         else if (DETECT_REPLAY_NONE.equalsIgnoreCase(detectReplay))
            this.detectReplay = DETECT_REPLAY_NONE;
         else if (DETECT_REPLAY_REQUIRED.equalsIgnoreCase(detectReplay))
            this.detectReplay = DETECT_REPLAY_REQUIRED;
         else
            throw new IllegalArgumentException("Unknown transport detectReplay: " + detectReplay);
      }

      /**
       * Get the integrity
       * 
       * @return the integrity
       */
      public String getIntegrity()
      {
         return integrity;
      }

      /**
       * Get the confidentiality
       * 
       * @return the confidentiality
       */
      public String getConfidentiality()
      {
         return confidentiality;
      }
      
      /**
       * Get the detect misordering
       * 
       * @return the detect misordering
       */
      public String getDetectMisordering()
      {
         return detectMisordering;
      }

      /**
       * Get the detect replay
       * 
       * @return the detect replay
       */
      public String getDetectReplay()
      {
         return detectReplay;
      }

      /**
       * Get the establish trust in target
       * 
       * @return the establish trust in target
       */
      public String getEstablishTrustInTarget()
      {
         return establishTrustInTarget;
      }

      /**
       * Whether establish trust in target is support
       * 
       * @return true when it is supported
       */
      public boolean isEstablishTrustInTargetSupported()
      {
         return ESTABLISH_TRUST_IN_TARGET_SUPPORTED.equalsIgnoreCase(establishTrustInTarget);
      }

      /**
       * Get the establish trust in client
       * 
       * @return the establish trust in client
       */
      public String getEstablishTrustInClient()
      {
         return establishTrustInClient;
      }

      @Override
      public String toString()
      {
         return
            "[integrity=" + integrity +
            ", confidentiality=" + confidentiality +
            ", establish-trust-in-target=" + establishTrustInTarget +
            ", establish-trust-in-client=" + establishTrustInClient + 
            ", detect-misordering=" + detectMisordering +
            ", detect-replay=" + detectReplay + "]";
      }
   }

   /**
    * as-context (CSIv2 authentication service) is the element describing the authentication
    * mechanism that will be used to authenticate the client. It can be either
    * the username-password mechanism, or none (default).
    */
   public class AsContext
   {
      public static final String AUTH_METHOD_USERNAME_PASSWORD = "USERNAME_PASSWORD";
      public static final String AUTH_METHOD_NONE = "NONE";

      /**
       * auth-method element describes the authentication method. The only supported values
       * are USERNAME_PASSWORD and NONE.
       * Required element.
       */
      private final String authMethod;

      /**
       * realm element describes the realm in which the user is authenticated. Must be
       * a valid realm that is registered in server configuration.
       * Required element.
       */
      private final String realm;

      /**
       * required element specifies if the authentication method specified is required
       * to be used for client authentication. If so the EstablishTrustInClient bit
       * will be set in the target_requires field of the AS_Context. The element value
       * is either true or false.
       * Required element.
       */
      private final boolean required;

      /**
       * Create a new AsContext.
       */
      private AsContext()
      {
         authMethod = AUTH_METHOD_USERNAME_PASSWORD;
         realm = "default";
         required = false;
      }

      /**
       * Create a new AsContext
       * 
       * @param asContext the delegate 
       */
      private AsContext(IORASContextMetaData asContext)
      {
         if (asContext == null)
            throw new IllegalArgumentException("Null asContext");
         authMethod = asContext.getAuthMethod();
         realm = asContext.getRealm();
         required = asContext.isRequired();
      }

      /**
       * Create a new AsContext with the specified properties.
       *
       * @param authMethod the authentication method to be used.
       * @param realm the realm in which the user is authenticated.
       * @param requires a {@code boolean} that specifies if the authentication method is required to be used for
       *        client authentication.
       */
      public AsContext(String authMethod, String realm, boolean required)
      {
         // set the auth method.
         if (authMethod == null)
            throw new IllegalArgumentException("Null authMethod");
         if (AUTH_METHOD_NONE.equalsIgnoreCase(authMethod))
            this.authMethod = AUTH_METHOD_NONE;
         else if (AUTH_METHOD_USERNAME_PASSWORD.equalsIgnoreCase(authMethod))
            this.authMethod = AUTH_METHOD_USERNAME_PASSWORD;
         else
            throw new IllegalArgumentException("Unknown ascontext authMethod: " + authMethod);

         // set the realm.
         if (realm == null)
            throw new IllegalArgumentException("Null realm");
         this.realm = realm;

         this.required = required;
      }
      
      /**
       * Get the auth method
       * 
       * @return the auth method
       */
      public String getAuthMethod()
      {
         return authMethod;
      }

      /**
       * Get the realm
       * 
       * @return the realm
       */
      public String getRealm()
      {
         return realm;
      }

      /**
       * Whether it is required
       * 
       * @return true when required
       */
      public boolean isRequired()
      {
         return required;
      }

      @Override
      public String toString()
      {
         return
            "[auth-method=" + authMethod +
            ", realm=" + realm +
            ", required=" + required + "]";
      }
   }

   /**
    * sas-context (related to CSIv2 security attribute service) element describes
    * the sas-context fields.
    */
   public class SasContext
   {
      public static final String CALLER_PROPAGATION_NONE = "NONE";
      public static final String CALLER_PROPAGATION_SUPPORTED = "SUPPORTED";

      /**
       * caller-propagation element indicates if the target will accept propagated caller identities
       * The values are NONE or SUPPORTED.
       * Required element.
       */
      private final String callerPropagation;

      /**
       * Create a new SasContext.
       */
      private SasContext()
      {
         callerPropagation = CALLER_PROPAGATION_NONE;
      }

      /**
       * Create a new SasContext.
       * 
       * @param sasContext the delegate
       */
      private SasContext(IORSASContextMetaData sasContext)
      {
         if (sasContext == null)
            throw new IllegalArgumentException("Null sasContext");
         callerPropagation = sasContext.getCallerPropagation();
      }

      /**
       * Create a new SasContext with the specified properties.
       *
       * @param callerPropagation indicates if the target will accept propagated caller identities or not.
       */      
      public SasContext(String callerPropagation)
      {
         if (callerPropagation == null)
            throw new IllegalArgumentException("Null callerPropagation");
         if (CALLER_PROPAGATION_NONE.equalsIgnoreCase(callerPropagation))
            this.callerPropagation = CALLER_PROPAGATION_NONE;
         else if (CALLER_PROPAGATION_SUPPORTED.equalsIgnoreCase(callerPropagation))
            this.callerPropagation = CALLER_PROPAGATION_SUPPORTED;
         else
            throw new IllegalArgumentException("Unknown sascontext callerPropagtion: " + callerPropagation);
      }

      /**
       * Get the caller propagation
       * 
       * @return the caller propagation
       */
      public String getCallerPropagation()
      {
         return callerPropagation;
      }

      /**
       * Whether caller propagation is supported
       * 
       * @return true when propagation is supported
       */
      public boolean isCallerPropagationSupported()
      {
         return CALLER_PROPAGATION_SUPPORTED.equalsIgnoreCase(callerPropagation);
      }

      @Override
      public String toString()
      {
         return "[caller-propagation=" + callerPropagation + "]";
      }
   }
}

