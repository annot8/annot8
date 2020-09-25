/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.testing.tck.impl;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

public abstract class AbstractGroupStoreTest {

  private static final String role = "test";
  private static final String key = "key";
  private static final String value = "value";
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

    Group copied = null;
    try {
      copied = groupStore.copy(group).save();
    } catch (IncompleteException e) {
      Assertions.fail("Failed to copy group.", e);
    }

    Assertions.assertNotEquals(group.getId(), copied.getId());
    Assertions.assertEquals(role, copied.getType());
    Assertions.assertTrue(copied.getProperties().has(key));
    Assertions.assertEquals(value, copied.getProperties().get(key).get());
  }

  @Test
  public void testEdit() {
    GroupStore groupStore = getGroupStore(getTestItem());
    Annotation annotation = getTestAnnotation();
    Group group = createTestGroup(groupStore, annotation);

    Group edit = null;
    try {
      edit = groupStore.edit(group).save();
    } catch (IncompleteException e) {
      Assertions.fail("Failed to copy group.", e);
    }

    Assertions.assertEquals(group.getId(), edit.getId());
    Assertions.assertEquals(role, edit.getType());
    Assertions.assertTrue(edit.getProperties().has(key));
    Assertions.assertEquals(value, edit.getProperties().get(key).get());
  }

  @Test
  public void testDelete() {
    GroupStore groupStore = getGroupStore(getTestItem());
    Group group = createTestGroup(groupStore, getTestAnnotation());

    Assertions.assertEquals(1, groupStore.getAll().count());

    groupStore.delete(group);

    Assertions.assertEquals(0, groupStore.getAll().count());
  }

  @Test
  public void testDeleteAll() {
    GroupStore groupStore = getGroupStore(getTestItem());
    Group group = createTestGroup(groupStore, getTestAnnotation());

    Assertions.assertEquals(1, groupStore.getAll().count());

    groupStore.deleteAll();

    Assertions.assertEquals(0, groupStore.getAll().count());
  }

  @Test
  public void testGetAll() {
    GroupStore groupStore = getGroupStore(getTestItem());
    Group group = createTestGroup(groupStore, getTestAnnotation());

    Assertions.assertEquals(1, groupStore.getAll().count());
    Assertions.assertEquals(group.getId(), groupStore.getAll().findFirst().get().getId());
  }

  @Test
  public void testGetByType() {
    GroupStore groupStore = getGroupStore(getTestItem());
    Group group = createTestGroup(groupStore, getTestAnnotation());
    groupStore.create().withType("wrongType").withAnnotation("testing", getTestAnnotation());

    List<Group> byType = groupStore.getByType(role).collect(Collectors.toList());
    Assertions.assertEquals(1, byType.size());
    Group group2 = byType.get(0);
    Assertions.assertEquals(group.getId(), group2.getId());
    Assertions.assertEquals(role, group2.getType());
  }

  @Test
  public void testGetById() {
    GroupStore groupStore = getGroupStore(getTestItem());
    Group group = createTestGroup(groupStore, getTestAnnotation());

    Optional<Group> byId = groupStore.getById(group.getId());
    Assertions.assertTrue(byId.isPresent());
    Group retrieved = byId.get();
    Assertions.assertEquals(group.getId(), retrieved.getId());
    Assertions.assertEquals(group.getType(), retrieved.getType());
  }

  private Group createTestGroup(GroupStore store, Annotation annotation) {
    Group group = null;
    try {
      group =
          store
              .create()
              .withAnnotation(role, annotation)
              .withProperty(key, value)
              .withType(role)
              .save();
    } catch (IncompleteException e) {
      Assertions.fail("Failed to build group.", e);
    }
    return group;
  }

  private void testGroupBuilder(Group.Builder groupBuilder) {
    String groupType = "groupType";
    String propKey1 = "key1";
    String propKey2 = "key2";
    String propKey3 = "key3";
    String propVal1 = "val1";
    String propVal2 = "val2";
    String propVal3 = "val3";
    Map<String, Object> properties = new HashMap<>();
    properties.put(propKey1, propVal1);
    properties.put(propKey2, propVal2);
    Annotation annotation = getTestAnnotation();

    Group group = null;
    try {
      group =
          groupBuilder
              .withType(groupType)
              .withProperties(new MapMutableProperties(properties))
              .withProperty(propKey3, propVal3)
              .withoutProperty(propKey1)
              .withoutProperty(propKey2, propVal2)
              .withAnnotation(role, annotation)
              .save();
    } catch (IncompleteException e) {
      Assertions.fail("Group failed to build", e);
    }

    Assertions.assertEquals(groupType, group.getType());
    Assertions.assertNotNull(group.getId());
    Assertions.assertEquals(propVal3, group.getProperties().get(propKey3).get());
    Assertions.assertFalse(group.getProperties().has(propVal1));
    Assertions.assertFalse(group.getProperties().has(propVal2));
    Map<String, Stream<Annotation>> groupAnnotationsMap = group.getAnnotations();
    Assertions.assertTrue(groupAnnotationsMap.containsKey(role));
    List<Annotation> annotationsList = groupAnnotationsMap.get(role).collect(Collectors.toList());
    Assertions.assertEquals(1, annotationsList.size());
    Assertions.assertEquals(annotation.getId(), annotationsList.get(0).getId());
  }

  private Item getTestItem() {
    Item mock = Mockito.mock(Item.class);
    Content mockContent = Mockito.mock(Content.class);
    Mockito.when(mockContent.getId()).thenReturn("TEST_CONTENT_ID");
    Mockito.when(mockContent.getDescription()).thenReturn(TEST_CONTENT_DESCRIPTION);
    Mockito.when(mock.getId()).thenReturn("TEST_ITEM_ID");
    Mockito.when(mock.getContent(ArgumentMatchers.eq(TEST_CONTENT_DESCRIPTION)))
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
    AnnotationStoreFactory mock = Mockito.mock(AnnotationStoreFactory.class);
    AnnotationStore mockedStore = Mockito.mock(AnnotationStore.class);

    Mockito.when(mockedStore.getById(TEST_ANNOTATION_ID))
        .thenReturn(Optional.of(getTestAnnotation()));
    Mockito.when(mock.create(ArgumentMatchers.any())).thenReturn(mockedStore);

    return mock;
  }
}
