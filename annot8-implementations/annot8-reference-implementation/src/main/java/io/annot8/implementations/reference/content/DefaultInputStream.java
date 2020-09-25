/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.reference.content;

import io.annot8.api.data.Content;
import io.annot8.api.data.Item;
import io.annot8.api.exceptions.Annot8RuntimeException;
import io.annot8.api.properties.ImmutableProperties;
import io.annot8.common.data.content.InputStreamContent;
import io.annot8.implementations.reference.stores.DefaultAnnotationStore;
import io.annot8.implementations.support.content.AbstractContent;
import io.annot8.implementations.support.content.AbstractContentBuilder;
import io.annot8.implementations.support.content.AbstractContentBuilderFactory;
import java.io.InputStream;
import java.util.function.Supplier;

public class DefaultInputStream extends AbstractContent<InputStream> implements InputStreamContent {

  private DefaultInputStream(
      Item item,
      String id,
      String description,
      ImmutableProperties properties,
      Supplier<InputStream> data) {
    super(
        item,
        InputStream.class,
        InputStreamContent.class,
        DefaultAnnotationStore::new,
        id,
        description,
        properties,
        data);
  }

  public static class Builder extends AbstractContentBuilder<InputStream, DefaultInputStream> {

    public Builder(Item item) {
      super(item);
    }

    @Override
    public Content.Builder<DefaultInputStream, InputStream> withData(InputStream data) {
      throw new Annot8RuntimeException(
          "Must use a Supplier to provider InputStream, otherwise it can only be read once");
    }

    @Override
    protected DefaultInputStream create(
        String id, String description, ImmutableProperties properties, Supplier<InputStream> data) {
      return new DefaultInputStream(getItem(), id, description, properties, data);
    }
  }

  public static class BuilderFactory
      extends AbstractContentBuilderFactory<InputStream, DefaultInputStream> {

    public BuilderFactory() {
      super(InputStream.class, DefaultInputStream.class);
    }

    @Override
    public Content.Builder<DefaultInputStream, InputStream> create(Item item) {
      return new DefaultInputStream.Builder(item);
    }
  }
}
