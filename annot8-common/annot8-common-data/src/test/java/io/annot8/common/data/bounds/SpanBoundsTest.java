/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.bounds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.annot8.api.data.Content;
import io.annot8.api.exceptions.Annot8RuntimeException;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class SpanBoundsTest {

  @Test
  void testSpanBounds() {
    SpanBounds sb = new SpanBounds(3, 10);

    assertEquals(3, sb.getBegin());
    assertEquals(10, sb.getEnd());
    assertEquals(7, sb.getLength());

    assertNotNull(sb.toString());
  }

  @Test
  void testNegativeBegin() {
    assertThrows(Annot8RuntimeException.class, () -> new SpanBounds(-1, 10));
  }

  @Test
  void testEndLessThanBegin() {
    assertThrows(Annot8RuntimeException.class, () -> new SpanBounds(10, 5));
  }

  @Test
  void testIsValidForString() {
    Content<String> content = mock(Content.class);
    when(content.getData()).thenReturn("1234567890abcde");

    SpanBounds sb = new SpanBounds(5, 10);

    assertTrue(sb.isValid(content));
  }

  @Test
  void testGetDataForString() {
    Content<String> content = mock(Content.class);
    when(content.getData()).thenReturn("1234567890abcde");

    SpanBounds sb = new SpanBounds(5, 10);

    Optional<String> data = sb.getData(content, String.class);
    assertEquals("67890", data.get());
  }

  @Test
  public void testGetDataForNonValidContent() {
    Content<Integer> content = mock(Content.class);
    when(content.getData()).thenReturn(1);

    SpanBounds span = new SpanBounds(2, 3);

    Optional<Integer> data = span.getData(content, Integer.class);
    assertFalse(data.isPresent());
  }

  @Test
  public void testIsValidForNonValidContent() {
    Content<Integer> content = mock(Content.class);
    when(content.getData()).thenReturn(1);

    SpanBounds span = new SpanBounds(2, 3);

    assertFalse(span.isValid(content));
  }

  @Test
  public void testIsWithin() {
    SpanBounds bounds = new SpanBounds(1, 10);
    SpanBounds within = new SpanBounds(3, 8);

    assertTrue(bounds.isWithin(within));

    SpanBounds notWithin = new SpanBounds(20, 30);

    assertFalse(bounds.isWithin(notWithin));

    SpanBounds same = new SpanBounds(1, 10);

    assertTrue(bounds.isWithin(same));
  }

  @Test
  public void testIsBeforeBounds() {
    SpanBounds bounds = new SpanBounds(5, 10);
    SpanBounds before = new SpanBounds(1, 4);
    SpanBounds after = new SpanBounds(11, 12);

    assertTrue(before.isBefore(bounds));
    assertFalse(after.isBefore(bounds));
    assertFalse(bounds.isBefore(bounds));
  }

  @Test
  public void testIsAfterBounds() {
    SpanBounds bounds = new SpanBounds(5, 10);
    SpanBounds before = new SpanBounds(1, 4);
    SpanBounds after = new SpanBounds(11, 12);

    assertTrue(after.isAfter(bounds));
    assertFalse(before.isAfter(bounds));
    assertFalse(bounds.isAfter(bounds));
  }

  @Test
  public void testIsSame() {
    SpanBounds bounds = new SpanBounds(5, 10);
    SpanBounds different = new SpanBounds(1, 4);
    SpanBounds sameBounds = new SpanBounds(5, 10);

    assertTrue(bounds.isSame(sameBounds));
    assertFalse(bounds.isSame(different));
  }

  @Test
  public void testIsOverlaps() {
    SpanBounds bounds = new SpanBounds(5, 10);
    SpanBounds overlapping = new SpanBounds(7, 8);
    SpanBounds slightOverlap = new SpanBounds(4, 6);

    assertTrue(overlapping.isOverlaps(bounds));
    assertTrue(bounds.isOverlaps(overlapping));
    assertTrue(slightOverlap.isOverlaps(bounds));
    assertTrue(bounds.isOverlaps(slightOverlap));
    assertFalse(overlapping.isOverlaps(slightOverlap));
  }

  @Test
  public void testEquals() {
    SpanBounds bounds = new SpanBounds(1, 2);
    SpanBounds bounds2 = new SpanBounds(1, 2);
    SpanBounds bounds3 = new SpanBounds(3, 5);

    assertEquals(bounds, bounds);
    assertEquals(bounds, bounds2);
    assertNotEquals(bounds, bounds3);
    assertNotEquals(null, bounds);
  }
}
