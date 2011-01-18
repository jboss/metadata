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
package org.jboss.metadata.ejb.jboss;

import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;

/**
 * IORTransportConfigMetaData.
 * 
 * TODO LAST enums
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="transport-configType", propOrder={"descriptions", "integrity", "confidentiality", "establishTrustInTarget",
      "establishTrustInClient", "detectMisordering", "detectReplay"})
public class IORTransportConfigMetaData extends IdMetaDataImplWithDescriptions
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 779676398058819672L;
   
   /** Integrity not required */
   public static final String INTEGRITY_NONE = "NONE";

   /** Integrity supported */
   public static final String INTEGRITY_SUPPORTED = "SUPPORTED";

   /** Integrity required */
   public static final String INTEGRITY_REQUIRED = "REQUIRED";

   /** Confidentiality not required */
   public static final String CONFIDENTIALITY_NONE = "NONE";

   /** Confidentiality supported */
   public static final String CONFIDENTIALITY_SUPPORTED = "SUPPORTED";

   /** Confidentiality required */
   public static final String CONFIDENTIALITY_REQUIRED = "REQUIRED";

   /** Detect misordering not required */
   public static final String DETECT_MISORDERING_NONE = "NONE";

   /** Detect misordering supported */
   public static final String DETECT_MISORDERING_SUPPORTED = "SUPPORTED";

   /** Detect misordering required */
   public static final String DETECT_MISORDERING_REQUIRED = "REQUIRED";

   /** Detect replay not required */
   public static final String DETECT_REPLAY_NONE = "NONE";

   /** Detect replay supported */
   public static final String DETECT_REPLAY_SUPPORTED = "SUPPORTED";

   /** Detect replay required */
   public static final String DETECT_REPLAY_REQUIRED = "REQUIRED";

   /** Establish trust in target not required */
   public static final String ESTABLISH_TRUST_IN_TARGET_NONE = "NONE";

   /** Establish trust in target supported */
   public static final String ESTABLISH_TRUST_IN_TARGET_SUPPORTED = "SUPPORTED";

   /** Establish trust in client not required */
   public static final String ESTABLISH_TRUST_IN_CLIENT_NONE = "NONE";

   /** Establish trust in client supported */
   public static final String ESTABLISH_TRUST_IN_CLIENT_SUPPORTED = "SUPPORTED";

   /** Establish trust in client required */
   public static final String ESTABLISH_TRUST_IN_CLIENT_REQUIRED = "REQUIRED";

   /** The integrity */
   private String integrity = INTEGRITY_NONE;

   /** The confidentiality */
   private String confidentiality = CONFIDENTIALITY_NONE;

   /** The establish trust in target */
   private String establishTrustInTarget = ESTABLISH_TRUST_IN_TARGET_NONE;

   /** The establish trust in client */
   private String establishTrustInClient = ESTABLISH_TRUST_IN_CLIENT_NONE;

   /** Whether to detect misordering */
   private String detectMisordering = DETECT_MISORDERING_NONE;

   /** Whether to detect replay */
   private String detectReplay = DETECT_REPLAY_NONE;
   
   /**
    * Get the integrity.
    * 
    * @return the integrity.
    */
   public String getIntegrity()
   {
      return integrity;
   }

   /**
    * Set the integrity.
    * 
    * @param integrity the integrity.
    * @throws IllegalArgumentException for a null integrity
    */
   public void setIntegrity(String integrity)
   {
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
   }

   /**
    * Get the confidentiality.
    * 
    * @return the confidentiality.
    */
   public String getConfidentiality()
   {
      return confidentiality;
   }

   /**
    * Set the confidentiality.
    * 
    * @param confidentiality the confidentiality.
    * @throws IllegalArgumentException for a null confidentiality
    */
   public void setConfidentiality(String confidentiality)
   {
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
   }

   /**
    * Get the establishTrustInTarget.
    * 
    * @return the establishTrustInTarget.
    */
   public String getEstablishTrustInTarget()
   {
      return establishTrustInTarget;
   }

   /**
    * Set the establishTrustInTarget.
    * 
    * @param establishTrustInTarget the establishTrustInTarget.
    * @throws IllegalArgumentException for a null establishTrustInTarget
    */
   public void setEstablishTrustInTarget(String establishTrustInTarget)
   {
      if (establishTrustInTarget == null)
         throw new IllegalArgumentException("Null establishTrustInTarget");
      if (ESTABLISH_TRUST_IN_TARGET_NONE.equalsIgnoreCase(establishTrustInTarget))
         this.establishTrustInTarget = ESTABLISH_TRUST_IN_TARGET_NONE;
      else if (ESTABLISH_TRUST_IN_TARGET_SUPPORTED.equalsIgnoreCase(establishTrustInTarget))
         this.establishTrustInTarget = ESTABLISH_TRUST_IN_TARGET_SUPPORTED;
      else
         throw new IllegalArgumentException("Unknown transport establishTrustInTarget: " + establishTrustInTarget);
   }

   /**
    * Get the establishTrustInClient.
    * 
    * @return the establishTrustInClient.
    */
   public String getEstablishTrustInClient()
   {
      return establishTrustInClient;
   }

   /**
    * Set the establishTrustInClient.
    * 
    * @param establishTrustInClient the establishTrustInClient.
    * @throws IllegalArgumentException for a null establishTrustInClient
    */
   public void setEstablishTrustInClient(String establishTrustInClient)
   {
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
   }

   /**
    * Get the detectMisordering.
    * 
    * @return the detectMisordering.
    */
   public String getDetectMisordering()
   {
      return detectMisordering;
   }

   /**
    * Set the detectMisordering.
    * 
    * @param detectMisordering the detectMisordering.
    * @throws IllegalArgumentException for a null detectMisordering
    */
   public void setDetectMisordering(String detectMisordering)
   {
      if (detectMisordering == null)
         throw new IllegalArgumentException("Null detectMisordering");
      if (DETECT_MISORDERING_NONE.equalsIgnoreCase(detectMisordering))
         this.detectMisordering = DETECT_MISORDERING_NONE;
      else if (DETECT_MISORDERING_SUPPORTED.equalsIgnoreCase(detectMisordering))
         this.detectMisordering = DETECT_MISORDERING_SUPPORTED;
      else if (DETECT_MISORDERING_REQUIRED.equalsIgnoreCase(detectMisordering))
         this.detectMisordering = DETECT_MISORDERING_REQUIRED;
      else
         throw new IllegalArgumentException("Unknown transport detectMisordering: " + detectMisordering);
   }

   /**
    * Get the detectReplay.
    * 
    * @return the detectReplay.
    */
   public String getDetectReplay()
   {
      return detectReplay;
   }

   /**
    * Set the detectReplay.
    * 
    * @param detectReplay the detectReplay.
    * @throws IllegalArgumentException for a null detectReplay
    */
   public void setDetectReplay(String detectReplay)
   {
      if (detectReplay == null)
         throw new IllegalArgumentException("Null detectReplay");
      if (DETECT_REPLAY_NONE.equalsIgnoreCase(detectReplay))
         this.detectReplay = DETECT_REPLAY_NONE;
      else if (DETECT_REPLAY_SUPPORTED.equalsIgnoreCase(detectReplay))
         this.detectReplay = DETECT_REPLAY_SUPPORTED;
      else if (DETECT_REPLAY_REQUIRED.equalsIgnoreCase(detectReplay))
         this.detectReplay = DETECT_REPLAY_REQUIRED;
      else
         throw new IllegalArgumentException("Unknown transport detectReplay: " + detectReplay);
   }
}
