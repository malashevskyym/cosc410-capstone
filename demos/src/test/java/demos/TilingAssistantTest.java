package demos;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TilingAssistantTest {
  @Test
  public void testTilingAssitantWithTileSize() {
    TilingAssistant test = new TilingAssistant();
    test.setLenRoom(15f);
    test.setWidRoom(21f);
    test.setSizeTile(4f);
    String result = test.checkFit();
    assertEquals("12:(4.0 x 4.0 in) 8:(0.5 x 4.0 in) 6:(4.0 x 1.25 in) 4:(0.5 x 1.25 in)", result);
  }

  @Test
  public void testTilingAssitant() {
    TilingAssistant test = new TilingAssistant();
    test.setLenRoom(324.5f);
    test.setWidRoom(200f);
    String result = test.checkFit();
    assertEquals("1500:(6.0 x 6.0 in) 100:(6.0 x 2.25 in)", result);
  }

  @Test
  public void testTilingAssitantNegativeGroutGap() {
    TilingAssistant test = new TilingAssistant();
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          test.setGroutGap(-2f);
        });
  }

  @Test
  public void testTilingAssitantNegativeTileSize() {
    TilingAssistant test = new TilingAssistant();
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          test.setSizeTile(-2f);
        });
  }

  @Test
  public void testTilingAssitantNegativeWidRoom() {
    TilingAssistant test = new TilingAssistant();
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          test.setWidRoom(-2f);
        });
  }

  @Test
  public void testTilingAssitantwithMetricFulOnlyWithoutFull() {
    TilingAssistant test = new TilingAssistant();
    test.setLenRoom(71f);
    test.setWidRoom(53f);
    test.setSizeTile(5f);
    test.setGroutGap(1f);
    String result = test.checkFit();
    assertEquals("108:(5.0 x 5.0 in)", result);
  }

  @Test
  public void testTilingAssitantwithMetricFulOnlyGroutGap() {
    TilingAssistant test = new TilingAssistant();
    test.setLenRoom(28.5f);
    test.setWidRoom(22f);
    test.setSizeTile(4.75f);
    test.setFullOnly(true);
    test.changeToMetric(true);
    test.setGroutGap(0.25f);
    String result = test.checkFit();
    assertEquals("20:(4.75 x 4.75 cm)", result);
  }

  @Test
  public void testMain() throws IOException {
    TilingAssistant tile = new TilingAssistant();
    String[] args = new String[] {"28.5", "--groutgap", "0.25", "22", "--tilesize", "4.75"};
    tile.main(args);
  }

  @Test
  public void testMainHelpException() throws IOException {
    TilingAssistant tile = new TilingAssistant();
    String[] args =
        new String[] {"--help", "28.5", "--groutgap", "0.25", "22", "--tilesize", "4.75"};
    tile.main(args);
  }

  @Test
  public void testMainNegative() throws IOException {
    TilingAssistant tile = new TilingAssistant();
    String[] args = new String[] {"-0.1", "10"};
    tile.main(args);
  }

  @Test
  public void testMainExeptions() throws IOException {
    TilingAssistant tile = new TilingAssistant();
    String[] args = new String[] {"15", "21", "4.0"};
    tile.main(args);
  }
}
