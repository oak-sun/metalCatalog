package rare.earth.metals.store.metalscatalog;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import rare.earth.metals.store.metalscatalog.integration.service.ServiceIntegrationTestSuite;
import rare.earth.metals.store.metalscatalog.unit.controller.ControllerUnitTestSuite;

@RunWith(Suite.class)
@SuiteClasses({ ServiceIntegrationTestSuite.class,
                ControllerUnitTestSuite.class })
public class TestSuite {
}