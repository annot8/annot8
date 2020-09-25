/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.support.stores;

import io.annot8.api.data.Item;
import io.annot8.api.data.ItemFactory;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

public class QueueItemFactory extends NotifyingItemFactory {

  private final Queue<Item> queue;

  public QueueItemFactory(ItemFactory itemFactory) {
    this(itemFactory, new LinkedList<>());
  }

  public QueueItemFactory(ItemFactory itemFactory, Queue<Item> queue) {
    super(itemFactory);
    this.queue = queue;
    // All new items go on the queue
    register(queue::add);
  }

  public void clear() {
    queue.clear();
  }

  public boolean isEmpty() {
    return queue.isEmpty();
  }

  public Optional<Item> next() {
    return Optional.ofNullable(queue.poll());
  }
}
