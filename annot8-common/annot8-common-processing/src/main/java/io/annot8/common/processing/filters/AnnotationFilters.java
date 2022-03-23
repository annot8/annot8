/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.processing.filters;

import io.annot8.api.annotations.Annotation;
import io.annot8.api.bounds.Bounds;
import io.annot8.api.filters.AndFilter;
import io.annot8.api.filters.Filter;
import io.annot8.api.filters.NotFilter;

public class AnnotationFilters {

  private AnnotationFilters() {
    // Singleton
  }

  public static Filter<Annotation> byType(String type) {
    return new TypeFilters.TypeFilter<>(type);
  }

  public static <B extends Bounds> Filter<Annotation> hasBounds(Class<B> bounds) {
    return new BoundsFilter(bounds);
  }

  public static Filter<Annotation> byProperty(String key) {
    return PropertyFilters.byProperty(key);
  }

  public static Filter<Annotation> byProperty(String key, Class<?> clazz) {
    return PropertyFilters.byProperty(key, clazz);
  }

  public static Filter<Annotation> byProperty(String key, Object value) {
    return PropertyFilters.byProperty(key, value);
  }

  public static Filter<Annotation> not(Filter<Annotation> filter) {
    return new NotFilter<>(filter);
  }

  public static Filter<Annotation> and(Filter<Annotation>... filters) {
    return new AndFilter<>(filters);
  }

  private static class BoundsFilter implements Filter<Annotation> {

    private Class<? extends Bounds> boundsClass;

    public BoundsFilter(Class<? extends Bounds> boundsClass) {
      this.boundsClass = boundsClass;
    }

    public boolean test(Annotation annotation) {
      return boundsClass.isInstance(annotation.getBounds());
    }
  }
}
