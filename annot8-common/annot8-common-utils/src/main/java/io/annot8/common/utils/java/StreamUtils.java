/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.utils.java;

import java.util.Optional;
import java.util.stream.Stream;

/** Utilities for working with streams */
public final class StreamUtils {

  private StreamUtils() {
    // Singleton
  }

  /**
   * Filter out items in the stream that aren't a subclass of clazz
   *
   * @param stream the stream
   * @param clazz the class to filter and cast on on
   * @param <T> input of the stream
   * @param <S> output of the stream
   * @return stream of just clazz implementations
   */
  public static <T, S extends T> Stream<S> cast(Stream<T> stream, Class<S> clazz) {
    return stream.filter(clazz::isInstance).map(clazz::cast);
  }

  /**
   * Extract optional which are present
   *
   * @param stream the stream
   * @param <T> content of stream
   * @return stream of non-null (present optionals)
   */
  public static <T> Stream<T> getIfPresent(Stream<Optional<T>> stream) {
    return stream.filter(Optional::isPresent).map(Optional::get);
  }

  public static <T> Stream<T> append(Stream<T> stream, T... objects) {
    return Stream.concat(stream, Stream.of(objects));
  }
}
