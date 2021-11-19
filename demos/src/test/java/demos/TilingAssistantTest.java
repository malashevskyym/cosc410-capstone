package demos;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
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
}
