package demos;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import org.junit.jupiter.api.Test;

class VolumeCalculatorTest {

  @Test
  public void testConstructorAndHWL() {
    VolumeCalculator t = new VolumeCalculator(2f, 5f, 3f);
    double a = t.getHeight();
    double b = t.getWidth();
    double c = t.getLength();
    assertEquals(3.0, a);
    assertEquals(5.0, b);
    assertEquals(2.0, c);
  }

  @Test
  public void testEllipsoid() {
    VolumeCalculator t = new VolumeCalculator(2f, 5f, 3f);
    t.setShape("ellipsoid");
    t.setPrecision(3);
    String a = t.getShape();
    double b = t.calcVolume();
    assertEquals("ellipsoid", a);
    assertEquals(125.664, b);
  }

  @Test
  public void testTriangle() {
    VolumeCalculator t = new VolumeCalculator(5f, 3f, 1f);
    t.setShape("triangle");
    t.setPrecision(2);
    String a = t.getShape();
    t.calcVolume();
    assertEquals("triangle", a);
  }

  @Test
  public void testDefaultShape() {
    VolumeCalculator t = new VolumeCalculator(2f, 5f, 3f);
    String a = t.getShape();
    assertEquals("box", a);
  }

  @Test
  public void testPyramid() {
    VolumeCalculator t = new VolumeCalculator(2f, 5f, 3f);
    t.setShape("pyramid");
    String a = t.getShape();
    double b = t.calcVolume();
    assertEquals("pyramid", a);
    assertEquals(10.0, b);
  }

  @Test
  public void testPrecisionWithDefault() {
    VolumeCalculator t = new VolumeCalculator(2.785f, 7.3f, 3.23f);
    t.setPrecision(3);
    double a = t.getPrecision();
    double b = t.calcVolume();
    assertEquals(3, a);
    assertEquals(65.668, b);
  }

  @Test
  public void testVolumeCalculatorMain() {
    String xml =
        "<?xml version='1.0'?><arguments><positionalArgs><positional><type>float</type><description>the length of the volume</description><name>length</name></positional><positional><type>float</type><name>width</name><description>the width of the volume</description></positional><positional><description>the height of the volume</description><name>height</name><type>float</type></positional></positionalArgs><namedArgs><named><description>the type of volume</description><shortname>t</shortname><type>string</type><name>type</name><restrictions><restriction>box</restriction><restriction>pyramid</restriction><restriction>ellipsoid</restriction></restrictions><default><value>box</value></default></named><named><default><value>4</value></default><type>integer</type><description>the maximum number of decimal places for the volume</description><name>precision</name><shortname>p</shortname></named></namedArgs></arguments>";
    String[] testStrings = new String[] {xml, "2", "5", "3"};
    VolumeCalculator t = new VolumeCalculator(2f, 5f, 3f);
    t.setShape("pyramid");
    t.setPrecision(2);
    t.main(testStrings);
    double a = t.calcVolume();
    assertEquals(10.0, a);
  }

  @Test
  public void testVolumeCalculatorMainInvalidXML() {
    String xml = "";
    String[] testStrings = new String[] {xml, "2", "5", "3"};
    VolumeCalculator t = new VolumeCalculator(2f, 5f, 3f);
    t.setShape("pyramid");
    t.setPrecision(2);
    t.main(testStrings);
    double a = t.calcVolume();
    assertEquals(10.0, a);
  }

  @Test
  public void testVolumeCalculatorMainNoPrecision() {
    String xml =
        "<?xml version='1.0'?><arguments><positionalArgs><positional><type>float</type><description>the length of the volume</description><name>length</name></positional><positional><type>float</type><name>width</name><description>the width of the volume</description></positional><positional><description>the height of the volume</description><name>height</name><type>float</type></positional></positionalArgs><namedArgs><named><description>the type of volume</description><shortname>t</shortname><type>string</type><name>type</name><restrictions><restriction>box</restriction><restriction>pyramid</restriction><restriction>ellipsoid</restriction></restrictions><default><value>box</value></default></named><named><default><value>4</value></default><type>integer</type><description>the maximum number of decimal places for the volume</description><name>precision</name><shortname>p</shortname></named></namedArgs></arguments>";
    String[] testStrings = new String[] {xml, "2", "5", "3", "-p"};
    VolumeCalculator t = new VolumeCalculator(2f, 5f, 3f);
    t.setShape("pyramid");
    t.setPrecision(2);
    t.main(testStrings);
    double a = t.calcVolume();
    assertEquals(10.0, a);
  }

  @Test
  public void testVolumeCalculatorMainHelp() {
    String xml =
        "<?xml version='1.0'?><arguments><positionalArgs><positional><type>float</type><description>the length of the volume</description><name>length</name></positional><positional><type>float</type><name>width</name><description>the width of the volume</description></positional><positional><description>the height of the volume</description><name>height</name><type>float</type></positional></positionalArgs><namedArgs><named><description>the type of volume</description><shortname>t</shortname><type>string</type><name>type</name><restrictions><restriction>box</restriction><restriction>pyramid</restriction><restriction>ellipsoid</restriction></restrictions><default><value>box</value></default></named><named><default><value>4</value></default><type>integer</type><description>the maximum number of decimal places for the volume</description><name>precision</name><shortname>p</shortname></named></namedArgs></arguments>";
    String[] testStrings = new String[] {xml, "2", "5", "3", "-h"};
    VolumeCalculator t = new VolumeCalculator(2f, 5f, 3f);
    t.setShape("pyramid");
    t.setPrecision(2);
    t.main(testStrings);
    double a = t.calcVolume();
    assertEquals(10.0, a);
  }
}
