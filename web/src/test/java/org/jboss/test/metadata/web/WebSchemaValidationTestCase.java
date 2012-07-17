package org.jboss.test.metadata.web;

import java.util.List;

import org.jboss.test.metadata.common.SchemaValidationTestCase;
import org.junit.runners.Parameterized.Parameters;

public class WebSchemaValidationTestCase extends SchemaValidationTestCase {
	
	@Parameters
	public static List<Object[]> parameters() {
		String xsdFile = "schema/web-app_3_0.xsd";
		return getXSDFiles(xsdFile);
	}

	public WebSchemaValidationTestCase(String xsd) {
		super(xsd);
	}

}
