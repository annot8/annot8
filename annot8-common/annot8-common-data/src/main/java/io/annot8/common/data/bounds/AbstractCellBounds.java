/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.bounds;

import io.annot8.api.bounds.Bounds;
import io.annot8.api.data.Content;
import io.annot8.common.data.content.Table;
import io.annot8.common.data.content.TableContent;

public abstract class AbstractCellBounds implements Bounds {

  @Override
  public <D, C extends Content<D>> boolean isValid(C content) {
    return content instanceof TableContent;
  }

  protected boolean isCellReferenceValid(TableContent content, int row, int column) {
    Table table = content.getData();
    if (row < 0 || table.getRowCount() <= row) {
      return false;
    }
    return column >= 0 && column < table.getColumnCount();
  }
}
