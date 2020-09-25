/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.utils.java;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class StreamUtilsTest {

  @Test
  void testCastStreamToSubclass() {
    Stream<B> cast = StreamUtils.cast(createMixedStream(), B.class);
    assertEquals(1, cast.count());
  }

  @Test
  void testCastStreamToParentClass() {
    Stream<A> cast = StreamUtils.cast(createMixedStream(), A.class);
    assertEquals(3, cast.count());
  }

  @Test
  void testCastStreamToMissingSubclass() {
    Stream<C> cast = StreamUtils.cast(createMixedStream(), C.class);
    assertEquals(0, cast.count());
  }

  @Test
  void testGetIfPresent() {
    List<Optional<String>> input = new LinkedList<>();

    input.add(Optional.of("a"));
    input.add(Optional.empty());
    input.add(Optional.of("b"));
    input.add(Optional.of("c"));
    input.add(Optional.empty());
    input.add(Optional.empty());

    List<String> output = StreamUtils.getIfPresent(input.stream()).collect(Collectors.toList());

    assertEquals(3, output.size());
    assertEquals("a", output.get(0));
    assertEquals("b", output.get(1));
    assertEquals("c", output.get(2));
  }

  private Stream<A> createMixedStream() {
    return Stream.of(new A(), new A(), new B());
  }

  private static class A {}

  private static class B extends A {}

  private static class C extends A {}
}
