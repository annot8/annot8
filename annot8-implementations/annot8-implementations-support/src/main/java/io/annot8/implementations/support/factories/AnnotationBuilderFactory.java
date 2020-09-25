/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.support.factories;

import io.annot8.api.annotations.Annotation;
import io.annot8.api.data.Content;
import io.annot8.api.stores.AnnotationStore;

/**
 * Factory to createContent an annotation builder.
 *
 * <p>Typically used in an AnnotationStore.getBuilder().
 */
@FunctionalInterface
public interface AnnotationBuilderFactory {

  /**
   * Create a new builder for the provided parameters.
   *
   * <p>Most implementation will simply need the store parameter to allow save on save.
   *
   * @param content the content
   * @param store the annotation store to use
   * @return non-null builder
   */
  Annotation.Builder create(Content<?> content, AnnotationStore store);
}
