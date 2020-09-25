/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.reference.data;

import io.annot8.api.data.Content;
import io.annot8.api.data.Content.Builder;
import io.annot8.api.data.Item;
import io.annot8.api.data.ItemFactory;
import io.annot8.api.exceptions.IncompleteException;
import io.annot8.api.exceptions.UnsupportedContentException;
import io.annot8.api.properties.MutableProperties;
import io.annot8.api.stores.GroupStore;
import io.annot8.implementations.reference.stores.DefaultGroupStore;
import io.annot8.implementations.support.delegates.DelegateContentBuilder;
import io.annot8.implementations.support.factories.ContentBuilderFactory;
import io.annot8.implementations.support.properties.MapMutableProperties;
import io.annot8.implementations.support.registries.ContentBuilderFactoryRegistry;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class DefaultItem implements Item {

  private final Map<String, Content<?>> contents = new ConcurrentHashMap<>();
  private final MutableProperties properties = new MapMutableProperties();
  private final ContentBuilderFactoryRegistry contentBuilderFactoryRegistry;
  private final DefaultGroupStore groups;
  private final String id;
  private final String parentId;
  private final ItemFactory itemFactory;
  private boolean discarded = false;

  public DefaultItem(
      String parentId,
      ItemFactory itemFactory,
      ContentBuilderFactoryRegistry contentBuilderFactoryRegistry) {
    this.parentId = parentId;
    this.itemFactory = itemFactory;
    this.id = UUID.randomUUID().toString();
    this.contentBuilderFactoryRegistry = contentBuilderFactoryRegistry;
    this.groups = new DefaultGroupStore(this);
  }

  public DefaultItem(
      ItemFactory itemFactory, ContentBuilderFactoryRegistry contentBuilderFactoryRegistry) {
    this(null, itemFactory, contentBuilderFactoryRegistry);
  }

  public Optional<String> getParent() {
    return Optional.ofNullable(parentId);
  }

  @Override
  public Optional<Content<?>> getContent(String id) {
    return Optional.ofNullable(contents.get(id));
  }

  @Override
  public Stream<Content<?>> getContents() {
    return contents.values().stream();
  }

  @Override
  public <C extends Content<D>, D> Builder<C, D> createContent(Class<C> clazz) {
    Optional<ContentBuilderFactory<D, C>> factory = contentBuilderFactoryRegistry.get(clazz);

    if (factory.isEmpty()) {
      throw new UnsupportedContentException("Unknown content type: " + clazz.getSimpleName());
    }

    return new DelegateContentBuilder<>(factory.get().create(this)) {
      @Override
      public C save() throws IncompleteException {
        C c = super.save();
        return DefaultItem.this.save(c);
      }
    };
  }

  private <D, C extends Content<D>> C save(C c) {
    assert c != null;
    contents.put(c.getId(), c);
    return c;
  }

  @Override
  public void removeContent(String id) {
    contents.remove(id);
  }

  @Override
  public GroupStore getGroups() {
    return groups;
  }

  @Override
  public MutableProperties getProperties() {
    return properties;
  }

  @Override
  public void discard() {
    discarded = true;
  }

  @Override
  public boolean isDiscarded() {
    return discarded;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public Item createChild() {
    return itemFactory.create(this);
  }
}
