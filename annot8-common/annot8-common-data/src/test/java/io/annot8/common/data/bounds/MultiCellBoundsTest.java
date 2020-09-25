/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.bounds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import io.annot8.common.data.content.Row;
import io.annot8.common.data.content.Table;
import io.annot8.common.data.content.TableContent;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MultiCellBoundsTest {

  @Test
  public void testMultiCellBounds() {
    MultiCellBounds bounds = new MultiCellBounds(0, new int[] {0, 1});

    assertEquals(0, bounds.getCells()[0]);
    assertEquals(1, bounds.getCells()[1]);
    assertEquals(0, bounds.getRow());
    assertTrue(bounds.isValid(getTestTableContent()));
  }

  @Test
  public void testInvalidMultiCellBounds() {
    MultiCellBounds bounds = new MultiCellBounds(0, new int[] {0, 1, 2});

    assertFalse(bounds.isValid(getTestTableContent()));
  }

  @Test
  public void testGetData() {
    MultiCellBounds bounds = new MultiCellBounds(0, new int[] {0, 1});
    Optional<MultiCellData> optional = bounds.getData(getTestTableContent(), MultiCellData.class);

    assertTrue(optional.isPresent());

    MultiCellData data = optional.get();
    assertEquals(2, data.getColumnNames().size());
    assertEquals(0, (int) data.getColumnNames().get("test"));
    assertEquals(1, (int) data.getColumnNames().get("test2"));

    assertEquals(2, data.getData().size());
    assertEquals("test", data.getDataForColumn("test", String.class).get());
    assertEquals("test", data.getDataForColumn("test2", String.class).get());
    assertEquals("test", data.getDataForColumn(0, String.class).get());
    assertEquals("test", data.getDataForColumn(1, String.class).get());
  }

  @Test
  public void testDataInvalidRequest() {
    MultiCellBounds bounds = new MultiCellBounds(0, new int[] {1, 2});
    assertFalse(bounds.getData(getTestTableContent(), Object.class).isPresent());
  }

  private TableContent getTestTableContent() {
    Row row = Mockito.mock(Row.class);
    when(row.getColumnNames()).thenReturn(Arrays.asList("test", "test2"));
    when(row.getColumnCount()).thenReturn(2);
    when(row.getValueAt(anyInt())).thenReturn(Optional.of("test"));
    when(row.getColumnName(anyInt())).thenCallRealMethod();

    TableContent content = Mockito.mock(TableContent.class);
    Table table = Mockito.mock(Table.class);
    when(table.getRowCount()).thenReturn(2);
    when(table.getColumnCount()).thenReturn(2);
    when(table.getRow(eq(0))).thenReturn(Optional.of(row));
    when(table.getRow(eq(1))).thenReturn(Optional.empty());
    when(content.getData()).thenReturn(table);
    return content;
  }
}
