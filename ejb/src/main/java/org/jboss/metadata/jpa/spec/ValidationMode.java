package org.jboss.metadata.jpa.spec;

import org.jboss.xb.annotations.JBossXmlEnum;

/**
 * @author Emmanuel Bernard
 */
@JBossXmlEnum(ignoreCase = true)
public enum ValidationMode
{
   AUTO,
   CALLBACK,
   NONE
}