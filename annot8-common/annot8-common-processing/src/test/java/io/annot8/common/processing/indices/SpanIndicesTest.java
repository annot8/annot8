/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.processing.indices;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.Multimap;
import io.annot8.api.annotations.Annotation;
import io.annot8.common.data.bounds.SpanBounds;
import io.annot8.testing.testimpl.TestAnnotation;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SpanIndicesTest {

  TestAnnotation o1 = new TestAnnotation("o1", "c", "t", new SpanBounds(0, 10));
  TestAnnotation o2 = new TestAnnotation("o2", "c", "t", new SpanBounds(20, 30));
  TestAnnotation o3 = new TestAnnotation("o3", "c", "t", new SpanBounds(40, 50));
  TestAnnotation o4 = new TestAnnotation("o4", "c", "t", new SpanBounds(22, 25));

  List<Annotation> over = List.of(o1, o2, o3, o4);

  // Exact match with o1
  TestAnnotation u1 = new TestAnnotation("u1", "c", "t", new SpanBounds(0, 10));
  // Nothing over
  TestAnnotation u2 = new TestAnnotation("u2", "c", "t", new SpanBounds(12, 18));
  // Both under o2 - second also under o4
  TestAnnotation u3 = new TestAnnotation("u3", "c", "t", new SpanBounds(21, 29));
  TestAnnotation u4 = new TestAnnotation("u4", "c", "t", new SpanBounds(18, 34));
  // overlaps o2
  TestAnnotation u5 = new TestAnnotation("u5", "c", "t", new SpanBounds(18, 22));
  TestAnnotation u6 = new TestAnnotation("u6", "c", "t", new SpanBounds(29, 30));
  // Point
  TestAnnotation u7 = new TestAnnotation("u7", "c", "t", new SpanBounds(0, 0));
  // Nothing under o3

  List<Annotation> under = List.of(u1, u2, u3, u4, u5, u6, u7);

  private io.annot8.common.processing.indices.SpanIndices si;

  @BeforeEach
  void beforeEach() {
    si = new io.annot8.common.processing.indices.SpanIndices(over.stream(), under.stream());
  }

  @Test
  void topDown() {
    Multimap<Annotation, Annotation> multimap = si.topDown();

    assertThat(multimap.get(o1)).contains(u1, u7);
    assertThat(multimap.get(o2)).contains(u3, u6);
    assertThat(multimap.get(o3)).isEmpty();
    assertThat(multimap.get(o4)).isEmpty();
  }

  @Test
  void bottomUp() {
    Multimap<Annotation, Annotation> multimap = si.bottomUp();

    assertThat(multimap.get(u1)).contains(o1);
    assertThat(multimap.get(u2)).isEmpty();
    assertThat(multimap.get(u3)).contains(o2);
    assertThat(multimap.get(u4)).isEmpty();
    assertThat(multimap.get(u5)).isEmpty();
    assertThat(multimap.get(u6)).contains(o2);
    assertThat(multimap.get(u7)).contains(o1);
  }
}
