/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.content;

import java.util.List;
import java.util.Optional;

public class DefaultRow implements Row {

  private final int index;
  private final List<String> columnNames;
  private final List<Object> data;

  public DefaultRow(int index, List<String> columnNames, List<Object> data) {
    this.index = index;
    this.columnNames = columnNames;
    this.data = data;
  }

  @Override
  public List<String> getColumnNames() {
    return columnNames;
  }

  @Override
  public int getColumnCount() {
    return data.size();
  }

  @Override
  public int getRowIndex() {
    return index;
  }

  @Override
  public Optional<Object> getValueAt(int index) {
    if (index < 0 || index > data.size() - 1) {
      return Optional.empty();
    }
    return Optional.ofNullable(data.get(index));
  }
}
