/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.reference.stores;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import io.annot8.api.annotations.Annotation;
import io.annot8.api.exceptions.IncompleteException;
import io.annot8.api.stores.AnnotationStore;
import io.annot8.common.data.bounds.NoBounds;
import io.annot8.testing.testimpl.TestConstants;
import io.annot8.testing.testimpl.content.TestStringContent;
import org.junit.jupiter.api.Test;

public class DefaultAnnotationStoreTest {

  @Test
  public void testInMemoryAnnotationStore() throws IncompleteException {
    String contentId = TestConstants.CONTENT_ID;
    AnnotationStore store = new DefaultAnnotationStore(new TestStringContent());

    assertEquals(0, store.getAll().count());

    Annotation a = store.getBuilder().withBounds(NoBounds.getInstance()).withType("TEST").save();

    assertEquals(1, store.getAll().count());
    store.getAll().forEach(annot -> assertEquals(a, annot));
    assertEquals(a, store.getById(a.getId()).get());
    assertFalse(store.getById("TEST").isPresent());

    store.delete(a);
    assertEquals(0, store.getAll().count());
  }
}
