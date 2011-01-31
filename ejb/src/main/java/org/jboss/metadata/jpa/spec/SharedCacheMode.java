package org.jboss.metadata.jpa.spec;

import org.jboss.xb.annotations.JBossXmlEnum;

/**
 * @author Emmanuel Bernard
 */
@JBossXmlEnum(ignoreCase = true)
public enum SharedCacheMode
{
   ALL,
   NONE,
   ENABLE_SELECTIVE,
   DISABLE_SELECTIVE,
   UNSPECIFIED
}