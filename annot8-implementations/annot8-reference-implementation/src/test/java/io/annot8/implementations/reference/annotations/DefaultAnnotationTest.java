/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.reference.annotations;

import static org.junit.jupiter.api.Assertions.*;

import io.annot8.api.annotations.Annotation;
import io.annot8.api.bounds.Bounds;
import io.annot8.api.exceptions.IncompleteException;
import io.annot8.implementations.reference.annotations.DefaultAnnotation.Builder;
import io.annot8.implementations.support.properties.MapMutableProperties;
import io.annot8.testing.tck.impl.WithIdBuilderTestUtils;
import io.annot8.testing.tck.impl.WithPropertiesBuilderTestUtils;
import io.annot8.testing.testimpl.TestBounds;
import io.annot8.testing.testimpl.TestConstants;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class DefaultAnnotationTest {

  private final Bounds bounds = new TestBounds();

  @Test
  public void testIncompleteBuilderNothingSet() {
    assertThrows(IncompleteException.class, new Builder(TestConstants.CONTENT_ID)::save);
  }

  @Test
  public void testIncompleteBuilderNoBounds() {
    assertThrows(
        IncompleteException.class, new Builder(TestConstants.CONTENT_ID).withType("TEST")::save);
  }

  @Test
  public void testIncompleteBuilderNoType() {
    assertThrows(
        IncompleteException.class, new Builder(TestConstants.CONTENT_ID).withBounds(bounds)::save);
  }

  @Test
  public void testIncompleteBuilderNoContent() {
    assertThrows(
        IncompleteException.class,
        () -> new Builder(null).withType("Test").withBounds(bounds).save());
  }

  @Test
  public void testMinimal() throws IncompleteException {
    Annotation a1 =
        new DefaultAnnotation.Builder(TestConstants.CONTENT_ID)
            .withType("TEST")
            .withBounds(bounds)
            .save();
    assertNotNull(a1.getId());
    assertEquals("TEST", a1.getType());
    assertEquals(bounds, a1.getBounds());
    assertEquals(TestConstants.CONTENT_ID, a1.getContentId());
    assertTrue(a1.getProperties().getAll().isEmpty());
  }

  @Test
  public void testWithProperty() throws IncompleteException {
    Annotation a2 =
        new DefaultAnnotation.Builder(TestConstants.CONTENT_ID)
            .withType(TestConstants.ANNOTATION_TYPE)
            .withBounds(bounds)
            .withProperty("key1", 17)
            .withProperty("key2", false)
            .save();
    assertNotNull(a2.getId());
    assertEquals(TestConstants.ANNOTATION_TYPE, a2.getType());
    assertEquals(bounds, a2.getBounds());
    assertEquals(TestConstants.CONTENT_ID, a2.getContentId());
  }

  @Test
  public void testWithProperties() throws IncompleteException {
    Map<String, Object> properties2 = new HashMap<>();
    properties2.put("key1", 17);
    properties2.put("key2", false);

    Annotation a3 =
        new DefaultAnnotation.Builder(TestConstants.CONTENT_ID)
            .withType(TestConstants.ANNOTATION_TYPE)
            .withBounds(bounds)
            .withProperties(new MapMutableProperties(properties2))
            .save();
    Map<String, Object> properties3 = a3.getProperties().getAll();
    assertEquals(2, properties3.size());
    assertEquals(17, properties3.get("key1"));
    assertEquals(false, properties3.get("key2"));
  }

  @Test
  public void testFromExisting() throws IncompleteException {
    Annotation a1 =
        new DefaultAnnotation.Builder(TestConstants.CONTENT_ID)
            .withType(TestConstants.ANNOTATION_TYPE)
            .withBounds(bounds)
            .save();

    Annotation a2 =
        new DefaultAnnotation.Builder(TestConstants.CONTENT_ID)
            .from(a1)
            .withProperty("key1", 17)
            .withProperty("key2", false)
            .save();
    assertNotNull(a2.getId());
    assertEquals(a1.getId(), a2.getId());

    assertEquals(TestConstants.ANNOTATION_TYPE, a2.getType());
    assertEquals(bounds, a2.getBounds());
    assertEquals(TestConstants.CONTENT_ID, a2.getContentId());

    Map<String, Object> properties2 = a2.getProperties().getAll();
    assertEquals(2, properties2.size());
    assertEquals(17, properties2.get("key1"));
    assertEquals(false, properties2.get("key2"));
  }

  @Test
  public void testFromExistingWithNewId() throws IncompleteException {
    Annotation a1 =
        new DefaultAnnotation.Builder(TestConstants.CONTENT_ID)
            .withType(TestConstants.ANNOTATION_TYPE)
            .withBounds(bounds)
            .save();

    Annotation a2 = new DefaultAnnotation.Builder(TestConstants.CONTENT_ID).from(a1).newId().save();
    assertNotNull(a2.getId());
    assertNotEquals(a1.getId(), a2.getId());
  }

  @Test
  public void testFromExistingOverridden() throws IncompleteException {
    Annotation a1 =
        new DefaultAnnotation.Builder(TestConstants.CONTENT_ID)
            .withType(TestConstants.ANNOTATION_TYPE)
            .withBounds(bounds)
            .save();

    Bounds otherBounds = new TestBounds();
    Annotation a2 =
        new DefaultAnnotation.Builder(TestConstants.CONTENT_ID)
            .from(a1)
            .withType("TEST2")
            .withBounds(otherBounds)
            .save();

    assertEquals(a1.getId(), a2.getId());
    assertEquals(a2.getType(), "TEST2");
    assertEquals(a2.getBounds(), otherBounds);

    // Check that a1 is unchecked
    assertEquals(a1.getType(), TestConstants.ANNOTATION_TYPE);
    assertEquals(a1.getBounds(), bounds);
  }

  @Test
  public void testProperties() {
    WithPropertiesBuilderTestUtils utils = new WithPropertiesBuilderTestUtils();
    Annotation.Builder builder =
        new Builder(TestConstants.CONTENT_ID).withType("TEST").withBounds(bounds);
    utils.testWithPropertiesBuilder(builder);
  }

  @Test
  public void testWithIdBuilder() {
    WithIdBuilderTestUtils utils = new WithIdBuilderTestUtils();
    Annotation.Builder builder =
        new Builder(TestConstants.CONTENT_ID).withType("TEST").withBounds(bounds);
    utils.testWithIdBuilder(builder);
  }
}
