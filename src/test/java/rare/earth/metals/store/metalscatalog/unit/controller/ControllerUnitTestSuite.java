package rare.earth.metals.store.metalscatalog.unit.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import rare.earth.metals.store.metalscatalog.unit.controller.collection.CollectionControllerTest;
import rare.earth.metals.store.metalscatalog
        .unit.controller.metal.MetalControllerTest;

@RunWith(Suite.class)
@SuiteClasses({MetalControllerTest.class,
               CollectionControllerTest.class})
public class ControllerUnitTestSuite {}