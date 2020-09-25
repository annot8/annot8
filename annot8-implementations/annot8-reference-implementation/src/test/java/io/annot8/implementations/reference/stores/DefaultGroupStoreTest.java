/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.reference.stores;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import io.annot8.api.annotations.Annotation;
import io.annot8.api.annotations.Group;
import io.annot8.api.exceptions.IncompleteException;
import io.annot8.api.stores.GroupStore;
import io.annot8.testing.testimpl.TestItem;
import io.annot8.testing.testimpl.content.TestStringContent;
import org.junit.jupiter.api.Test;

public class DefaultGroupStoreTest {

  @Test
  public void testInMemoryAnnotationStore() throws IncompleteException {

    TestItem item = new TestItem();
    GroupStore store = new DefaultGroupStore(item);
    item.setGroups(store);
    TestStringContent content = item.save(new TestStringContent(new TestItem()));
    Annotation a1 = content.getAnnotations().create().save();
    Annotation a2 = content.getAnnotations().create().save();

    assertEquals(0, store.getAll().count());

    Group g =
        store
            .getBuilder()
            .withType("TEST")
            .withAnnotation("source", a1)
            .withAnnotation("target", a2)
            .save();

    assertEquals(1, store.getAll().count());
    store.getAll().forEach(group -> assertEquals(g, group));
    assertEquals(g, store.getById(g.getId()).get());
    assertFalse(store.getById("TEST").isPresent());

    store.delete(g);
    assertEquals(0, store.getAll().count());
  }
}
