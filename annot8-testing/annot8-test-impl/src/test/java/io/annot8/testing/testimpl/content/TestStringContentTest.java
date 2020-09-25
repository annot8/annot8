/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.testing.testimpl.content;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestStringContentTest {

  @Test
  public void testAbstractTestContent() {
    TestStringContent stringContent = new TestStringContent(null);
    assertEquals("Test data", stringContent.getData());
  }
}
