/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.content;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import org.junit.jupiter.api.Test;

public class TableMetadataTest {

  @Test
  public void testTableMetadata() {
    ColumnMetadata columnMetadata = new ColumnMetadata("test", 0);
    TableMetadata metadata =
        new TableMetadata("test", "test", Collections.singletonList(columnMetadata), 1);

    assertEquals("test", metadata.getName());
    assertEquals("test", metadata.getType());
    assertEquals(1, metadata.getColumns().size());
    assertEquals(1, metadata.getRowCount());
    assertEquals(new ColumnMetadata("test", 0), metadata.getColumns().get(0));
  }
}
