/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.content;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.annot8.api.annotations.Annotation;
import io.annot8.api.bounds.Bounds;
import io.annot8.api.stores.AnnotationStore;
import io.annot8.common.data.bounds.SpanBounds;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

class TextTest {

  private Annotation annotation;
  private Bounds bounds;
  private Text text;

  @BeforeEach
  void beforeEach() {
    text = mock(Text.class);
    when(text.getText(any(Annotation.class))).thenCallRealMethod();
    when(text.getData()).thenReturn("Test data");

    bounds = mock(Bounds.class);
    annotation = mock(Annotation.class);
    when(annotation.getBounds()).thenReturn(bounds);
  }

  @Test
  void testTextTextNonEmpty() {
    when(bounds.getData(eq(text), eq(String.class))).thenReturn(Optional.of("covered"));

    Optional<String> output = text.getText(annotation);
    assertEquals("covered", output.get());
  }

  @Test
  void testTextTextEmpty() {
    when(bounds.getData(eq(text), eq(String.class))).thenReturn(Optional.empty());

    Optional<String> output = text.getText(annotation);
    assertFalse(output.isPresent());
  }

  @Test
  public void testGetBetween() {
    Text text = mock(Text.class);
    Annotation annotation = getAnnotation(1, 10);
    AnnotationStore annotationStore = getAnnotationStore(annotation);
    when(text.getAnnotations()).thenReturn(annotationStore);
    doCallRealMethod().when(text).getBetween(Mockito.anyInt(), Mockito.anyInt());

    List<Annotation> between = text.getBetween(0, 11).collect(Collectors.toList());
    assertEquals(1, between.size());
    assertEquals(annotation, between.get(0));
  }

  @Test
  public void testGetBefore() {
    Text text = mock(Text.class);
    Annotation annotation = getAnnotation(5, 10);
    AnnotationStore annotationStore = getAnnotationStore(annotation);
    when(text.getAnnotations()).thenReturn(annotationStore);
    doCallRealMethod().when(text).getBefore(Mockito.anyInt());

    List<Annotation> empty = text.getBefore(4).collect(Collectors.toList());
    assertEquals(0, empty.size());

    List<Annotation> before = text.getBefore(11).collect(Collectors.toList());
    assertEquals(1, before.size());
    assertEquals(annotation, before.get(0));
  }

  @Test
  public void testGetAfter() {
    Text text = mock(Text.class);
    Annotation annotation = getAnnotation(1, 10);
    AnnotationStore annotationStore = getAnnotationStore(annotation);
    when(text.getAnnotations()).thenReturn(annotationStore);
    doCallRealMethod().when(text).getAfter(Mockito.anyInt());

    List<Annotation> after = text.getAfter(0).collect(Collectors.toList());
    assertEquals(1, after.size());
    assertEquals(annotation, after.get(0));

    List<Annotation> noneAfter = text.getAfter(20).collect(Collectors.toList());
    assertEquals(0, noneAfter.size());
  }

  @Test
  public void testGetCovers() {
    Text text = mock(Text.class);
    Annotation annotation = getAnnotation(5, 10);
    AnnotationStore annotationStore = getAnnotationStore(annotation);
    when(text.getAnnotations()).thenReturn(annotationStore);
    doCallRealMethod().when(text).getCovers(Mockito.anyInt(), Mockito.anyInt());

    List<Annotation> covers = text.getCovers(7, 8).collect(Collectors.toList());
    assertEquals(1, covers.size());
    assertEquals(annotation, covers.get(0));

    List<Annotation> empty = text.getCovers(1, 11).collect(Collectors.toList());
    assertEquals(0, empty.size());
  }

  private Annotation getAnnotation(int begin, int end) {
    SpanBounds bounds = new SpanBounds(begin, end);
    Annotation annotation = mock(Annotation.class);
    doReturn(Optional.of(bounds)).when(annotation).getBounds(Mockito.eq(SpanBounds.class));
    return annotation;
  }

  private AnnotationStore getAnnotationStore(Annotation annotation) {
    AnnotationStore annotationStore = mock(AnnotationStore.class);
    when(annotationStore.getByBounds(Mockito.eq(SpanBounds.class)))
        .thenAnswer((Answer<Stream<Annotation>>) invocation -> Stream.of(annotation));
    return annotationStore;
  }
}
