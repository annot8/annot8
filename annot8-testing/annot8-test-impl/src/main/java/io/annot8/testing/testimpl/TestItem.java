/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.testing.testimpl;

import io.annot8.api.data.Content;
import io.annot8.api.data.Content.Builder;
import io.annot8.api.data.Item;
import io.annot8.api.data.ItemFactory;
import io.annot8.api.exceptions.IncompleteException;
import io.annot8.api.exceptions.UnsupportedContentException;
import io.annot8.api.properties.MutableProperties;
import io.annot8.api.stores.GroupStore;
import io.annot8.common.utils.java.StreamUtils;
import io.annot8.implementations.support.delegates.DelegateContentBuilder;
import io.annot8.implementations.support.factories.ContentBuilderFactory;
import io.annot8.implementations.support.registries.ContentBuilderFactoryRegistry;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class TestItem implements Item {

  private final String parentId;

  private final String id;
  private MutableProperties properties;
  private GroupStore groups;
  private ContentBuilderFactoryRegistry contentBuilderFactoryRegistry;
  private Map<String, Content<?>> content = new ConcurrentHashMap<>();

  private boolean discarded = false;

  private final ItemFactory itemFactory;

  public TestItem() {
    this(new TestGroupStore());
  }

  public TestItem(String parentId) {
    this(new TestGroupStore(), parentId);
  }

  public TestItem(GroupStore groupStore) {
    this(groupStore, new TestContentBuilderFactoryRegistry());
  }

  public TestItem(GroupStore groupStore, String parentId) {
    this(groupStore, new TestContentBuilderFactoryRegistry(), parentId);
  }

  public TestItem(
      GroupStore groupStore, ContentBuilderFactoryRegistry contentBuilderFactoryRegistry) {
    this(groupStore, contentBuilderFactoryRegistry, null);
  }

  public TestItem(
      GroupStore groupStore,
      ContentBuilderFactoryRegistry contentBuilderFactoryRegistry,
      String parentId) {
    this(new TestItemFactory(), groupStore, contentBuilderFactoryRegistry, parentId);
  }

  public TestItem(
      ItemFactory itemFactory,
      GroupStore groupStore,
      ContentBuilderFactoryRegistry contentBuilderFactoryRegistry,
      String parentId) {
    this.itemFactory = itemFactory;
    this.id = UUID.randomUUID().toString();
    this.parentId = parentId;
    this.properties = new TestProperties();
    this.groups = groupStore;
    this.contentBuilderFactoryRegistry = contentBuilderFactoryRegistry;
  }

  @Override
  public Optional<Content<?>> getContent(String id) {
    return Optional.ofNullable(content.get(id));
  }

  @Override
  public Stream<Content<?>> getContents() {
    return content.values().stream();
  }

  @Override
  public <T extends Content<?>> Stream<T> getContents(Class<T> clazz) {
    return StreamUtils.cast(getContents(), clazz);
  }

  @Override
  public String getId() {
    return id;
  }

  public Optional<String> getParent() {
    return Optional.ofNullable(parentId);
  }

  @Override
  public <C extends Content<D>, D> Builder<C, D> createContent(Class<C> clazz) {
    Optional<ContentBuilderFactory<D, C>> optional = contentBuilderFactoryRegistry.get(clazz);

    if (optional.isEmpty()) {
      throw new UnsupportedContentException("No content builder factory " + clazz.getSimpleName());
    }

    ContentBuilderFactory<D, C> factory = optional.get();

    Builder<C, D> builder = factory.create(this);
    return new DelegateContentBuilder<>(builder) {
      @Override
      public C save() {
        return TestItem.this.save(super.save());
      }
    };
  }

  public <C extends Content<D>, D> C save(Builder<C, D> builder) {
    C c;
    try {
      c = builder.save();
    } catch (IncompleteException e) {
      throw new AssertionError(e.getMessage());
    }

    return save(c);
  }

  public <D, C extends Content<D>> C save(C c) {
    content.put(c.getId(), c);
    return c;
  }

  @Override
  public void removeContent(String id) {
    content.remove(id);
  }

  @Override
  public GroupStore getGroups() {
    return groups;
  }

  public void setGroups(GroupStore groups) {
    this.groups = groups;
  }

  @Override
  public MutableProperties getProperties() {
    return properties;
  }

  public void setProperties(MutableProperties properties) {
    this.properties = properties;
  }

  public ContentBuilderFactoryRegistry getContentBuilderFactoryRegistry() {
    return contentBuilderFactoryRegistry;
  }

  public void setContentBuilderFactoryRegistry(
      ContentBuilderFactoryRegistry contentBuilderFactoryRegistry) {
    this.contentBuilderFactoryRegistry = contentBuilderFactoryRegistry;
  }

  public Map<String, Content<?>> getContent() {
    return content;
  }

  public void setContent(Map<String, Content<?>> content) {
    this.content = content;
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
  public Item createChild() {
    return itemFactory == null ? null : itemFactory.create();
  }

  public ItemFactory getItemFactory() {
    return itemFactory;
  }
}
