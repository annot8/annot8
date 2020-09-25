/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.support.delegates;

import io.annot8.api.data.Content;
import io.annot8.api.data.Content.Builder;
import io.annot8.api.properties.Properties;
import java.util.Optional;
import java.util.function.Supplier;

public class DelegateContentBuilder<C extends Content<D>, D> implements Builder<C, D> {

  private final Builder<C, D> delegate;

  public DelegateContentBuilder(Builder<C, D> delegate) {
    this.delegate = delegate;
  }

  @Override
  public Builder<C, D> withDescription(String description) {
    delegate.withDescription(description);
    return this;
  }

  @Override
  public Builder<C, D> withData(Supplier<D> data) {
    delegate.withData(data);
    return this;
  }

  @Override
  public Builder<C, D> from(C from) {
    delegate.from(from);
    return this;
  }

  @Override
  public Builder<C, D> withId(String id) {
    delegate.withId(id);
    return this;
  }

  @Override
  public Builder<C, D> withProperty(String key, Object value) {
    delegate.withProperty(key, value);
    return this;
  }

  @Override
  public Builder<C, D> withPropertyIfPresent(String key, Optional<?> value) {
    value.ifPresent(o -> delegate.withProperty(key, o));
    return this;
  }

  @Override
  public Builder<C, D> withoutProperty(String key, Object value) {
    delegate.withoutProperty(key, value);
    return this;
  }

  @Override
  public Builder<C, D> withoutProperty(String key) {
    delegate.withoutProperty(key);
    return this;
  }

  @Override
  public Builder<C, D> withProperties(Properties properties) {
    delegate.withProperties(properties);
    return this;
  }

  @Override
  public C save() {
    return delegate.save();
  }
}
