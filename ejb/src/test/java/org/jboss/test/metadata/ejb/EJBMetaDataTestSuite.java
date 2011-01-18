/*
* JBoss, Home of Professional Open Source
* Copyright 2005, JBoss Inc., and individual contributors as indicated
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
package org.jboss.test.metadata.ejb;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * EJB Metadata Test Suite.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.8 $
 */
public class EJBMetaDataTestSuite extends TestSuite
{
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("EJB Metadata Tests");

      suite.addTest(EjbJar3xUnitTestCase.suite());
      suite.addTest(EjbJar21UnitTestCase.suite());
      suite.addTest(EjbJar20UnitTestCase.suite());
      suite.addTest(EjbJar3xEverythingUnitTestCase.suite());
      suite.addTest(EjbJar21EverythingUnitTestCase.suite());
      suite.addTest(JBoss50UnitTestCase.suite());
      suite.addTest(JBoss5xEverythingUnitTestCase.suite());
      suite.addTest(EjbJarJBossMergeEverythingUnitTestCase.suite());
      suite.addTest(JBoss42UnitTestCase.suite());
      suite.addTest(EjbJarJBossMergeActivationConfigUnitTestCase.suite());
      suite.addTestSuite(ResolvedJndiNameUnitTestCase.class);

      //suite.addTest(JBossCMPUnitTestCase.suite());
      //suite.addTest(JBossCMPMergeUnitTestCase.suite());
      
      return suite;
   }
}
