/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.testing.tck.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import io.annot8.api.data.Content;
import io.annot8.api.data.Item;
import io.annot8.api.exceptions.IncompleteException;
import io.annot8.api.exceptions.UnsupportedContentException;
import io.annot8.common.data.content.Text;
import io.annot8.testing.testimpl.TestConstants;
import java.util.Optional;
import org.junit.jupiter.api.Test;

/**
 * Abstract class providing tests for non default methods on provided implementations of {@link
 * Item}
 */
public abstract class AbstractItemTest {

  protected abstract Item getItem();

  @Test
  public void testGetContent() {
    Item item = getItem();
    try {
      item.createContent(Text.class)
          .withData(() -> "test")
          .withId(TestConstants.CONTENT_ID)
          .withDescription(TestConstants.CONTENT_DESCRIPTION)
          .save();
    } catch (IncompleteException | UnsupportedContentException e) {
      fail("Test should not fail here", e);
    }

    Optional<Content<?>> optional = item.getContent(TestConstants.CONTENT_ID);
    assertTrue(optional.isPresent());
    Content<?> content = optional.get();
    assertEquals(TestConstants.CONTENT_ID, content.getId());
    assertEquals(TestConstants.CONTENT_DESCRIPTION, content.getDescription());
    assertEquals("test", content.getData());
  }

  @Test
  public void testGetContents() {
    Item item = getItem();

    try {
      item.createContent(Text.class)
          .withData(() -> "test")
          .withDescription(TestConstants.CONTENT_DESCRIPTION)
          .save();
      item.createContent(Text.class).withData(() -> "test2").withDescription("content2").save();
    } catch (UnsupportedContentException | IncompleteException e) {
      fail("Test should not error here", e);
    }

    assertThat(item.getContents().map(Content::getData).map(String.class::cast))
        .containsExactlyInAnyOrder("test", "test2");
    assertThat(item.getContents().map(Content::getDescription))
        .containsExactlyInAnyOrder(TestConstants.CONTENT_DESCRIPTION, "content2");
  }

  @Test
  public void testCreate() {
    Item item = getItem();

    Text test = null;
    try {
      test =
          item.createContent(Text.class)
              .withDescription(TestConstants.CONTENT_DESCRIPTION)
              .withData("test")
              .save();
    } catch (UnsupportedContentException | IncompleteException e) {
      fail("Test should not throw an exception.", e);
    }

    assertNotNull(test);
    assertEquals(TestConstants.CONTENT_DESCRIPTION, test.getDescription());
    assertEquals("test", test.getData());
    assertNotNull(test.getId());
  }

  @Test
  public void testRemoveContent() {
    Item item = getItem();
    try {
      item.createContent(Text.class)
          .withData(() -> "test")
          .withId(TestConstants.CONTENT_ID)
          .withDescription(TestConstants.CONTENT_DESCRIPTION)
          .save();
    } catch (IncompleteException | UnsupportedContentException e) {
      fail("Test should not fail here", e);
    }

    assertThat(item.getContents()).isNotEmpty();

    item.removeContent(TestConstants.CONTENT_ID);

    assertThat(item.getContents()).isEmpty();
  }

  @Test
  public void testDiscard() {
    Item item = getItem();
    assertFalse(item.isDiscarded());
    item.discard();
    assertTrue(item.isDiscarded());
  }
}
