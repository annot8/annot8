/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.processing.indices;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.annot8.api.annotations.Annotation;
import io.annot8.common.data.bounds.SpanBounds;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class SpanIndices {

  private final List<Annotation> over;
  private final List<Annotation> under;

  public SpanIndices(Stream<Annotation> overStream, Stream<Annotation> underStream) {
    over = filterSpanBounds(overStream);
    under = filterSpanBounds(underStream);
  }

  public Multimap<Annotation, Annotation> topDown() {
    // Suppressed because it's (orElse(null) never happens as those are filtered out in the
    // constructor
    @SuppressWarnings("ConstantConditions")
    Map<Annotation, SpanBounds> underMap =
        under.stream()
            .collect(toMap(Function.identity(), e -> e.getBounds(SpanBounds.class).orElse(null)));

    HashMultimap<Annotation, Annotation> multimap = HashMultimap.create();

    for (Annotation o : over) {

      Optional<SpanBounds> oBounds = o.getBounds(SpanBounds.class);

      if (oBounds.isPresent()) {
        List<Annotation> found = findEnclosedAnnotations(oBounds.get(), underMap);

        if (!found.isEmpty()) {
          multimap.putAll(o, found);
        }
      }
    }

    return multimap;
  }

  private List<Annotation> filterSpanBounds(Stream<Annotation> stream) {
    return stream.filter(s -> s.getBounds(SpanBounds.class).isPresent()).collect(toList());
  }

  private List<Annotation> findEnclosedAnnotations(
      SpanBounds bounds, Map<Annotation, SpanBounds> list) {

    return list.entrySet().stream()
        .filter(e -> bounds.isWithin(e.getValue()))
        .map(Map.Entry::getKey)
        .collect(toList());
  }

  public Multimap<Annotation, Annotation> bottomUp() {
    // Suppressed because it's (orElse(null) never happens as those are filtered out in the
    // constructor
    @SuppressWarnings("ConstantConditions")
    Map<Annotation, SpanBounds> overMap =
        over.stream().collect(toMap(e -> e, e -> e.getBounds(SpanBounds.class).orElse(null)));

    HashMultimap<Annotation, Annotation> multimap = HashMultimap.create();

    for (Annotation u : under) {

      Optional<SpanBounds> oBounds = u.getBounds(SpanBounds.class);

      if (oBounds.isPresent()) {
        List<Annotation> found = findInsideAnnotations(oBounds.get(), overMap);

        if (!found.isEmpty()) {
          multimap.putAll(u, found);
        }
      }
    }

    return multimap;
  }

  private List<Annotation> findInsideAnnotations(
      SpanBounds bounds, Map<Annotation, SpanBounds> list) {
    return list.entrySet().stream()
        .filter(e -> e.getValue().isWithin(bounds))
        .map(Map.Entry::getKey)
        .collect(toList());
  }
}
