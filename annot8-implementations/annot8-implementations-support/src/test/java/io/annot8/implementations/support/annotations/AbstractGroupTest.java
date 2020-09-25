/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.support.annotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import io.annot8.api.annotations.Annotation;
import io.annot8.api.annotations.Group;
import io.annot8.api.properties.ImmutableProperties;
import io.annot8.api.references.AnnotationReference;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

public class AbstractGroupTest {

  @Test
  public void testBasicEquals() {
    String groupId = "groupId";
    String groupType = "groupType";
    Group group = new TestGroup(groupId, groupType, Collections.emptyMap(), Collections.emptyMap());
    Group other = new TestGroup(groupId, groupType, Collections.emptyMap(), Collections.emptyMap());

    assertEquals(group, group);
    assertEquals(group, other);
    assertEquals(group.hashCode(), other.hashCode());
    assertNotEquals(null, group);
    assertNotEquals(group, new Object());
  }

  @Test
  public void testEqualsWithProperties() {
    String groupId = "groupId";
    String groupType = "groupType";
    String key = "key";
    String value = "value";
    Group group =
        new TestGroup(
            groupId, groupType, Collections.emptyMap(), Collections.singletonMap(key, value));
    Group other =
        new TestGroup(
            groupId, groupType, Collections.emptyMap(), Collections.singletonMap(key, value));

    assertEquals(group, other);
    assertEquals(group.hashCode(), other.hashCode());

    Group differentProperties =
        new TestGroup(
            groupId, groupType, Collections.emptyMap(), Collections.singletonMap(key, "diffValue"));

    assertNotEquals(group, differentProperties);
  }

  @Test
  public void testEqualsWithReferences() {
    String groupId = "groupId";
    String groupType = "groupType";
    String annotationId = "annoId";
    String contentId = "contentId";
    String role = "role";
    AnnotationReference reference = getAnnotationReference(annotationId, contentId);

    Group group =
        new TestGroup(
            groupId,
            groupType,
            Collections.singletonMap(role, Collections.singletonList(reference)),
            Collections.emptyMap());
    Group other =
        new TestGroup(
            groupId,
            groupType,
            Collections.singletonMap(role, Collections.singletonList(reference)),
            Collections.emptyMap());

    assertEquals(group, other);

    AnnotationReference diffReference = getAnnotationReference("diffId", "diffContentId");

    Group differentReference =
        new TestGroup(
            groupId,
            groupType,
            Collections.singletonMap(role, Collections.singletonList(diffReference)),
            Collections.emptyMap());

    assertNotEquals(group, differentReference);

    Group differentRole =
        new TestGroup(
            groupId,
            groupType,
            Collections.singletonMap("diffRole", Collections.singletonList(reference)),
            Collections.emptyMap());

    assertNotEquals(group, differentRole);
  }

  private AnnotationReference getAnnotationReference(String annotationId, String contentId) {
    return new AnnotationReference() {

      @Override
      public Optional<Annotation> toAnnotation() {
        return Optional.empty();
      }

      @Override
      public String getContentId() {
        return contentId;
      }

      @Override
      public String getAnnotationId() {
        return annotationId;
      }
    };
  }

  private class TestGroup extends AbstractGroup {

    private final String id;
    private final String type;
    private final Map<String, Collection<AnnotationReference>> refs;
    private final Map<String, Object> properties;

    public TestGroup(
        String id,
        String type,
        Map<String, Collection<AnnotationReference>> refs,
        Map<String, Object> properties) {
      this.id = id;
      this.type = type;
      this.refs = refs;
      this.properties = properties;
    }

    @Override
    public Map<String, Stream<AnnotationReference>> getReferences() {
      return refs.entrySet().stream()
          .collect(Collectors.toMap(Entry::getKey, v -> v.getValue().stream()));
    }

    @Override
    public Optional<String> getRole(Annotation annotation) {
      throw new UnsupportedOperationException("Test class does not processor this operation");
    }

    @Override
    public boolean containsAnnotation(Annotation annotation) {
      throw new UnsupportedOperationException("Test class does not processor this operation");
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

      return () -> properties;
    }
  }
}
