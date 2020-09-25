/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.support.listeners;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.BiConsumer;

public class Listeners<L, E> {

  private final BiConsumer<L, E> publish;
  private Set<L> set = null;

  public Listeners(BiConsumer<L, E> publish) {
    this.publish = publish;
  }

  public Deregister register(L listener) {
    if (publish == null) {
      return () -> {};
    }

    Objects.requireNonNull(listener);

    if (set == null) {
      set = new CopyOnWriteArraySet<>();
    }

    set.add(listener);

    return () -> deregister(listener);
  }

  public void deregister(L listener) {
    if (set != null) {
      set.remove(listener);
    }
  }

  public void fire(E event) {
    if (set != null && event != null && publish != null) {
      set.forEach(l -> publish.accept(l, event));
    }
  }

  public void clear() {
    if (set != null) {
      set.clear();
    }
  }
}
