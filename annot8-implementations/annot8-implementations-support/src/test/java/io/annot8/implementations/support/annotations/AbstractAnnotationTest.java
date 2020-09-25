/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.support.annotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import io.annot8.api.bounds.Bounds;
import io.annot8.api.properties.ImmutableProperties;
import java.util.Collections;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class AbstractAnnotationTest {

  @Test
  public void testBasicEquals() {
    TestAnnotation annotation =
        new TestAnnotation("id", "type", "content", Collections.emptyMap(), null);
    TestAnnotation different =
        new TestAnnotation("diffId", "diffType", "diffContentName", Collections.emptyMap(), null);

    assertEquals(annotation, annotation);
    assertNotEquals(null, annotation);
    assertNotEquals(annotation, new Object());
  }

  @Test
  public void testEqualsWithFields() {
    TestAnnotation annotation =
        new TestAnnotation("id", "type", "content", Collections.singletonMap("key", "value"), null);
    TestAnnotation same =
        new TestAnnotation("id", "type", "content", Collections.singletonMap("key", "value"), null);

    assertEquals(annotation, same);
    assertEquals(annotation.hashCode(), same.hashCode());

    TestAnnotation differentMap =
        new TestAnnotation(
            "id", "type", "content", Collections.singletonMap("key", "diffValue"), null);
    assertNotEquals(annotation, differentMap);

    TestAnnotation differentId =
        new TestAnnotation(
            "diffId", "type", "content", Collections.singletonMap("key", "value"), null);
    assertNotEquals(annotation, differentId);

    TestAnnotation differentType =
        new TestAnnotation(
            "id", "diffType", "content", Collections.singletonMap("key", "value"), null);
    assertNotEquals(annotation, differentType);

    TestAnnotation differentContent =
        new TestAnnotation(
            "id", "type", "diffContent", Collections.singletonMap("key", "value"), null);
    assertNotEquals(annotation, differentContent);
  }

  private class TestAnnotation extends AbstractAnnotation {

    private final String id;
    private final String type;
    private final Map<String, Object> properties;
    private final String contentId;
    private final Bounds bounds;

    public TestAnnotation(
        String id, String type, String contentId, Map<String, Object> properties, Bounds bounds) {
      this.id = id;
      this.type = type;
      this.contentId = contentId;
      this.properties = properties;
      this.bounds = bounds;
    }

    @Override
    public Bounds getBounds() {
      return bounds;
    }

    @Override
    public String getContentId() {
      return contentId;
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
