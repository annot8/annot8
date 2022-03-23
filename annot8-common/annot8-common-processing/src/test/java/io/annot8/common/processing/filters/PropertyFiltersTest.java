/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.processing.filters;

import static org.assertj.core.api.Assertions.assertThat;

import io.annot8.api.annotations.Annotation;
import io.annot8.api.filters.Filter;
import io.annot8.api.helpers.WithId;
import io.annot8.api.helpers.WithProperties;
import io.annot8.common.data.bounds.PositionBounds;
import io.annot8.common.data.bounds.SpanBounds;
import io.annot8.testing.testimpl.TestAnnotation;
import io.annot8.testing.testimpl.TestProperties;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PropertyFiltersTest {

  List<Annotation> annotations;

  @BeforeEach
  void beforeEach() {
    annotations =
        List.of(
            new TestAnnotation("1", "c", "t1", new PositionBounds(1)),
            new TestAnnotation(
                "2", "c", "t1", new PositionBounds(2), new TestProperties("test", 1)),
            new TestAnnotation(
                "3", "c", "t2", new PositionBounds(3), new TestProperties("test", "2")),
            new TestAnnotation(
                "4", "c", "t2", new SpanBounds(0, 4), new TestProperties("test", 1, "other", "a")),
            new TestAnnotation(
                "5",
                "c",
                "t3",
                new PositionBounds(4),
                new TestProperties("test", "2", "other", "b")));
  }

  @Test
  public void byPropertyKey() {
    assertThat(filter(PropertyFilters.byProperty("test"))).containsExactly("2", "3", "4", "5");
  }

  @Test
  public void byPropertyKeyClass() {
    assertThat(filter(PropertyFilters.byProperty("test", String.class))).containsExactly("3", "5");
  }

  @Test
  public void byPropertyKeyValue() {
    assertThat(filter(PropertyFilters.byProperty("test", 3))).isEmpty();
  }

  @Test
  public void byOtherPropertyKeyValue() {
    assertThat(filter(PropertyFilters.byProperty("other", "b"))).containsExactly("5");
  }

  private Stream<String> filter(Filter<WithProperties> f) {
    return annotations.stream().filter(f::test).map(WithId::getId);
  }
}
