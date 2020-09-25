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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class PositionBoundsTest {

  @Test
  void testNewPosition() {
    PositionBounds bounds = new PositionBounds(34);
    assertEquals(34, bounds.getPosition());
    assertNotNull(bounds.toString());
  }

  @Test
  void negativeIsInvalid() {
    assertThrows(Annot8RuntimeException.class, () -> new PositionBounds(-1));
  }

  @Test
  void zeroIsValid() {
    PositionBounds bounds = new PositionBounds(0);
    assertEquals(0, bounds.getPosition());
  }

  @Test
  void testGetDataForString() {
    Content<String> content = mock(Content.class);
    when(content.getData()).thenReturn("test");
    PositionBounds bounds = new PositionBounds(0);
    assertTrue(bounds.getData(content, String.class).isPresent());
  }

  @Test
  void testIsValidForString() {
    Content<String> content = mock(Content.class);
    when(content.getData()).thenReturn("test");
    PositionBounds bounds = new PositionBounds(0);
    assertTrue(bounds.isValid(content));
  }

  @Test
  public void testGetDataForCharacter() {
    Content<String> content = mock(Content.class);
    when(content.getData()).thenReturn("c");
    PositionBounds bounds = new PositionBounds(0);
    Optional<Character> data = bounds.getData(content, Character.class);
    assertTrue(data.isPresent());
    assertEquals(Character.valueOf('c'), data.get());
  }

  @Test
  public void testGetDataForArray() {
    Content<String[]> content = mock(Content.class);
    when(content.getData()).thenReturn(new String[] {"test"});
    PositionBounds bounds = new PositionBounds(0);
    Optional<String> result = bounds.getData(content, String.class);
    assertTrue(result.isPresent());
    assertEquals("test", result.get());
  }

  @Test
  public void testIsValidForArray() {
    Content<String[]> content = mock(Content.class);
    when(content.getData()).thenReturn(new String[] {"test"});
    PositionBounds bounds = new PositionBounds(0);
    assertTrue(bounds.isValid(content));
    PositionBounds invalidBounds = new PositionBounds(10);
    assertFalse(invalidBounds.isValid(content));
  }

  @Test
  public void testGetDataForList() {
    Content<List<String>> content = mock(Content.class);
    when(content.getData()).thenReturn(Collections.singletonList("test"));
    PositionBounds bounds = new PositionBounds(0);
    Optional<String> data = bounds.getData(content, String.class);
    assertTrue(data.isPresent());
    assertEquals("test", data.get());
  }

  @Test
  public void testIsValidForList() {
    Content<List<String>> content = mock(Content.class);
    when(content.getData()).thenReturn(Collections.singletonList("test"));
    PositionBounds bounds = new PositionBounds(0);
    assertTrue(bounds.isValid(content));
    PositionBounds invalidBounds = new PositionBounds(10);
    assertFalse(invalidBounds.isValid(content));
  }

  @Test
  public void testIsBefore() {
    PositionBounds p1 = new PositionBounds(1);
    PositionBounds p2 = new PositionBounds(2);
    assertTrue(p1.isBefore(p2));
    assertFalse(p2.isBefore(p1));
    assertFalse(p1.isBefore(p1));
  }

  @Test
  public void testIsAfter() {
    PositionBounds p1 = new PositionBounds(1);
    PositionBounds p2 = new PositionBounds(2);
    assertTrue(p2.isAfter(p1));
    assertFalse(p1.isAfter(p2));
    assertFalse(p1.isAfter(p1));
  }

  @Test
  public void testIsSame() {
    PositionBounds p1 = new PositionBounds(1);
    PositionBounds p2 = new PositionBounds(2);
    assertTrue(p1.isSame(p1));
    assertFalse(p1.isSame(p2));
  }

  @Test
  public void testEquals() {
    PositionBounds p1 = new PositionBounds(1);
    PositionBounds p2 = new PositionBounds(1);
    PositionBounds p3 = new PositionBounds(2);
    assertEquals(p1, p1);
    assertEquals(p1, p2);
    assertNotEquals(p1, p3);
    assertNotEquals(p1, new Object());
    assertNotEquals(null, p1);
  }
}
