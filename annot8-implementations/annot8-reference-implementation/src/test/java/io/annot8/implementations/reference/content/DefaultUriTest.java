/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.reference.content;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import io.annot8.api.data.Content.Builder;
import io.annot8.api.exceptions.IncompleteException;
import io.annot8.common.data.content.UriContent;
import io.annot8.implementations.reference.content.DefaultUri.BuilderFactory;
import io.annot8.testing.testimpl.TestItem;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;

public class DefaultUriTest {

  private static final String URL = "https://www.test.co.uk";

  @Test
  public void testDefaultURLBuilderFactory() {
    BuilderFactory factory = new DefaultUri.BuilderFactory();
    Builder<UriContent, URI> defaultURLBuilder = factory.create(new TestItem());
    assertNotNull(defaultURLBuilder);
  }

  @Test
  public void testDefaultURLBuilder() {
    BuilderFactory builderFactory = new BuilderFactory();
    Builder<UriContent, URI> urlContentBuilder = builderFactory.create(new TestItem());

    String id = "id";
    String description = "test";
    String key = "testKey";
    String prop = "testValue";
    UriContent content = null;
    try {
      content =
          urlContentBuilder
              .withId(id)
              .withDescription(description)
              .withData(new URI(URL))
              .withProperty(key, prop)
              .save();
    } catch (URISyntaxException | IncompleteException e) {
      fail("Test should not fail here", e);
    }

    assertNotNull(content);
    assertEquals(description, content.getDescription());
    assertEquals(URL, content.getData().toString());
    assertEquals(URI.class, content.getDataClass());
    assertEquals(UriContent.class, content.getContentClass());
    assertEquals(id, content.getId());
    assertTrue(content.getProperties().has(key));
    assertEquals(prop, content.getProperties().get(key).get());
    assertNotNull(content.getAnnotations());
  }

  @Test
  public void testDefaultURLBuilderFillsArgs() {
    BuilderFactory builderFactory = new BuilderFactory();
    Builder<UriContent, URI> urlContentBuilder = builderFactory.create(new TestItem());

    UriContent content = null;
    try {
      content = urlContentBuilder.withDescription("test").withData(new URI(URL)).save();
    } catch (URISyntaxException | IncompleteException e) {
      fail("Test should not fail here", e);
    }

    assertNotNull(content.getId());
    assertNotNull(content.getProperties());
    assertTrue(content.getProperties().getAll().isEmpty());
  }
}
