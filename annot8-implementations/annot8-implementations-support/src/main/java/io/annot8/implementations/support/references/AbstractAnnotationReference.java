/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.support.references;

import io.annot8.api.references.AnnotationReference;
import java.util.Objects;

/**
 * Abstract implementation of AnnotationReference, providing correct implementations of equals,
 * hashCode and toString.
 *
 * <p>Two annotation references are taken to be equal if the following properties are all equal. The
 * actual implementation of the annotation reference is seen to be irrelevant and not checked.
 *
 * <ul>
 *   <li>annotationId
 *   <li>contentName
 * </ul>
 */
public abstract class AbstractAnnotationReference implements AnnotationReference {

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }

    if (other == null) {
      return false;
    }

    if (!AnnotationReference.class.isAssignableFrom(other.getClass())) {
      return false;
    }

    AnnotationReference that = (AnnotationReference) other;
    return Objects.equals(getAnnotationId(), that.getAnnotationId())
        && Objects.equals(getContentId(), that.getContentId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getAnnotationId(), getContentId());
  }

  @Override
  public String toString() {
    return this.getClass().getName()
        + " [annotationId="
        + getAnnotationId()
        + ", contentId="
        + getContentId()
        + "]";
  }
}
