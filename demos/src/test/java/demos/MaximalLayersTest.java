package demos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MaximalLayersTest {
  @Test
  public void testOneLayer() {
    String[] testInput = new String[] {"5,5"};
    MaximalLayers maxLayerTest = new MaximalLayers(testInput[0], false, false);

    assertEquals("1:(5,5)", maxLayerTest.layers);
  }

  @Test
  public void testTwoLayers() {
    String[] testInput = new String[] {"5,5,4,9,10,2"};
    MaximalLayers maxLayerTest = new MaximalLayers(testInput[0], false, false);

    assertEquals("1:(5,5)(4,9)(10,2)", maxLayerTest.layers);
  }

  @Test
  public void testThreeLayers() {
    String[] testInput = new String[] {"5,5,4,9,10,2,2,3,15,7"};
    MaximalLayers maxLayerTest = new MaximalLayers(testInput[0], false, false);

    assertEquals("1:(4,9)(15,7) 2:(5,5)(10,2) 3:(2,3)", maxLayerTest.layers);
  }

  @Test
  public void testLayerWithTwoMaximalPoints() {
    String[] testInput = new String[] {"1,5,5,5,4,9,10,2,2,3,15,7"};
    MaximalLayers maxLayerTest = new MaximalLayers(testInput[0], false, false);

    assertEquals("1:(4,9)(15,7) 2:(1,5)(5,5)(10,2) 3:(2,3)", maxLayerTest.layers);
  }

  @Test
  public void testSortedX() {
    String[] testInput = new String[] {"1,5,5,5,4,9,10,2,2,3,15,7", "--sortedX"};
    MaximalLayers maxLayerTest = new MaximalLayers(testInput[0], true, false);

    assertEquals("1:(4,9)(15,7) 2:(1,5)(5,5)(10,2) 3:(2,3)", maxLayerTest.layers);
  }

  @Test
  public void testSortedY() {
    String[] testInput = new String[] {"1,5,5,5,4,9,10,2,2,3,15,7", "--sortedY"};
    MaximalLayers maxLayerTest = new MaximalLayers(testInput[0], false, true);

    assertEquals("1:(15,7)(4,9) 2:(10,2)(1,5)(5,5) 3:(2,3)", maxLayerTest.layers);
  }

  @Test
  public void testSortedXAndSortedY() {
    String[] testInput = new String[] {"1,5,5,5,4,9,10,2,2,3,15,7", "--sortedX", "--sortedY"};
    MaximalLayers maxLayerTest = new MaximalLayers(testInput[0], true, true);

    assertEquals("1:(4,9)(15,7) 2:(1,5)(5,5)(10,2) 3:(2,3)", maxLayerTest.layers);
  }

  @Test
  public void testMain() {
    String[] testInput = new String[] {"1,5,5,5,4,9,10,2,2,3,15,7"};
    MaximalLayers maxLayerTest = new MaximalLayers(testInput[0], false, false);
    maxLayerTest.main("1,5,5,5,4,9,10,2,2,3,15,7");

    assertEquals("1:(4,9)(15,7) 2:(1,5)(5,5)(10,2) 3:(2,3)", maxLayerTest.layers);
  }

  @Test
  public void testMainParseError() {
    String[] testInput = new String[] {"1,5,5,5,4,9,10,2,2,3,15,7"};
    MaximalLayers maxLayerTest = new MaximalLayers(testInput[0], false, false);
    maxLayerTest.main("1,5,5,5,x,9,10,2,2,3,15,7");

    assertEquals("1:(4,9)(15,7) 2:(1,5)(5,5)(10,2) 3:(2,3)", maxLayerTest.layers);
  }

  @Test
  public void testMainPairError() {
    String[] testInput = new String[] {"1,5,5,5,4,9,10,2,2,3,15,7"};
    MaximalLayers maxLayerTest = new MaximalLayers(testInput[0], false, false);
    maxLayerTest.main("1,5,5,5,x,9,10,2,2,3,15");

    assertEquals("1:(4,9)(15,7) 2:(1,5)(5,5)(10,2) 3:(2,3)", maxLayerTest.layers);
  }
}
