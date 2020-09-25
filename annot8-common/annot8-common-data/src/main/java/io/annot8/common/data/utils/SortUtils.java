/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.utils;

import io.annot8.api.annotations.Annotation;
import io.annot8.common.data.bounds.SpanBounds;
import java.util.Comparator;

public class SortUtils {

  private SortUtils() {
    // Private constructor for utility class
  }

  public static final Comparator<Annotation> SORT_BY_SPANBOUNDS =
      Comparator.comparingInt(
          a -> a.getBounds(SpanBounds.class).orElse(new SpanBounds(0, 0)).getBegin());
}
