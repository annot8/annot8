/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.reference.annotations;

import io.annot8.api.annotations.Annotation;
import io.annot8.api.bounds.Bounds;
import io.annot8.api.exceptions.IncompleteException;
import io.annot8.api.properties.ImmutableProperties;
import io.annot8.api.properties.MutableProperties;
import io.annot8.api.properties.Properties;
import io.annot8.common.data.properties.EmptyImmutableProperties;
import io.annot8.implementations.support.annotations.AbstractAnnotation;
import io.annot8.implementations.support.properties.MapImmutableProperties;
import io.annot8.implementations.support.properties.MapMutableProperties;
import java.util.Optional;
import java.util.UUID;

/** Simple implementation of Annotation interface */
public class DefaultAnnotation extends AbstractAnnotation {

  private final String id;
  private final String type;
  private final ImmutableProperties properties;
  private final Bounds bounds;
  private final String contentId;

  private DefaultAnnotation(
      final String id,
      final String type,
      final ImmutableProperties properties,
      final Bounds bounds,
      final String contentId) {
    this.id = id;
    this.type = type;
    this.properties = properties;
    this.bounds = bounds;
    this.contentId = contentId;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getType() {
    return type;
  }

  @Override
  public ImmutableProperties getProperties() {
    return properties;
  }

  @Override
  public Bounds getBounds() {
    return bounds;
  }

  @Override
  public String getContentId() {
    return contentId;
  }

  /**
   * AbstractContentBuilder class for DefaultAnnotation, using UUID for generating new IDs and
   * InMemoryImmutableProperties or EmptyImmutableProperties for the properties.
   */
  public static class Builder implements Annotation.Builder {

    private final String contentId;
    private String type = null;
    private MutableProperties properties = new MapMutableProperties();
    private Bounds bounds = null;
    private String id = null;

    public Builder(String contentId) {
      this.contentId = contentId;
    }

    @Override
    public Annotation.Builder withId(String id) {
      this.id = id;
      return this;
    }

    @Override
    public io.annot8.api.annotations.Annotation.Builder withType(String type) {
      this.type = type;
      return this;
    }

    @Override
    public io.annot8.api.annotations.Annotation.Builder withProperty(String key, Object value) {
      properties.set(key, value);
      return this;
    }

    @Override
    public Annotation.Builder withPropertyIfPresent(String key, Optional<?> value) {
      value.ifPresent(o -> properties.set(key, o));
      return this;
    }

    @Override
    public Annotation.Builder withoutProperty(String key, Object value) {
      Optional<Object> val = properties.get(key);
      if (val.isPresent() && val.get().equals(value)) {
        properties.remove(key);
      }

      return this;
    }

    @Override
    public Annotation.Builder withoutProperty(String key) {
      properties.remove(key);
      return this;
    }

    @Override
    public io.annot8.api.annotations.Annotation.Builder withProperties(Properties properties) {
      this.properties = new MapMutableProperties(properties);
      return this;
    }

    @Override
    public io.annot8.api.annotations.Annotation.Builder newId() {
      this.id = null;
      return this;
    }

    @Override
    public io.annot8.api.annotations.Annotation.Builder from(Annotation from) {
      this.id = from.getId();
      this.type = from.getType();
      this.properties = new MapMutableProperties(from.getProperties());
      this.bounds = from.getBounds();

      return this;
    }

    @Override
    public Annotation save() {
      if (id == null) {
        id = UUID.randomUUID().toString();
      }

      if (type == null) {
        throw new IncompleteException("Type has not been set");
      }

      if (bounds == null) {
        throw new IncompleteException("Bounds has not been set");
      }

      if (contentId == null) {
        throw new IncompleteException("Content ID has not been set");
      }

      ImmutableProperties immutableProperties;
      if (properties.getAll().isEmpty()) {
        immutableProperties = EmptyImmutableProperties.getInstance();
      } else {
        immutableProperties = new MapImmutableProperties.Builder().from(properties).save();
      }

      return new DefaultAnnotation(id, type, immutableProperties, bounds, contentId);
    }

    @Override
    public io.annot8.api.annotations.Annotation.Builder withBounds(Bounds bounds) {
      this.bounds = bounds;
      return this;
    }
  }
}
