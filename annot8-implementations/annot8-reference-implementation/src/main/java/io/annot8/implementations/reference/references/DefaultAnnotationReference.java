/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.reference.references;

import io.annot8.api.annotations.Annotation;
import io.annot8.api.data.Content;
import io.annot8.api.data.Item;
import io.annot8.implementations.support.references.AbstractAnnotationReference;
import java.util.Optional;

/**
 * A reference which will always retrieve the latest annotation from the appropriate annotation
 * store.
 *
 * <p>Does not hold a reference to the group.
 */
public class DefaultAnnotationReference extends AbstractAnnotationReference {

  private final Item item;

  private final String contentId;

  private final String annotationId;

  /**
   * New reference either from another reference or manually created.
   *
   * @param annotationId the annotation id
   * @param contentId the content id
   * @param item the item (which owns the content)
   */
  public DefaultAnnotationReference(Item item, String contentId, String annotationId) {
    this.item = item;
    this.contentId = contentId;
    this.annotationId = annotationId;
  }

  /**
   * Create an annotation reference for the annotation.
   *
   * @param annotation the annotation
   * @param item the item owning the annotation
   * @return reference to the annotation
   */
  public static DefaultAnnotationReference to(Item item, Annotation annotation) {
    return new DefaultAnnotationReference(item, annotation.getContentId(), annotation.getId());
  }

  @Override
  public String getAnnotationId() {
    return annotationId;
  }

  @Override
  public String getContentId() {
    return contentId;
  }

  @Override
  public Optional<Annotation> toAnnotation() {
    return item.getContent(contentId)
        .map(Content::getAnnotations)
        .flatMap(store -> store.getById(annotationId));
  }
}
