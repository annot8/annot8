/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.bounds;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class MultiCellData {

  private final Map<Integer, Object> data;
  private final Map<String, Integer> columnNames;

  public MultiCellData(Map<Integer, Object> data, Map<String, Integer> columnNames) {
    this.data = data;
    this.columnNames = columnNames;
  }

  public Collection<Integer> getColumns() {
    return data.keySet();
  }

  public Map<String, Integer> getColumnNames() {
    return columnNames;
  }

  public Map<Integer, Object> getData() {
    return data;
  }

  public <T> Optional<T> getDataForColumn(String columnName, Class<T> requiredClass) {
    if (getColumnNames().containsKey(columnName)) {
      return getDataForColumn(getColumnNames().get(columnName), requiredClass);
    }
    return Optional.empty();
  }

  public <T> Optional<T> getDataForColumn(int column, Class<T> requiredClass) {
    if (data.containsKey(column)) {
      Object value = data.get(column);
      if (requiredClass.isAssignableFrom(value.getClass())) {
        return Optional.of(requiredClass.cast(value));
      }
    }
    return Optional.empty();
  }
}
