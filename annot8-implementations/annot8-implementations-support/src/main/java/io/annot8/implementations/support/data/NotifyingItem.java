/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.support.data;

import io.annot8.api.data.Content;
import io.annot8.api.data.Item;
import io.annot8.api.properties.MutableProperties;
import io.annot8.api.stores.GroupStore;
import io.annot8.implementations.support.factories.NotifyingItemFactory;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A wrapper for an Item that allows it to be used within a NotifyingItemFactory without losing
 * notifications when child items are created.
 */
public class NotifyingItem implements Item {

  private final Item item;
  private final NotifyingItemFactory notifyingItemFactory;

  public NotifyingItem(Item item, NotifyingItemFactory notifyingItemFactory) {
    this.item = item;
    this.notifyingItemFactory = notifyingItemFactory;
  }

  public Item getOriginalItem() {
    return item;
  }

  @Override
  public Optional<String> getParent() {
    return item.getParent();
  }

  @Override
  public Optional<Content<?>> getContent(String id) {
    return item.getContent(id);
  }

  @Override
  public Stream<Content<?>> getContents() {
    return item.getContents();
  }

  @Override
  public <C extends Content<D>, D> Content.Builder<C, D> createContent(Class<C> clazz) {
    return item.createContent(clazz);
  }

  @Override
  public void removeContent(String id) {
    item.removeContent(id);
  }

  @Override
  public void discard() {
    item.discard();
  }

  @Override
  public boolean isDiscarded() {
    return item.isDiscarded();
  }

  @Override
  public Item createChild() {
    return notifyingItemFactory.create(item);
  }

  @Override
  public GroupStore getGroups() {
    return item.getGroups();
  }

  @Override
  public String getId() {
    return item.getId();
  }

  @Override
  public MutableProperties getProperties() {
    return item.getProperties();
  }
}
