package rare.earth.metals.store.metalscatalog.integration.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import rare.earth.metals.store.metalscatalog.integration.service.metal.MetalServiceTest;
import rare.earth.metals.store.metalscatalog.integration.service.collection.CollectionServiceTest;

@RunWith(Suite.class)
@SuiteClasses({CollectionServiceTest.class,
               MetalServiceTest.class})
public class ServiceIntegrationTestSuite {}