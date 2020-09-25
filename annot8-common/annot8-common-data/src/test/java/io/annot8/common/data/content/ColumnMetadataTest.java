/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.content;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class ColumnMetadataTest {

  @Test
  public void testColumnMetadata() {
    String name = "name";
    String type = "type";
    long size = 100;
    ColumnMetadata metadata = new ColumnMetadata(name, size);
    assertEquals(name, metadata.getName());
    assertEquals(size, metadata.getSize());
  }

  @Test
  public void testEquals() {
    ColumnMetadata test = new ColumnMetadata("test", 1);
    assertNotEquals(test, null);
    assertNotEquals(test, new Object());
    assertNotEquals(test, new ColumnMetadata("different", 2));
    ColumnMetadata equal = new ColumnMetadata("test", 1);
    assertEquals(test, equal);
    assertEquals(test.hashCode(), equal.hashCode());
  }
}
