/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.support.content;

import io.annot8.api.data.Content;
import io.annot8.api.data.Item;
import io.annot8.api.properties.ImmutableProperties;
import io.annot8.api.stores.AnnotationStore;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AbstractContent<D> implements Content<D> {

  private final Class<D> dataClass;
  private final Class<? extends Content<D>> contentClass;

  private final String id;
  private final String description;
  private final AnnotationStore annotations;
  private final ImmutableProperties properties;
  private final Supplier<D> data;
  private final Item item;

  protected AbstractContent(
      Item item,
      Class<D> dataClass,
      Class<? extends Content<D>> contentClass,
      Function<Content<?>, AnnotationStore> annotations,
      String id,
      String description,
      ImmutableProperties properties,
      Supplier<D> data) {
    this.item = item;
    this.dataClass = dataClass;
    this.contentClass = contentClass;
    this.id = id;
    this.description = description;
    this.properties = properties;
    this.data = data;
    this.annotations = annotations.apply(this);
  }

  @Override
  public Item getItem() {
    return item;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public D getData() {
    return data.get();
  }

  @Override
  public Class<D> getDataClass() {
    return dataClass;
  }

  @Override
  public Class<? extends Content<D>> getContentClass() {
    return contentClass;
  }

  @Override
  public AnnotationStore getAnnotations() {
    return annotations;
  }

  @Override
  public ImmutableProperties getProperties() {
    return properties;
  }
}
