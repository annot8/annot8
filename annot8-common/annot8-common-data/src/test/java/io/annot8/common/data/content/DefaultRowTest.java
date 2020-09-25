/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.content;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class DefaultRowTest {

  @Test
  public void testDefaultRow() {
    int index = 1;
    List<String> columnNames = Arrays.asList("test", "test2");
    List<Object> data = Arrays.asList(new Object[] {1, "test"});
    DefaultRow row = new DefaultRow(index, columnNames, data);

    assertEquals(1, row.getRowIndex());
    assertEquals(columnNames, row.getColumnNames());
    assertEquals(2, row.getColumnCount());

    Optional<Object> value1 = row.getValueAt(0);
    Optional<Object> value2 = row.getValueAt(1);

    assertTrue(value1.isPresent());
    assertTrue(value2.isPresent());
    assertEquals(1, value1.get());
    assertEquals("test", value2.get());
  }

  @Test
  public void testGetValueAtOutOfBounds() {
    DefaultRow row =
        new DefaultRow(1, Collections.singletonList("Test"), Collections.singletonList("Test"));

    assertFalse(row.getValueAt(1).isPresent());
    assertFalse(row.getValueAt(-1).isPresent());
    assertTrue(row.getValueAt(0).isPresent());
  }
}
