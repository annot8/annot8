/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.support.stores;

import io.annot8.api.data.Content;
import io.annot8.api.stores.AnnotationStore;

@FunctionalInterface
public interface AnnotationStoreFactory {

  AnnotationStore create(Content<?> content);
}
