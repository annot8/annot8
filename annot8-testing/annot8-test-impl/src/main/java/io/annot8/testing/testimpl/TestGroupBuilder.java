/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.testing.testimpl;

import io.annot8.api.annotations.Annotation;
import io.annot8.api.annotations.Group;
import io.annot8.api.annotations.Group.Builder;
import io.annot8.api.properties.Properties;
import io.annot8.api.references.AnnotationReference;
import io.annot8.implementations.support.factories.GroupBuilderFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class TestGroupBuilder implements Group.Builder {

  private final Map<AnnotationReference, String> annotations = new HashMap<>();
  private final TestProperties properties = new TestProperties();
  private String id = UUID.randomUUID().toString();
  private String type = TestConstants.GROUP_TYPE;

  public static GroupBuilderFactory factory() {
    return (item, store) -> new TestGroupBuilder();
  }

  @Override
  public Builder withAnnotation(String role, Annotation annotation) {
    annotations.put(new TestAnnotationReference(annotation), role);
    return this;
  }

  @Override
  public Group save() {
    TestGroup group = new TestGroup(id, type);
    group.setProperties(properties);
    group.setAnnotations(annotations);
    return group;
  }

  @Override
  public Builder from(Group from) {
    id = from.getId();
    type = from.getType();
    properties.add(from.getProperties().getAll());
    from.getAnnotations()
        .forEach(
            (key, value) ->
                value.forEach(a -> annotations.put(new TestAnnotationReference(a), key)));
    return this;
  }

  @Override
  public Builder withId(String id) {
    this.id = id;
    return this;
  }

  @Override
  public Builder newId() {
    id = null;
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
