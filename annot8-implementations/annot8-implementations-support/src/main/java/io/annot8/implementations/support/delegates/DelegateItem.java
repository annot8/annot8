/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.support.delegates;

import io.annot8.api.data.Content;
import io.annot8.api.data.Content.Builder;
import io.annot8.api.data.Item;
import io.annot8.api.filters.Filter;
import io.annot8.api.properties.MutableProperties;
import io.annot8.api.stores.GroupStore;
import java.util.Optional;
import java.util.stream.Stream;

public class DelegateItem implements Item {

  private final Item item;

  public DelegateItem(Item item) {
    this.item = item;
  }

  @Override
  public Optional<String> getParent() {
    return item.getParent();
  }

  @Override
  public boolean hasParent() {
    return item.hasParent();
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
  public <T extends Content<?>> Stream<T> getContents(Class<T> clazz) {
    return item.getContents(clazz);
  }

  @Override
  public <C extends Content<D>, D> Builder<C, D> createContent(Class<C> clazz) {
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
    return item.createChild();
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

  @Override
  public Optional<Content<?>> find(Filter<Content<?>> filter) {
    return item.find(filter);
  }

  @Override
  public Stream<Content<?>> filter(Filter<Content<?>> filter) {
    return item.filter(filter);
  }
}
