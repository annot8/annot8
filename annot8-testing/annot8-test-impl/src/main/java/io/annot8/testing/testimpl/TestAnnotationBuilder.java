/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.testing.testimpl;

import io.annot8.api.annotations.Annotation;
import io.annot8.api.annotations.Annotation.Builder;
import io.annot8.api.bounds.Bounds;
import io.annot8.api.properties.MutableProperties;
import io.annot8.api.properties.Properties;
import io.annot8.implementations.support.factories.AnnotationBuilderFactory;
import java.util.Optional;
import java.util.UUID;

public class TestAnnotationBuilder implements Annotation.Builder {

  private final MutableProperties properties = new TestProperties();
  private String contentId;
  private Bounds bounds;
  private String id;
  private String type;

  public TestAnnotationBuilder(String contentId) {
    this.contentId = contentId;
  }

  public static AnnotationBuilderFactory factory() {
    return (content, store) -> new TestAnnotationBuilder(content.getId());
  }

  @Override
  public Builder withId(String id) {
    this.id = id;
    return this;
  }

  @Override
  public Builder withBounds(Bounds bounds) {
    this.bounds = bounds;
    return this;
  }

  @Override
  public Annotation save() {
    String assignedId = this.id == null ? UUID.randomUUID().toString() : this.id;

    TestAnnotation annotation = new TestAnnotation();
    annotation.setBounds(bounds);
    annotation.setContentId(contentId);
    annotation.setId(assignedId);
    annotation.setType(type);
    TestProperties testProperties = new TestProperties();
    testProperties.add(properties.getAll());
    annotation.setProperties(testProperties);

    return annotation;
  }

  @Override
  public Builder from(Annotation from) {
    contentId = from.getContentId();
    bounds = from.getBounds();
    id = from.getId();
    type = from.getType();
    properties.add(from.getProperties().getAll());
    return this;
  }

  @Override
  public Builder newId() {
    this.id = null;
    return this;
  }

  @Override
  public Builder withProperty(String key, Object value) {
    this.properties.set(key, value);
    return this;
  }

  @Override
  public Builder withPropertyIfPresent(String key, Optional<?> value) {
    value.ifPresent(o -> properties.set(key, o));
    return this;
  }

  @Override
  public Builder withoutProperty(String key, Object value) {
    Optional<Object> opt = this.properties.get(key);
    if (opt.isPresent() && opt.get().equals(value)) {
      this.properties.remove(key);
    }

    return this;
  }

  @Override
  public Builder withoutProperty(String key) {
    this.properties.remove(key);
    return this;
  }

  @Override
  public Builder withProperties(Properties properties) {
    this.properties.add(properties.getAll());
    return this;
  }

  @Override
  public Builder withType(String type) {
    this.type = type;
    return this;
  }
}
