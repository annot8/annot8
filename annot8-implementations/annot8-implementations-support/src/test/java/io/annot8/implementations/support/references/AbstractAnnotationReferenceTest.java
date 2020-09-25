/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.support.references;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import io.annot8.api.annotations.Annotation;
import io.annot8.api.references.AnnotationReference;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class AbstractAnnotationReferenceTest {

  @Test
  public void testEquals() {
    AnnotationReference ref = new TestAnnotationReference("content", "annotationID");
    AnnotationReference same = new TestAnnotationReference("content", "annotationID");
    AnnotationReference diff = new TestAnnotationReference("diffContent", "diffId");

    assertEquals(ref, ref);
    assertEquals(ref, same);
    assertEquals(ref.hashCode(), same.hashCode());
    assertNotEquals(same, diff);
    assertNotEquals(null, ref);
    assertNotEquals(ref, new Object());
  }

  private class TestAnnotationReference extends AbstractAnnotationReference {

    private final String contentId;
    private final String annotationId;

    public TestAnnotationReference(String contentId, String annotationId) {
      this.contentId = contentId;
      this.annotationId = annotationId;
    }

    @Override
    public String getContentId() {
      return contentId;
    }

    @Override
    public String getAnnotationId() {
      return annotationId;
    }

    @Override
    public Optional<Annotation> toAnnotation() {
      throw new UnsupportedOperationException("Not supported in test implementation");
    }
  }
}
