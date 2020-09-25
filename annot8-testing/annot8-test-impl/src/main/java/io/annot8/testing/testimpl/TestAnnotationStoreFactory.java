/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.testing.testimpl;

import io.annot8.api.data.Content;
import io.annot8.api.stores.AnnotationStore;
import io.annot8.implementations.support.stores.AnnotationStoreFactory;

public class TestAnnotationStoreFactory implements AnnotationStoreFactory {

  private static final TestAnnotationStoreFactory INSTANCE = new TestAnnotationStoreFactory();

  public static AnnotationStoreFactory getInstance() {
    return INSTANCE;
  }

  @Override
  public AnnotationStore create(Content<?> content) {
    return new TestAnnotationStore(content);
  }
}
