/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.testing.testimpl;

import io.annot8.api.annotations.Annotation;
import io.annot8.api.references.AnnotationReference;
import java.util.Objects;
import java.util.Optional;

public class TestAnnotationReference implements AnnotationReference {

  private Annotation annotation;

  public TestAnnotationReference(Annotation annotation) {
    if (annotation == null) {
      throw new IllegalArgumentException("Invalid null annotation");
    }
    this.annotation = annotation;
  }

  @Override
  public String getAnnotationId() {
    return annotation.getId();
  }

  @Override
  public String getContentId() {
    return annotation.getContentId();
  }

  @Override
  public Optional<Annotation> toAnnotation() {
    return Optional.of(annotation);
  }

  public void setAnnotation(Annotation annotation) {
    if (annotation == null) {
      throw new IllegalArgumentException("Invalid null annotation");
    }
    this.annotation = annotation;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TestAnnotationReference that = (TestAnnotationReference) o;
    return Objects.equals(annotation.getId(), that.annotation.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(annotation.getId());
  }
}
