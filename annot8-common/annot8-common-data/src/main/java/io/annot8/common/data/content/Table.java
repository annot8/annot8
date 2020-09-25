/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.content;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface Table {

  /** @return number of columns in the table */
  int getColumnCount();

  /** @return number of rows in the table */
  int getRowCount();

  Optional<List<String>> getColumnNames();

  Stream<Row> getRows();

  default Optional<Row> getRow(int index) {
    return getRows().filter(row -> row.getRowIndex() == index).findFirst();
  }
}
