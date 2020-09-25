/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.bounds;

import io.annot8.api.data.Content;
import io.annot8.common.data.content.Row;
import io.annot8.common.data.content.TableContent;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

/**
 * Bounds to represent multiple cells within a table row
 *
 * <p>This bounds implementation will only return instances of MultiCellData with getData
 */
public class MultiCellBounds extends AbstractCellBounds {

  private final int[] cells;
  private final int row;

  @JsonbCreator
  public MultiCellBounds(@JsonbProperty("row") int row, @JsonbProperty("cells") int[] cells) {
    this.row = row;
    this.cells = cells;
  }

  public int getRow() {
    return row;
  }

  public int[] getCells() {
    return cells;
  }

  @Override
  public <D, C extends Content<D>, R> Optional<R> getData(C content, Class<R> requiredClass) {
    if (requiredClass.equals(MultiCellData.class)) {
      if (!isValid(content)) {
        return Optional.empty();
      }

      TableContent tableContent = (TableContent) content;
      Optional<Row> rowOptional = tableContent.getData().getRow(this.row);

      if (rowOptional.isPresent()) {
        Row r = rowOptional.get();
        Map<Integer, Object> values = new HashMap<>();
        Map<String, Integer> headers = new HashMap<>();
        for (int i : cells) {
          Optional<Object> valueOptional = r.getValueAt(i);
          Optional<String> headerOptional = r.getColumnName(i);
          if (valueOptional.isPresent()) {
            values.put(i, valueOptional.get());
            headerOptional.ifPresent(s -> headers.put(s, i));
          }
        }

        MultiCellData multiCellData = new MultiCellData(values, headers);
        return Optional.of(requiredClass.cast(multiCellData));
      }
    }
    return Optional.empty();
  }

  @Override
  public <D, C extends Content<D>> boolean isValid(C content) {
    if (!super.isValid(content)) {
      return false;
    }

    for (int i : cells) {
      if (!isCellReferenceValid((TableContent) content, row, i)) {
        return false;
      }
    }

    return true;
  }
}
