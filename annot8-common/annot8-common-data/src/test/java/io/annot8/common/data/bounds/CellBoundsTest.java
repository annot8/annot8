/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.bounds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import io.annot8.api.exceptions.Annot8RuntimeException;
import io.annot8.common.data.content.Row;
import io.annot8.common.data.content.Table;
import io.annot8.common.data.content.TableContent;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CellBoundsTest {

  @Test
  public void testCellBounds() {
    CellBounds bounds = new CellBounds(1, 1);

    assertEquals(1, bounds.getRow());
    assertEquals(1, bounds.getColumn());
    assertTrue(bounds.isValid(getTestTableContent()));

    CellBounds nonValidBounds = new CellBounds(-1, -1);
    assertFalse(nonValidBounds.isValid(getTestTableContent()));
  }

  @Test
  public void testGetData() {
    CellBounds bounds = new CellBounds(0, 0);
    Optional<String> data = bounds.getData(getTestTableContent(), String.class);

    assertTrue(data.isPresent(), "Retrieved data should be populated");
    assertEquals("test", data.get(), "Expected to match the mocked value");
  }

  @Test
  public void testGetDataCastError() {
    CellBounds bounds = new CellBounds(0, 0);
    assertThrows(
        ClassCastException.class,
        () -> bounds.getData(getTestTableContent(), Integer.class),
        "Mocked value is not Integer so Exception is expected");
  }

  @Test
  public void testGetDataNoRow() {
    CellBounds bounds = new CellBounds(1, 0);
    assertThrows(
        Annot8RuntimeException.class,
        () -> bounds.getData(getTestTableContent(), String.class),
        "Exception expected if bounds are valid but a row cannot be found");
  }

  @Test
  public void testGetDataInvalidBounds() {
    CellBounds bounds = new CellBounds(-1, -1);
    assertFalse(
        bounds.getData(getTestTableContent(), String.class).isPresent(),
        "Empty Optional expected if the provided content is not valid");
  }

  private TableContent getTestTableContent() {
    Row row = Mockito.mock(Row.class);
    when(row.getValueAt(anyInt())).thenReturn(Optional.of("test"));

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
