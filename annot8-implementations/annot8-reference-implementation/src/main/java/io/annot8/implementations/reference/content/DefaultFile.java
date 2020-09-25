/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.reference.content;

import io.annot8.api.data.Content;
import io.annot8.api.data.Item;
import io.annot8.api.properties.ImmutableProperties;
import io.annot8.common.data.content.FileContent;
import io.annot8.implementations.reference.stores.DefaultAnnotationStore;
import io.annot8.implementations.support.content.AbstractContent;
import io.annot8.implementations.support.content.AbstractContentBuilder;
import io.annot8.implementations.support.content.AbstractContentBuilderFactory;
import java.io.File;
import java.util.function.Supplier;

public class DefaultFile extends AbstractContent<File> implements FileContent {

  private DefaultFile(
      Item item,
      String id,
      String description,
      ImmutableProperties properties,
      Supplier<File> data) {
    super(
        item,
        File.class,
        FileContent.class,
        DefaultAnnotationStore::new,
        id,
        description,
        properties,
        data);
  }

  public static class Builder extends AbstractContentBuilder<File, DefaultFile> {

    public Builder(Item item) {
      super(item);
    }

    @Override
    protected DefaultFile create(
        String id, String description, ImmutableProperties properties, Supplier<File> data) {
      return new DefaultFile(getItem(), id, description, properties, data);
    }
  }

  public static class BuilderFactory extends AbstractContentBuilderFactory<File, DefaultFile> {

    public BuilderFactory() {
      super(File.class, DefaultFile.class);
    }

    @Override
    public Content.Builder<DefaultFile, File> create(Item item) {
      return new DefaultFile.Builder(item);
    }
  }
}
