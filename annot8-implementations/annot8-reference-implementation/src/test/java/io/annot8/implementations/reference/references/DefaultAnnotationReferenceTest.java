/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.reference.references;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.annot8.api.annotations.Annotation;
import io.annot8.api.data.Content;
import io.annot8.api.data.Item;
import io.annot8.api.stores.AnnotationStore;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DefaultAnnotationReferenceTest {

  @Test
  void to() {
    Item item = mock(Item.class);
    Annotation annotation = mock(Annotation.class);
    when(annotation.getContentId()).thenReturn("content");
    when(annotation.getId()).thenReturn("id");

    DefaultAnnotationReference reference = DefaultAnnotationReference.to(item, annotation);

    assertEquals("content", reference.getContentId());
    assertEquals("id", reference.getAnnotationId());
  }

  @Test
  void newAnnotationReference() {

    Item item = mock(Item.class);
    Content content = mock(Content.class);
    AnnotationStore annotationStore = mock(AnnotationStore.class);
    Annotation annotation = mock(Annotation.class);

    DefaultAnnotationReference reference = new DefaultAnnotationReference(item, "content", "1");

    assertEquals("content", reference.getContentId());
    assertEquals("1", reference.getAnnotationId());

    when(item.getContent("content")).thenReturn(Optional.of(content));
    when(content.getAnnotations()).thenReturn(annotationStore);
    when(annotationStore.getById(Mockito.eq("1"))).thenReturn(Optional.of(annotation));

    assertEquals(annotation, reference.toAnnotation().get());
  }
}
