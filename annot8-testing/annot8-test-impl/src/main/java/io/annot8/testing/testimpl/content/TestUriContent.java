/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.testing.testimpl.content;

import io.annot8.api.data.Content;
import io.annot8.api.data.Item;
import io.annot8.api.properties.ImmutableProperties;
import io.annot8.common.data.content.UriContent;
import io.annot8.implementations.support.content.AbstractContentBuilder;
import io.annot8.implementations.support.content.AbstractContentBuilderFactory;
import io.annot8.testing.testimpl.AbstractTestContent;
import java.net.URI;
import java.util.function.Supplier;

public class TestUriContent extends AbstractTestContent<URI> implements UriContent {

  public TestUriContent() {
    this(null);
  }

  public TestUriContent(Item item) {
    super(item, URI.class);
    setData(URI.create("http://www.example.com"));
  }

  public TestUriContent(
      Item item, String id, String description, ImmutableProperties properties, URI data) {
    super(item, URI.class, id, description, properties, data);
  }

  @Override
  public Class<? extends Content<URI>> getContentClass() {
    return UriContent.class;
  }

  public static class TestUriBuilder extends AbstractContentBuilder<URI, UriContent> {

    public TestUriBuilder(Item item) {
      super(item);
    }

    @Override
    protected UriContent create(
        String id, String description, ImmutableProperties properties, Supplier<URI> data) {
      return new TestUriContent(getItem(), id, description, properties, data.get());
    }
  }

  public static class TestURLBuilderFactory extends AbstractContentBuilderFactory<URI, UriContent> {

    public TestURLBuilderFactory() {
      super(URI.class, UriContent.class);
    }

    @Override
    public Builder<UriContent, URI> create(Item item) {
      return new TestUriBuilder(item);
    }
  }
}
