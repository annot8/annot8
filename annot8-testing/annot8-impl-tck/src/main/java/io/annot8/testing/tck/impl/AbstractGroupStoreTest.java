/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.testing.tck.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.annot8.api.annotations.Annotation;
import io.annot8.api.annotations.Group;
import io.annot8.api.data.Content;
import io.annot8.api.data.Item;
import io.annot8.api.exceptions.IncompleteException;
import io.annot8.api.stores.AnnotationStore;
import io.annot8.api.stores.GroupStore;
import io.annot8.implementations.support.properties.MapMutableProperties;
import io.annot8.implementations.support.stores.AnnotationStoreFactory;
import io.annot8.testing.testimpl.TestAnnotation;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

public abstract class AbstractGroupStoreTest {

  private static final String ROLE = "test";
  private static final String KEY = "key";
  private static final String VALUE = "value";
  private static final String TEST_ANNOTATION_ID = "TEST_ANNOTATION_ID";
  private static final String TEST_CONTENT_DESCRIPTION = "TEST_CONTENT_DESCRIPTION";

  protected abstract GroupStore getGroupStore(Item item);

  @Test
  public void testGetBuilder() {
    GroupStore groupStore = getGroupStore(getTestItem());
    testGroupBuilder(groupStore.getBuilder());
  }

  @Test
  public void testCreate() {
    GroupStore groupStore = getGroupStore(getTestItem());
    testGroupBuilder(groupStore.create());
  }

  @Test
  public void testCopy() {
    GroupStore groupStore = getGroupStore(getTestItem());
    Annotation annotation = getTestAnnotation();
    Group group = createTestGroup(groupStore, annotation);

    try {
      Group copied = groupStore.copy(group).save();
      assertNotEquals(group.getId(), copied.getId());
      assertEquals(ROLE, copied.getType());
      assertTrue(copied.getProperties().has(KEY));
      assertEquals(VALUE, copied.getProperties().get(KEY).orElseThrow());
    } catch (IncompleteException e) {
      fail("Failed to copy group.", e);
    }
  }

  @Test
  public void testEdit() {
    GroupStore groupStore = getGroupStore(getTestItem());
    Annotation annotation = getTestAnnotation();
    Group group = createTestGroup(groupStore, annotation);

    try {
      Group edit = groupStore.edit(group).save();
      assertEquals(group.getId(), edit.getId());
      assertEquals(ROLE, edit.getType());
      assertTrue(edit.getProperties().has(KEY));
      assertEquals(VALUE, edit.getProperties().get(KEY).orElseThrow());
    } catch (IncompleteException e) {
      fail("Failed to copy group.", e);
    }
  }

  @Test
  public void testDelete() {
    GroupStore groupStore = getGroupStore(getTestItem());
    Group group = createTestGroup(groupStore, getTestAnnotation());

    assertEquals(1, groupStore.getAll().count());

    groupStore.delete(group);

    assertEquals(0, groupStore.getAll().count());
  }

  @Test
  public void testDeleteAll() {
    GroupStore groupStore = getGroupStore(getTestItem());
    createTestGroup(groupStore, getTestAnnotation());

    assertEquals(1, groupStore.getAll().count());

    groupStore.deleteAll();

    assertEquals(0, groupStore.getAll().count());
  }

  @Test
  public void testGetAll() {
    GroupStore groupStore = getGroupStore(getTestItem());
    Group group = createTestGroup(groupStore, getTestAnnotation());

    assertEquals(1, groupStore.getAll().count());
    assertEquals(group.getId(), groupStore.getAll().findFirst().orElseThrow().getId());
  }

  @Test
  public void testGetByType() {
    GroupStore groupStore = getGroupStore(getTestItem());
    Group group = createTestGroup(groupStore, getTestAnnotation());
    groupStore.create().withType("wrongType").withAnnotation("testing", getTestAnnotation());

    List<Group> byType = groupStore.getByType(ROLE).collect(Collectors.toList());
    assertEquals(1, byType.size());
    Group group2 = byType.get(0);
    assertEquals(group.getId(), group2.getId());
    assertEquals(ROLE, group2.getType());
  }

