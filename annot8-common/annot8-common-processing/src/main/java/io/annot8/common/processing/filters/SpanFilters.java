/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.processing.filters;

import io.annot8.api.annotations.Annotation;
import io.annot8.api.filters.Filter;
import io.annot8.common.data.bounds.SpanBounds;
import java.util.Optional;

public class SpanFilters {

  private SpanFilters() {
    // Singleton
  }

  public static Filter<Annotation> inside(SpanBounds bounds) {
    return new InsideFilter(bounds);
  }

  public static Filter<Annotation> encloses(SpanBounds bounds) {
    return new EnclosesFilter(bounds);
  }

  public static Filter<Annotation> overlaps(SpanBounds bounds) {
    return new OverlapsFilter(bounds);
  }

  public static Filter<Annotation> before(SpanBounds bounds) {
    return new BeforeFilter(bounds);
  }

  public static Filter<Annotation> after(SpanBounds bounds) {
    return new AfterFilter(bounds);
  }

  public abstract static class AbstractSpanBoundsFilter implements Filter<Annotation> {

    protected final SpanBounds bounds;

    public AbstractSpanBoundsFilter(SpanBounds bounds) {
      this.bounds = bounds;
    }

    @Override
    public boolean test(Annotation annotation) {
      Optional<SpanBounds> optional = annotation.getBounds(SpanBounds.class);
      if (optional.isEmpty()) {
        return false;
      } else {
        return test(bounds, optional.get());
      }
    }

    protected abstract boolean test(SpanBounds ref, SpanBounds annotation);
  }

  public static class InsideFilter extends AbstractSpanBoundsFilter {

    public InsideFilter(SpanBounds bounds) {
      super(bounds);
    }

    @Override
    protected boolean test(SpanBounds ref, SpanBounds annotation) {
      return ref.isWithin(annotation);
    }
  }

  public static class EnclosesFilter extends AbstractSpanBoundsFilter {

    public EnclosesFilter(SpanBounds bounds) {
      super(bounds);
    }

    @Override
    protected boolean test(SpanBounds ref, SpanBounds annotation) {
      return annotation.isWithin(ref);
    }
  }

  public static class OverlapsFilter extends AbstractSpanBoundsFilter {

    public OverlapsFilter(SpanBounds bounds) {
      super(bounds);
    }

    @Override
    protected boolean test(SpanBounds ref, SpanBounds annotation) {
      return ref.isOverlaps(annotation);
    }
  }

  public static class BeforeFilter extends AbstractSpanBoundsFilter {

    public BeforeFilter(SpanBounds bounds) {
      super(bounds);
    }

    @Override
    protected boolean test(SpanBounds ref, SpanBounds annotation) {
      return annotation.isBefore(ref);
    }
  }

  public static class AfterFilter extends AbstractSpanBoundsFilter {

    public AfterFilter(SpanBounds bounds) {
      super(bounds);
    }

    @Override
    protected boolean test(SpanBounds ref, SpanBounds annotation) {
      return annotation.isAfter(ref);
    }
  }
}
