/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.bounds;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import io.annot8.api.data.Content;
import io.annot8.common.data.content.FileContent;
import io.annot8.common.data.content.Table;
import io.annot8.common.data.content.TableContent;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class AbstractCellBoundsTest {

  @Test
  public void testIsValid() {
    AbstractCellBounds bounds =
        new AbstractCellBounds() {
          @Override
          public <D, C extends Content<D>, R> Optional<R> getData(
              C content, Class<R> requiredClass) {
            return Optional.empty();
          }
        };

    FileContent fileContent = Mockito.mock(FileContent.class);
    TableContent tableContent = Mockito.mock(TableContent.class);

    assertTrue(bounds.isValid(tableContent));
    assertFalse(bounds.isValid(fileContent));
  }

  @Test
  public void testIsCellReferenceValid() {
    AbstractCellBounds bounds =
        new AbstractCellBounds() {
          @Override
          public <D, C extends Content<D>, R> Optional<R> getData(
              C content, Class<R> requiredClass) {
            return Optional.empty();
          }
        };

    TableContent content = Mockito.mock(TableContent.class);
    Table table = Mockito.mock(Table.class);
    when(table.getRowCount()).thenReturn(2);
    when(table.getColumnCount()).thenReturn(2);
    when(content.getData()).thenReturn(table);

    assertTrue(bounds.isCellReferenceValid(content, 0, 0));
    assertTrue(bounds.isCellReferenceValid(content, 0, 1));
    assertTrue(bounds.isCellReferenceValid(content, 1, 0));
    assertTrue(bounds.isCellReferenceValid(content, 1, 1));
    assertFalse(bounds.isCellReferenceValid(content, 2, 0));
    assertFalse(bounds.isCellReferenceValid(content, 0, 2));
  }
}