  @Test
  public void testGetById() {
    GroupStore groupStore = getGroupStore(getTestItem());
    Group group = createTestGroup(groupStore, getTestAnnotation());

    Optional<Group> byId = groupStore.getById(group.getId());
    assertTrue(byId.isPresent());
    Group retrieved = byId.get();
    assertEquals(group.getId(), retrieved.getId());
    assertEquals(group.getType(), retrieved.getType());
  }

  private Group createTestGroup(GroupStore store, Annotation annotation) {
    try {
      return store
          .create()
          .withAnnotation(ROLE, annotation)
          .withProperty(KEY, VALUE)
          .withType(ROLE)
          .save();
    } catch (IncompleteException e) {
      fail("Failed to build group.", e);
      throw e;
    }
  }

  private void testGroupBuilder(Group.Builder groupBuilder) {
    String groupType = "groupType";
    String propKey1 = "key1";
    String propKey2 = "key2";
    String propKey3 = "key3";
    String propVal1 = "val1";
    String propVal2 = "val2";
    String propVal3 = "val3";
    Map<String, Object> properties = Map.of(propKey1, propVal1, propKey2, propVal2);
    Annotation annotation = getTestAnnotation();

    try {
      Group group =
          groupBuilder
              .withType(groupType)
              .withProperties(new MapMutableProperties(properties))
              .withProperty(propKey3, propVal3)
              .withoutProperty(propKey1)
              .withoutProperty(propKey2, propVal2)
              .withAnnotation(ROLE, annotation)
              .save();

      assertEquals(groupType, group.getType());
      assertNotNull(group.getId());
      assertEquals(propVal3, group.getProperties().get(propKey3).orElseThrow());
      assertFalse(group.getProperties().has(propVal1));
      assertFalse(group.getProperties().has(propVal2));
      Map<String, Stream<Annotation>> groupAnnotationsMap = group.getAnnotations();
      assertTrue(groupAnnotationsMap.containsKey(ROLE));
      List<Annotation> annotationsList = groupAnnotationsMap.get(ROLE).collect(Collectors.toList());
      assertEquals(1, annotationsList.size());
      assertEquals(annotation.getId(), annotationsList.get(0).getId());
    } catch (IncompleteException e) {
      fail("Group failed to build", e);
    }
  }

  private Item getTestItem() {
    Item mock = mock(Item.class);
    @SuppressWarnings("unchecked")
    Content<String> mockContent = mock(Content.class);
    when(mockContent.getId()).thenReturn("TEST_CONTENT_ID");
    when(mockContent.getDescription()).thenReturn(TEST_CONTENT_DESCRIPTION);
    when(mock.getId()).thenReturn("TEST_ITEM_ID");
    when(mock.getContent(ArgumentMatchers.eq(TEST_CONTENT_DESCRIPTION)))
        .thenReturn(Optional.of(mockContent));

    return mock;
  }

  private Annotation getTestAnnotation() {
    TestAnnotation annotation = new TestAnnotation();
    annotation.setContentId(TEST_CONTENT_DESCRIPTION);
    annotation.setId(TEST_ANNOTATION_ID);
    return annotation;
  }

  protected AnnotationStoreFactory getMockedAnnotationStoreFactory() {
    AnnotationStoreFactory mock = mock(AnnotationStoreFactory.class);
    AnnotationStore mockedStore = mock(AnnotationStore.class);

    when(mockedStore.getById(TEST_ANNOTATION_ID)).thenReturn(Optional.of(getTestAnnotation()));
    when(mock.create(ArgumentMatchers.any())).thenReturn(mockedStore);

    return mock;
  }
}
