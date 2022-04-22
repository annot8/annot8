/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.content;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class ColumnMetadataTest {

  void testColumnMetadata() {
    String name = "name";
    long size = 100;
    ColumnMetadata metadata = new ColumnMetadata(name, size);
    assertEquals(name, metadata.getName());
    assertEquals(size, metadata.getSize());
  }

  @Test
  void testEquals() {
    ColumnMetadata test = new ColumnMetadata("test", 1);
    assertNotEquals(test, new Object());
    assertNotEquals(test, new ColumnMetadata("different", 2));
    ColumnMetadata equal = new ColumnMetadata("test", 1);
    assertEquals(test, equal);
    assertEquals(test.hashCode(), equal.hashCode());
  }
}
