package demos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VolumeCalculatorTest {
  @Test
  public void testConstructorAndHWL() {
    VolumeCalculator t = new VolumeCalculator(2, 5, 3);
    double a = t.getHeight();
    double b = t.getWidth();
    double c = t.getLength();
    assertEquals(3.0, a);
    assertEquals(5.0, b);
    assertEquals(2.0, c);
  }

  @Test
  public void testEllipsoid() {
    VolumeCalculator t = new VolumeCalculator(2, 5, 3);
    t.setShape("ellipsoid");
    t.setPrecision(3);
    String a = t.getShape();
    double b = t.calcVolume();
    assertEquals("ellipsoid", a);
    assertEquals(125.664, b);
  }

  @Test
  public void testDefaultShape() {
    VolumeCalculator t = new VolumeCalculator(2, 5, 3);
    String a = t.getShape();
    assertEquals("box", a);
  }

  @Test
  public void testPyramid() {
    VolumeCalculator t = new VolumeCalculator(2, 5, 3);
    t.setShape("pyramid");
    String a = t.getShape();
    double b = t.calcVolume();
    assertEquals("pyramid", a);
    assertEquals(10.0, b);
  }

  @Test
  public void testPrecisionWithDefault() {
    VolumeCalculator t = new VolumeCalculator(2.785, 7.3, 3.23);
    t.setPrecision(3);
    double a = t.getPrecision();
    double b = t.calcVolume();
    assertEquals(3, a);
    assertEquals(65.668, b);
  }

  @Test
  public void testVolumeCalculatorMain() {
    String[] testStrings = new String[] {"2", "5", "3"};
    VolumeCalculator t = new VolumeCalculator(2.785, 7.3, 3.23);
    t.main(testStrings);
    String a = t.getShape();
    assertEquals("box", a);
  }
}
