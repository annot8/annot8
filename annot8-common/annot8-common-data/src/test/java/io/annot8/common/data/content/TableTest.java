/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.content;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

public class TableTest {

  @Test
  public void testGetRow() {
    Row row1 = getMockRow(1);
    Row row2 = getMockRow(2);
    Table table = Mockito.mock(Table.class);
    when(table.getRow(Mockito.anyInt())).thenCallRealMethod();
    doAnswer(getRowsAnswer(row1, row2)).when(table).getRows();

    Optional<Row> fetchedRow1 = table.getRow(1);
    Optional<Row> fetchedRow2 = table.getRow(2);
    assertTrue(fetchedRow1.isPresent());
    assertTrue(fetchedRow2.isPresent());
    assertEquals(row1, fetchedRow1.get());
    assertEquals(row2, fetchedRow2.get());
  }

  @Test
  public void testOutOfBoundsGetRow() {
    Table table = Mockito.mock(Table.class);
    when(table.getRow(Mockito.anyInt())).thenCallRealMethod();
    Row row = getMockRow(1);
    doAnswer(getRowsAnswer(row)).when(table).getRows();
    assertFalse(table.getRow(-1).isPresent());
    assertFalse(table.getRow(2).isPresent());
  }

  @Test
  public void testGetRowNoRows() {
    Table table = Mockito.mock(Table.class);
    when(table.getRow(Mockito.anyInt())).thenCallRealMethod();
    doAnswer(getRowsAnswer()).when(table).getRows();
    assertFalse(table.getRow(1).isPresent());
  }

  private Row getMockRow(int rowIndex) {
    Row row = Mockito.mock(Row.class);
    when(row.getRowIndex()).thenReturn(rowIndex);
    return row;
  }

  private Answer<Stream<Row>> getRowsAnswer(Row... rows) {
    return invocation -> {
      if (rows.length == 0) {
        return Stream.empty();
      }
      return Stream.of(rows);
    };
  }
}
