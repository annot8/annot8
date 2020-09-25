/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.support.content;

import io.annot8.api.data.Content;
import io.annot8.api.data.Item;
import io.annot8.api.exceptions.IncompleteException;
import io.annot8.api.properties.ImmutableProperties;
import io.annot8.api.properties.Properties;
import io.annot8.implementations.support.properties.MapImmutableProperties;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public abstract class AbstractContentBuilder<D, C extends Content<D>>
    implements Content.Builder<C, D> {

  private final Item item;
  private final ImmutableProperties.Builder properties = new MapImmutableProperties.Builder();
  private String id;
  private String description;

  private Supplier<D> data;

  protected AbstractContentBuilder(Item item) {
    this.item = item;
  }

  protected Item getItem() {
    return item;
  }

  @Override
  public Content.Builder<C, D> withId(String id) {
    this.id = id;
    return this;
  }

  @Override
  public Content.Builder<C, D> withDescription(String description) {
    this.description = description;
    return this;
  }

  @Override
  public Content.Builder<C, D> withData(Supplier<D> data) {
    this.data = data;
    return this;
  }

  @Override
  public Content.Builder<C, D> from(C from) {
    return this;
  }

  @Override
  public Content.Builder<C, D> withProperty(String key, Object value) {
    properties.withProperty(key, value);
    return this;
  }

  @Override
  public Content.Builder<C, D> withPropertyIfPresent(String key, Optional<?> value) {
    value.ifPresent(o -> properties.withProperty(key, o));
    return this;
  }

  @Override
  public Content.Builder<C, D> withProperties(Properties properties) {
    this.properties.withProperties(properties);
    return this;
  }

  @Override
  public Content.Builder<C, D> withoutProperty(String key, Object value) {
    properties.withoutProperty(key, value);
    return this;
  }

  @Override
  public Content.Builder<C, D> withoutProperty(String key) {
    properties.withoutProperty(key);
    return this;
  }

  @Override
  public C save() throws IncompleteException {
    if (id == null) {
      id = UUID.randomUUID().toString();
    }

    if (data == null) {
      throw new IncompleteException("Data supplier is required");
    }

    return create(id, description, properties.save(), data);
  }

  protected abstract C create(
      String id, String description, ImmutableProperties properties, Supplier<D> data);
}
