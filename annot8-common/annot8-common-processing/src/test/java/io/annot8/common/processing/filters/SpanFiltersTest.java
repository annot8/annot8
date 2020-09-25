/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.processing.filters;

import static org.assertj.core.api.Assertions.assertThat;

import io.annot8.api.annotations.Annotation;
import io.annot8.api.filters.Filter;
import io.annot8.common.data.bounds.SpanBounds;
import io.annot8.testing.testimpl.TestAnnotation;
import io.annot8.testing.testimpl.TestAnnotationStore;
import io.annot8.testing.testimpl.content.TestStringContent;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SpanFiltersTest {

  TestAnnotationStore store;

  @BeforeEach
  public void beforeEach() {
    store = new TestAnnotationStore(new TestStringContent());
    store.save(new TestAnnotation("1", "c", "t1", new SpanBounds(1, 5)));
    store.save(new TestAnnotation("2", "c", "t1", new SpanBounds(2, 7)));
    store.save(new TestAnnotation("3", "c", "t2", new SpanBounds(5, 8)));
    store.save(new TestAnnotation("4", "c", "t2", new SpanBounds(10, 20)));
    store.save(new TestAnnotation("5", "c", "t3", new SpanBounds(15, 19)));

    store
        .create()
        .withId("6")
        .withType("p1")
        .withBounds(new SpanBounds(11, 12))
        .withProperty("test", "a")
        .save();

    store
        .create()
        .withId("7")
        .withType("p1")
        .withBounds(new SpanBounds(13, 13))
        .withProperty("test", 5)
        .save();

    store
        .create()
        .withId("8")
        .withType("p1")
        .withBounds(new SpanBounds(17, 19))
        .withProperty("missing", 5)
        .save();
  }

  @Test
  void inside() {
    assertThat(filter(SpanFilters.inside(new SpanBounds(0, 10)))).containsExactly("1", "2", "3");
  }

  @Test
  void encloses() {
    assertThat(filter(SpanFilters.encloses(new SpanBounds(16, 18)))).containsExactly("4", "5");
  }

  @Test
  void overlaps() {
    assertThat(filter(SpanFilters.overlaps(new SpanBounds(16, 18)))).containsExactly("4", "5", "8");
  }

  @Test
  void before() {
    assertThat(filter(SpanFilters.before(new SpanBounds(12, 13)))).containsExactly("1", "2", "3");
  }

  @Test
  void after() {
    assertThat(filter(SpanFilters.after(new SpanBounds(12, 13)))).containsExactly("5", "8");
  }

  private Stream<String> filter(Filter<Annotation> f) {
    return store.filter(f).map(Annotation::getId);
  }
}
