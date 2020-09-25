/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.processing.filters;

import static org.assertj.core.api.Assertions.assertThat;

import io.annot8.api.annotations.Annotation;
import io.annot8.api.filters.Filter;
import io.annot8.common.data.bounds.PositionBounds;
import io.annot8.common.data.bounds.SpanBounds;
import io.annot8.testing.testimpl.TestAnnotation;
import io.annot8.testing.testimpl.TestAnnotationStore;
import io.annot8.testing.testimpl.content.TestStringContent;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnnotationFiltersTest {

  TestAnnotationStore store;

  @BeforeEach
  public void beforeEach() {
    store = new TestAnnotationStore(new TestStringContent());
    store.save(new TestAnnotation("1", "c", "t1", new PositionBounds(1)));
    store.save(new TestAnnotation("2", "c", "t1", new PositionBounds(2)));
    store.save(new TestAnnotation("3", "c", "t2", new PositionBounds(3)));
    store.save(new TestAnnotation("4", "c", "t2", new SpanBounds(0, 4)));
    store.save(new TestAnnotation("5", "c", "t3", new PositionBounds(4)));

    store
        .create()
        .withId("6")
        .withType("p1")
        .withBounds(new SpanBounds(1, 2))
        .withProperty("test", "a")
        .save();

    store
        .create()
        .withId("7")
        .withType("p1")
        .withBounds(new SpanBounds(1, 5))
        .withProperty("test", 5)
        .save();

    store
        .create()
        .withId("8")
        .withType("p1")
        .withBounds(new SpanBounds(7, 9))
        .withProperty("missing", 5)
        .save();
  }

  @Test
  void byType() {
    assertThat(filter(AnnotationFilters.byType("t1"))).containsExactly("1", "2");
  }

  @Test
  void hasBounds() {
    assertThat(filter(AnnotationFilters.hasBounds(PositionBounds.class)))
        .containsExactly("1", "2", "3", "5");
  }

  @Test
  void byPropertyKey() {
    assertThat(filter(AnnotationFilters.byProperty("test"))).containsExactly("6", "7");
  }

  @Test
  void byPropertyWithClass() {
    assertThat(filter(AnnotationFilters.byProperty("test", String.class))).containsExactly("6");
  }

  @Test
  void byPropertyWithValue() {
    assertThat(filter(AnnotationFilters.byProperty("test", 5))).containsExactly("7");
  }

  private Stream<String> filter(Filter<Annotation> f) {
    return store.filter(f).map(Annotation::getId);
  }
}
