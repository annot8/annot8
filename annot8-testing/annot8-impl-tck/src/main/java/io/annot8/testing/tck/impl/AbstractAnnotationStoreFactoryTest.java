/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.testing.tck.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.annot8.api.stores.AnnotationStore;
import io.annot8.implementations.support.stores.AnnotationStoreFactory;
import io.annot8.testing.testimpl.TestProperties;
import io.annot8.testing.testimpl.content.TestStringContent;
import org.junit.jupiter.api.Test;

public abstract class AbstractAnnotationStoreFactoryTest {

  protected abstract AnnotationStoreFactory getAnnotationStoreFactory();

  @Test
  public void testGetAnnotationStore() {
    TestStringContent content =
        new TestStringContent(null, "testContentId", "name", new TestProperties(), () -> "test");
    AnnotationStore annotationStore = getAnnotationStoreFactory().create(content);
    assertNotNull(annotationStore);
  }
}
