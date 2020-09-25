/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.processing.filters;

import static org.assertj.core.api.Assertions.assertThat;

import io.annot8.api.annotations.Group;
import io.annot8.api.filters.Filter;
import io.annot8.api.stores.GroupStore;
import io.annot8.testing.testimpl.TestAnnotation;
import io.annot8.testing.testimpl.TestGroupStore;
import io.annot8.testing.testimpl.TestItem;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GroupFiltersTest {

  GroupStore store;

  @BeforeEach
  void beforeEach() {
    store = new TestGroupStore(new TestItem());

    store
        .create()
        .withType("gt1")
        .withAnnotation("r1", new TestAnnotation("1", "c", "t1"))
        .withAnnotation("r2", new TestAnnotation("2", "c", "t2"))
        .withId("g1")
        .save();

    store
        .create()
        .withType("gt2")
        .withAnnotation("r1", new TestAnnotation("1", "c", "t3"))
        .withAnnotation("r2", new TestAnnotation("2", "c", "t4"))
        .withId("g2")
        .withProperty("test", "string")
        .save();

    store
        .create()
        .withType("gt3")
        .withAnnotation("r3", new TestAnnotation("1", "c", "t5"))
        .withAnnotation("r4", new TestAnnotation("2", "c", "t6"))
        .withId("g3")
        .withProperty("test", 3)
        .save();
  }

  @Test
  public void byType() {

    assertThat(filter(GroupFilters.byType("gt1"))).containsExactly("g1");
  }

  @Test
  public void byPropertyKey() {
    assertThat(filter(GroupFilters.byProperty("test"))).containsExactly("g2", "g3");
  }

  @Test
  public void byPropertyKeyClass() {
    assertThat(filter(GroupFilters.byProperty("test", String.class))).containsExactly("g2");
  }

  @Test
  public void byPropertyKeyValue() {
    assertThat(filter(GroupFilters.byProperty("test", 3))).containsExactly("g3");
  }

  @Test
  public void hasRoles() {
    assertThat(filter(GroupFilters.hasRoles("r2"))).containsExactly("g1", "g2");
  }

  @Test
  public void includesAnnotation() {
    assertThat(filter(GroupFilters.includesAnnotation(AnnotationFilters.byType("t1"))))
        .containsExactly("g1");
  }

  private Stream<String> filter(Filter<Group> f) {
    return store.filter(f).map(Group::getId);
  }
}
