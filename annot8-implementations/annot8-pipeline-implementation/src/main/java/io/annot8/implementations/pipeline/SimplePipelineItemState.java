/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.pipeline;

import io.annot8.api.pipelines.ItemStatus;
import io.annot8.api.pipelines.PipelineItemState;
import java.util.HashMap;
import java.util.Map;

public class SimplePipelineItemState implements PipelineItemState {
  private Map<String, ItemStatus> itemHistory = new HashMap<>();

  @Override
  public void setItemStatus(String itemId, ItemStatus status) {
    itemHistory.put(itemId, status);
  }

  @Override
  public Map<String, ItemStatus> getAll() {
    return itemHistory;
  }
}
