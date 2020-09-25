/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.testing.testimpl.components;

import io.annot8.api.components.Processor;
import io.annot8.api.components.responses.ProcessorResponse;
import io.annot8.api.data.Item;
import java.util.LinkedList;
import java.util.List;

public class ItemCollector implements Processor {

  private final List<Item> items = new LinkedList<>();

  @Override
  public ProcessorResponse process(Item item) {
    items.add(item);
    return ProcessorResponse.ok();
  }

  public List<Item> getItems() {
    return items;
  }

  public void clear() {
    items.clear();
  }
}
