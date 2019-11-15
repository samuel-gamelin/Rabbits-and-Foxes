package model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BoardTest.class, FoxTest.class, MushroomTest.class, RabbitTest.class, TileTest.class })
public class AllTests {

}
