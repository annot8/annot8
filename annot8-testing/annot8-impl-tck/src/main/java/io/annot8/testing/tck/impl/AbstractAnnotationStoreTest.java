/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.testing.tck.impl;

import io.annot8.api.annotations.Annotation;
import io.annot8.api.annotations.Annotation.Builder;
import io.annot8.api.data.Content;
import io.annot8.api.exceptions.IncompleteException;
import io.annot8.api.stores.AnnotationStore;
import io.annot8.testing.testimpl.TestBounds;
import io.annot8.testing.testimpl.TestItem;
import io.annot8.testing.testimpl.TestProperties;
import io.annot8.testing.testimpl.content.TestStringContent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class AbstractAnnotationStoreTest {

  private static final String TEST_ID = "TEST_ID";
  private static final String TEST_DATA = "TEST_DATA";
  private static final String TEST_NAME = "TEST_NAME";
  private static final String TEST_ANNOTATION_ID = "TEST_ANNOTATION";
  private static final String TEST_ANNOTATION_TYPE = "TEST_TYPE";
  private static final String TEST_PROPERTY_KEY = "TEST_KEY";
  private static final String TEST_PROPERTY_VALUE = "TEST_VALUE";
  private final TestBounds bounds = new TestBounds();
  private final TestItem item = new TestItem();

  protected abstract AnnotationStore getAnnotationStore(Content content);

  private Content getTestContent() {
    return new TestStringContent(item, TEST_ID, TEST_NAME, new TestProperties(), () -> TEST_DATA);
  }

  @Test
  public void testGetBuilder() {
    testBuilder(getAnnotationStore(getTestContent()).getBuilder());
    testIncompleteBuilder(getAnnotationStore(getTestContent()).getBuilder());
  }

  @Test
  public void testCreate() {
    testBuilder(getAnnotationStore(getTestContent()).create());
    testIncompleteBuilder(getAnnotationStore(getTestContent()).create());
  }

  @Test
  public void testCreateSameId() {
    String updated = "updated";
    createDefaultTestAnnotation();
    createTestAnnotation(TEST_ANNOTATION_ID, updated, TEST_PROPERTY_KEY, TEST_PROPERTY_VALUE);

    List<Annotation> all =
        getAnnotationStore(getTestContent()).getAll().collect(Collectors.toList());
    Assertions.assertEquals(1, all.size());
    Assertions.assertEquals("updated", all.get(0).getType());
  }

  @Test
  public void testCopy() {
    Annotation annotation = createDefaultTestAnnotation();

    Annotation copied = null;
    try {
      copied = getAnnotationStore(getTestContent()).copy(annotation).save();
    } catch (IncompleteException e) {
      Assertions.fail("Exception not expected", e);
    }

    Assertions.assertNotEquals(TEST_ANNOTATION_ID, copied.getId());
    testFields(annotation);
  }

  @Test
  public void testEdit() {
    Annotation annotation = createDefaultTestAnnotation();
    Annotation edit = null;
    try {
      edit = getAnnotationStore(getTestContent()).edit(annotation).save();
    } catch (IncompleteException e) {
      Assertions.fail("Exception not expected", e);
    }

    Assertions.assertEquals(TEST_ANNOTATION_ID, edit.getId());
    testFields(annotation);
  }

  @Test
  public void testDelete() {
    Annotation annotation = createDefaultTestAnnotation();
    AnnotationStore annotationStore = getAnnotationStore(getTestContent());
    Assertions.assertTrue(annotationStore.getById(TEST_ANNOTATION_ID).isPresent());

    annotationStore.delete(annotation);
    Assertions.assertTrue(annotationStore.getById(TEST_ANNOTATION_ID).isEmpty());
  }

  @Test
  public void testDeleteCollection() {
    Annotation annotation = createDefaultTestAnnotation();
    Annotation annotation2 = createTestAnnotationWithId("ANNOTATION2");
    AnnotationStore annotationStore = getAnnotationStore(getTestContent());
    Assertions.assertEquals(2, annotationStore.getAll().count());
    List<Annotation> list = new ArrayList<>();
    list.add(annotation);
    list.add(annotation2);
    annotationStore.delete(list);
    Assertions.assertTrue(annotationStore.getById(TEST_ANNOTATION_ID).isEmpty());
    Assertions.assertTrue(annotationStore.getById("ANNOTATION2").isEmpty());
  }

  @Test
  public void testGetAll() {
    Annotation annotation = createDefaultTestAnnotation();
    String annotation2Id = "Annotation2";
    Annotation annotation2 = createTestAnnotationWithId(annotation2Id);

    Map<String, Annotation> annotations =
        getAnnotationStore(getTestContent())
            .getAll()
            .collect(Collectors.toMap(Annotation::getId, (a) -> a));

    Assertions.assertTrue(annotations.containsKey(TEST_ANNOTATION_ID));
    Assertions.assertTrue(annotations.containsKey(annotation2Id));

    Annotation testAnnotation1 = annotations.get(TEST_ANNOTATION_ID);
    Assertions.assertEquals(TEST_ANNOTATION_ID, testAnnotation1.getId());
    testFields(testAnnotation1);

    Annotation testAnnotation2 = annotations.get(annotation2Id);
    Assertions.assertEquals(annotation2Id, testAnnotation2.getId());
    testFields(testAnnotation1);
  }

  @Test
  public void testDeleteAll() {
    createDefaultTestAnnotation();
    createTestAnnotationWithId("annotation2");

    AnnotationStore annotationStore = getAnnotationStore(getTestContent());
    Assertions.assertEquals(2, annotationStore.getAll().count());
    annotationStore.deleteAll();
    Assertions.assertEquals(0, annotationStore.getAll().count());
  }

  @Test
  public void testGetByType() {
    createDefaultTestAnnotation();
    String annotation2Id = "2id";
    String type = "diffType";
    createTestAnnotation(annotation2Id, type, TEST_PROPERTY_KEY, TEST_PROPERTY_VALUE);

    AnnotationStore annotationStore = getAnnotationStore(getTestContent());
    Assertions.assertEquals(2, annotationStore.getAll().count());

    List<Annotation> byType = annotationStore.getByType(type).collect(Collectors.toList());
    Assertions.assertEquals(1, byType.size());
    Annotation typeResult = byType.get(0);
    Assertions.assertEquals(annotation2Id, typeResult.getId());
    Assertions.assertEquals(type, typeResult.getType());
  }

  @Test
  public void testGetByBounds() {
    createDefaultTestAnnotation();
    AnnotationStore annotationStore = getAnnotationStore(getTestContent());
    List<Annotation> results =
        annotationStore.getByBounds(TestBounds.class).collect(Collectors.toList());
    Assertions.assertEquals(1, results.size());
  }

  private void testFields(Annotation annotation) {
    Assertions.assertEquals(TEST_ANNOTATION_TYPE, annotation.getType());
    Assertions.assertTrue(annotation.getProperties().has(TEST_PROPERTY_KEY));
    Optional<Object> expected = annotation.getProperties().get(TEST_PROPERTY_KEY);
    Assertions.assertTrue(expected.isPresent());
    Assertions.assertEquals(expected.get(), TEST_PROPERTY_VALUE);
    Assertions.assertEquals(1, annotation.getProperties().keys().count());
    Assertions.assertEquals(bounds, annotation.getBounds());
  }

  private void testBuilder(Builder builder) {
    Annotation annotation = createDefaultTestAnnotation();

    Assertions.assertEquals(TEST_ANNOTATION_ID, annotation.getId());
    testFields(annotation);
  }

  private void testIncompleteBuilder(Builder builder) {
    try {
      builder.save();
    } catch (IncompleteException e) {
      return;
    }
    Assertions.fail("Code should not reach here, exception should be thrown");
  }

  private Annotation createDefaultTestAnnotation() {
    return createTestAnnotation(
        TEST_ANNOTATION_ID, TEST_ANNOTATION_TYPE, TEST_PROPERTY_KEY, TEST_PROPERTY_VALUE);
  }

  private Annotation createTestAnnotationWithId(String id) {
    return createTestAnnotation(id, TEST_ANNOTATION_TYPE, TEST_PROPERTY_KEY, TEST_PROPERTY_VALUE);
  }

  private Annotation createTestAnnotation(String id, String type, String key, String value) {
    Annotation annotation = null;
    try {
      annotation =
          getAnnotationStore(getTestContent())
              .create()
              .withId(id)
              .withType(type)
              .withProperty(key, value)
              .withBounds(bounds)
              .save();
    } catch (IncompleteException e) {
      Assertions.fail("Exception is not expected", e);
    }
    return annotation;
  }
}
