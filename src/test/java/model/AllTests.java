package model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import util.SolverTest;
import util.MoveTest;

@RunWith(Suite.class)
@SuiteClasses({ BoardTest.class, FoxTest.class, MushroomTest.class, RabbitTest.class, TileTest.class, SolverTest.class, MoveTest.class})
public class AllTests {

}
