/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.testing.testimpl;

import io.annot8.api.annotations.Annotation;
import io.annot8.api.bounds.Bounds;
import io.annot8.api.properties.ImmutableProperties;
import java.util.Objects;
import java.util.UUID;

/**
 * An annotation for use (only) in unit tests.
 *
 * <p>DO NOT USE THIS OUTSIDE A UNIT TEST.
 *
 * <p>This does not have the necessary correctness of behaviour, for example is it mutable.
 */
public class TestAnnotation implements Annotation {

  private Bounds bounds;
  private String content;
  private String id;
  private ImmutableProperties properties;
  private String type;

  public TestAnnotation() {
    this(UUID.randomUUID().toString(), TestConstants.CONTENT_DESCRIPTION);
  }

  public TestAnnotation(String id, String content) {
    this(id, content, TestConstants.ANNOTATION_TYPE);
  }

  public TestAnnotation(String id, String content, String type) {
    this(id, content, type, new TestBounds());
  }

  public TestAnnotation(String id, String content, String type, Bounds bounds) {
    this.id = id;
    this.content = content;
    this.type = type;
    this.bounds = bounds;
    this.properties = new TestProperties();
  }

  @Override
  public Bounds getBounds() {
    return bounds;
  }

  public void setBounds(Bounds bounds) {
    this.bounds = bounds;
  }

  @Override
  public String getContentId() {
    return content;
  }

  public void setContentId(String content) {
    this.content = content;
  }

  @Override
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public ImmutableProperties getProperties() {
    return properties;
  }

  public void setProperties(ImmutableProperties properties) {
    this.properties = properties;
  }

  @Override
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TestAnnotation that = (TestAnnotation) o;
    return Objects.equals(bounds, that.bounds)
        && Objects.equals(content, that.content)
        && Objects.equals(id, that.id)
        && Objects.equals(properties, that.properties)
        && Objects.equals(type, that.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bounds, content, id, properties, type);
  }

  @Override
  public String toString() {
    return String.format("TestAnnotation[%s]", getId());
  }
}
