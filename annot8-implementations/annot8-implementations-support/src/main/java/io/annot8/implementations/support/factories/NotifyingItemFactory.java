/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.support.factories;

import io.annot8.api.data.Item;
import io.annot8.api.data.ItemFactory;
import io.annot8.implementations.support.data.NotifyingItem;
import io.annot8.implementations.support.listeners.Deregister;
import io.annot8.implementations.support.listeners.Listenable;
import io.annot8.implementations.support.listeners.Listeners;
import java.util.function.Consumer;

public class NotifyingItemFactory implements ItemFactory, Listenable<Consumer<Item>> {

  private final Listeners<Consumer<Item>, Item> listeners = new Listeners<>(Consumer::accept);
  private final ItemFactory itemFactory;

  public NotifyingItemFactory(ItemFactory itemFactory) {
    this.itemFactory = itemFactory;
  }

  public Deregister register(Consumer<Item> consumer) {
    return listeners.register(consumer);
  }

  public void deregister(Consumer<Item> consumer) {
    listeners.deregister(consumer);
  }

  @Override
  public Item create() {
    Item item = new NotifyingItem(itemFactory.create(), this);
    listeners.fire(item);
    return item;
  }

  @Override
  public Item create(String id) {
    Item item = new NotifyingItem(itemFactory.create(id), this);
    listeners.fire(item);
    return item;
  }

  @Override
  public Item create(Item parent) {
    Item item = new NotifyingItem(itemFactory.create(parent), this);
    listeners.fire(item);
    return item;
  }

  @Override
  public Item create(Item parent, String id) {
    Item item = new NotifyingItem(itemFactory.create(parent, id), this);
    listeners.fire(item);
    return item;
  }

  @Override
  public Item create(Consumer<Item> func) {
    Item item = new NotifyingItem(itemFactory.create(func), this);
    listeners.fire(item);
    return item;
  }

  @Override
  public Item create(String id, Consumer<Item> func) {
    Item item = new NotifyingItem(itemFactory.create(id, func), this);
    listeners.fire(item);
    return item;
  }

  @Override
  public Item create(Item parent, Consumer<Item> func) {
    Item item = new NotifyingItem(itemFactory.create(parent, func), this);
    listeners.fire(item);
    return item;
  }

  @Override
  public Item create(Item parent, String id, Consumer<Item> func) {
    Item item = new NotifyingItem(itemFactory.create(parent, id, func), this);
    listeners.fire(item);
    return item;
  }
}
