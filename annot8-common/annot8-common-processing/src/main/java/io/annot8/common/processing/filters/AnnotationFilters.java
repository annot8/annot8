/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.processing.filters;

import io.annot8.api.annotations.Annotation;
import io.annot8.api.bounds.Bounds;
import io.annot8.api.filters.AndFilter;
import io.annot8.api.filters.Filter;
import io.annot8.api.filters.NotFilter;
import java.util.Optional;

public class AnnotationFilters {

  private AnnotationFilters() {
    // Singleton
  }

  public static Filter<Annotation> byType(String type) {
    return new TypeFilter(type);
  }

  public static <B extends Bounds> Filter<Annotation> hasBounds(Class<B> bounds) {
    return new BoundsFilter(bounds);
  }

  public static Filter<Annotation> byProperty(String key) {
    return new HasPropertyFilter(key, null, null);
  }

  public static Filter<Annotation> byProperty(String key, Class<?> clazz) {
    return new HasPropertyFilter(key, clazz, null);
  }

  public static Filter<Annotation> byProperty(String key, Object value) {
    return new HasPropertyFilter(key, null, value);
  }

  public static Filter<Annotation> not(Filter<Annotation> filter) {
    return new NotFilter<>(filter);
  }

  public static Filter<Annotation> and(Filter<Annotation>... filters) {
    return new AndFilter<>(filters);
  }

  public static class TypeFilter implements Filter<Annotation> {
    private String type;

    public TypeFilter(String type) {
      this.type = type;
    }

    @Override
    public boolean test(Annotation annotation) {
      return annotation.getType().equals(type);
    }
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

  public static class HasPropertyFilter implements Filter<Annotation> {

    private final String key;
    private final Class<?> valueClass;
    private final Object value;

    public HasPropertyFilter(String key, Class<?> valueClass, Object value) {
      this.key = key;
      this.valueClass = valueClass;
      this.value = value;
    }

    @Override
    public boolean test(Annotation annotation) {
      Optional<Object> o = annotation.getProperties().get(key);
      if (o.isEmpty()) {
        return false;
      } else if (value != null) {
        return o.get().equals(value);
      } else if (valueClass != null) {
        return valueClass.isInstance(o.get());
      } else {
        return true;
      }
    }
  }
}
