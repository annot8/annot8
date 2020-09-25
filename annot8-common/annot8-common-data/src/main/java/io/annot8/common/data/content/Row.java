/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.content;

import io.annot8.common.utils.java.ConversionUtils;
import java.util.List;
import java.util.Optional;

public interface Row {

  List<String> getColumnNames();

  int getColumnCount();

  int getRowIndex();

  Optional<Object> getValueAt(int index);

  default Optional<String> getColumnName(int index) {
    if (getColumnNames() == null || getColumnNames().isEmpty() || index > getColumnCount()) {
      return Optional.empty();
    }
    return Optional.ofNullable(getColumnNames().get(index));
  }

  default Optional<String> getString(int index) {
    return getValueAt(index).map(Object::toString);
  }

  default Optional<Integer> getInt(int index) {
    return ConversionUtils.toInt(getValueAt(index));
  }

  default Optional<Long> getLong(int index) {
    return ConversionUtils.toLong(getValueAt(index));
  }

  default Optional<Double> getDouble(int index) {
    return ConversionUtils.toDouble(getValueAt(index));
  }

  default Optional<Object> getValueAt(String columnName) {
    return getIndex(columnName)
        .map(this::getValueAt)
        .filter(Optional::isPresent)
        .map(Optional::get);
  }

  default Optional<String> getString(String columnName) {
    return getValueAt(columnName).map(Object::toString);
  }

  default Optional<Integer> getInt(String columnName) {
    return ConversionUtils.toInt(getValueAt(columnName));
  }

  default Optional<Long> getLong(String columnName) {
    return ConversionUtils.toLong(getValueAt(columnName));
  }

  default Optional<Double> getDouble(String columnName) {
    return ConversionUtils.toDouble(getValueAt(columnName));
  }

  default Optional<Integer> getIndex(String columnName) {
    if (getColumnNames() != null && getColumnNames().contains(columnName)) {
      return Optional.of(getColumnNames().indexOf(columnName));
    }
    return Optional.empty();
  }

  default <T> Optional<T> getObject(int index, Class<T> requiredClass) {
    return getValueAt(index)
        .map(
            v -> {
              try {
                return requiredClass.cast(v);
              } catch (ClassCastException e) {
                return null;
              }
            });
  }

  default <T> Optional<T> getObject(String columnName, Class<T> requiredClass) {
    return getIndex(columnName)
        .map(i -> this.getObject(i, requiredClass))
        .filter(Optional::isPresent)
        .map(Optional::get);
  }
}
