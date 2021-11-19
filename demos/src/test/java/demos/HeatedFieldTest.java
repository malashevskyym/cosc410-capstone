package demos;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HeatedFieldTest {
  @Test
  public void testHeatedTest() {
    HeatedField t = new HeatedField();
    t.setNorthTemp(70);
    t.setSouthTemp(60);
    t.setEastTemp(80);
    t.setWestTemp(90);
    t.setInitialTemp(10);
    t.setTarget(2, 2);
    t.setMinutes(2);
    t.createField();
    String tempResult = t.getTemp();
    assertEquals("cell (2, 2) is 18.75 degrees Fahrenheit after 2 minutes", tempResult);
  }

  @Test
  public void testNegativeMinutes() {
    HeatedField t = new HeatedField();
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          t.setMinutes(-2);
        });
  }

  @Test
  public void testMain() throws IOException {
    HeatedField field = new HeatedField();
    String[] args = new String[] {"70", "60", "80", "90", "2", "2", "-t", "10", "-m", "2"};
    field.main(args);
  }
}
