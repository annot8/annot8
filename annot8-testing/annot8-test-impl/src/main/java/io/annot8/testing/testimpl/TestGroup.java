/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.testing.testimpl;

import io.annot8.api.annotations.Annotation;
import io.annot8.api.annotations.Group;
import io.annot8.api.properties.ImmutableProperties;
import io.annot8.api.references.AnnotationReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * A group implementation which provides convenience for unit testing.
 *
 * <p>DO NOT USE THIS OUTSIDE A UNIT TEST.
 *
 * <p>It does not have the necessary correctness of implementation, as it stores annotation so
 * changes to the annotation will not be reflected.
 */
public class TestGroup implements Group {

  private String id;

  private ImmutableProperties properties;

  private String type;

  private Map<AnnotationReference, String> annotations = new HashMap<>();

  public TestGroup() {
    this(UUID.randomUUID().toString(), TestConstants.GROUP_TYPE);
  }

  public TestGroup(String id, String type) {
    this.id = id;
    this.type = type;
    this.properties = new TestProperties();
  }

  @Override
  public Map<String, Stream<AnnotationReference>> getReferences() {
    Map<String, Stream<AnnotationReference>> ret = new HashMap<>();

    annotations.forEach(
        (key, value) -> {
          Stream<AnnotationReference> s = ret.getOrDefault(value, Stream.empty());
          ret.put(value, Stream.concat(s, Stream.of(key)));
        });

    return ret;
  }

  public void setAnnotations(Map<AnnotationReference, String> annotations) {
    this.annotations = annotations;
  }

  @Override
  public Optional<String> getRole(Annotation annotation) {
    String role = annotations.get(new TestAnnotationReference(annotation));
    return Optional.ofNullable(role);
  }

  @Override
  public boolean containsAnnotation(Annotation annotation) {
    return annotations.containsKey(new TestAnnotationReference(annotation));
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
    TestGroup testGroup = (TestGroup) o;
    return Objects.equals(id, testGroup.id)
        && Objects.equals(properties, testGroup.properties)
        && Objects.equals(type, testGroup.type)
        && Objects.equals(annotations, testGroup.annotations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, properties, type, annotations);
  }
}
