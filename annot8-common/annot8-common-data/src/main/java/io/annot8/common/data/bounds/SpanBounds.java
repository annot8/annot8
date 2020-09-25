/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.bounds;

import io.annot8.api.bounds.Bounds;
import io.annot8.api.data.Content;
import io.annot8.api.exceptions.InvalidBoundsException;
import java.util.Objects;
import java.util.Optional;
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;

/** Implementation of Bounds for a simple 2D span, such as an offset of text. */
public class SpanBounds implements Bounds {

  private final int begin;
  private final int end;

  /**
   * Create a new object with the specified begin and end values
   *
   * @param begin start offset, at least 0
   * @param end (must be greater than begin)
   */
  @JsonbCreator
  public SpanBounds(@JsonbProperty("begin") final int begin, @JsonbProperty("end") final int end) {
    if (begin < 0) {
      throw new InvalidBoundsException("Begin must be greater than or equal to 0");
    }

    if (end < begin) {
      throw new InvalidBoundsException("End must be greater than or equal to Begin");
    }

    this.begin = begin;
    this.end = end;
  }

  /**
   * The begin position of this object
   *
   * @return the offset
   */
  public int getBegin() {
    return begin;
  }

  /**
   * The end position of this object
   *
   * @return the offset
   */
  public int getEnd() {
    return end;
  }

  /**
   * The length of this span
   *
   * @return length (end-begin)
   */
  @JsonbTransient
  public int getLength() {
    return end - begin;
  }

  @Override
  public <D, C extends Content<D>, R> Optional<R> getData(C content, Class<R> requiredClass) {

    D data = content.getData();

    if (requiredClass.isAssignableFrom(String.class) && data.getClass().equals(String.class)) {
      String s = (String) data;

      int normBegin = Math.max(0, begin);
      int normEnd = Math.min(s.length(), end);

      @SuppressWarnings("unchecked") // This is checked R = String.class
      R r = (R) s.substring(normBegin, normEnd);

      return Optional.of(r);
    }

    return Optional.empty();
  }

  @Override
  public <D, C extends Content<D>> boolean isValid(C content) {

    D data = content.getData();

    if (data.getClass().equals(String.class)) {
      String s = (String) data;
      return end <= s.length();
    }

    return false;
  }

  @Override
  public String toString() {
    return this.getClass().getName() + " [begin=" + begin + ", end=" + end + "]";
  }

  @Override
  public int hashCode() {
    return Objects.hash(begin, end);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof SpanBounds)) {
      return false;
    }

    SpanBounds sb = (SpanBounds) o;

    return Objects.equals(begin, sb.getBegin()) && Objects.equals(end, sb.getEnd());
  }

  /**
   * If the provided begin and end is within this object's bounds
   *
   * @param begin the begin offset
   * @param end the end offset
   * @return true if completely within
   */
  public boolean isWithin(int begin, int end) {
    return this.begin <= begin && end <= this.end;
  }

  /**
   * If the provided utils bounds is within this object's bounds
   *
   * @param other the bounds to test against
   * @return true if completely within
   */
  public boolean isWithin(SpanBounds other) {
    return this.isWithin(other.getBegin(), other.getEnd());
  }

  /**
   * Check if this is before (lower position than) the offset
   *
   * @param offset the offset to test against
   * @return true is this is before
   */
  public boolean isBefore(int offset) {
    return getEnd() < offset;
  }

  /**
   * Check if this is before (lower position than) the utils
   *
   * @param other the bounds to test against
   * @return true is this is before
   */
  public boolean isBefore(SpanBounds other) {
    return isBefore(other.getBegin());
  }

  /**
   * Check if this is after (higher position than) the utils
   *
   * @param other the bounds to test against
   * @return true is this is after
   */
  public boolean isAfter(SpanBounds other) {
    return isAfter(other.getEnd());
  }

  /**
   * Check if this is after (higher position than) the offset
   *
   * @param offset the bounds to test against
   * @return true is this is after
   */
  public boolean isAfter(int offset) {
    return getBegin() > offset;
  }

  /**
   * Check if this is same bounds as the utils
   *
   * @param other the bounds to test against
   * @return true if the same
   */
  public boolean isSame(SpanBounds other) {
    return getBegin() == other.getBegin() && getEnd() == other.getEnd();
  }

  /**
   * Check if the bounds overlaps one another
   *
   * @param other the bounds to test against
   * @return true if the same
   */
  public boolean isOverlaps(SpanBounds other) {
    return !isAfter(other) && !isBefore(other);
  }
}
