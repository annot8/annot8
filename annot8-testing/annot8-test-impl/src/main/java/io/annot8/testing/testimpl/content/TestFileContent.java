/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.testing.testimpl.content;

import io.annot8.api.data.Content;
import io.annot8.api.data.Item;
import io.annot8.api.properties.ImmutableProperties;
import io.annot8.api.stores.AnnotationStore;
import io.annot8.common.data.content.FileContent;
import io.annot8.implementations.support.content.AbstractContentBuilder;
import io.annot8.implementations.support.content.AbstractContentBuilderFactory;
import io.annot8.implementations.support.stores.AnnotationStoreFactory;
import io.annot8.testing.testimpl.AbstractTestContent;
import io.annot8.testing.testimpl.TestAnnotationStoreFactory;
import java.io.File;
import java.util.function.Supplier;

public class TestFileContent extends AbstractTestContent<File> implements FileContent {

  public TestFileContent() {
    this(null);
  }

  public TestFileContent(Item item) {
    super(item, File.class);
    setData(new File("."));
  }

  public TestFileContent(
      Item item,
      String id,
      String description,
      ImmutableProperties properties,
      Supplier<File> data) {
    super(item, File.class, id, description, properties, data);
  }

  public TestFileContent(
      Item item,
      AnnotationStore annotations,
      String id,
      String description,
      ImmutableProperties properties,
      Supplier<File> data) {
    super(item, File.class, c -> annotations, id, description, properties, data);
  }

  public TestFileContent(
      Item item,
      AnnotationStoreFactory annotationStore,
      String id,
      String description,
      ImmutableProperties properties,
      Supplier<File> data) {
    super(item, File.class, annotationStore, id, description, properties, data);
  }

  @Override
  public Class<? extends Content<File>> getContentClass() {
    return FileContent.class;
  }

  public static class Builder extends AbstractContentBuilder<File, TestFileContent> {

    private final AnnotationStoreFactory annotationStoreFactory;

    public Builder(Item item, AnnotationStoreFactory annotationStoreFactory) {
      super(item);
      this.annotationStoreFactory = annotationStoreFactory;
    }

    @Override
    protected TestFileContent create(
        String id, String description, ImmutableProperties properties, Supplier<File> data) {
      return new TestFileContent(
          getItem(), annotationStoreFactory, id, description, properties, data);
    }
  }

  public static class BuilderFactory extends AbstractContentBuilderFactory<File, TestFileContent> {

    private final AnnotationStoreFactory annotationStoreFactory;

    public BuilderFactory() {
      this(TestAnnotationStoreFactory.getInstance());
    }

    public BuilderFactory(AnnotationStoreFactory annotationStoreFactory) {
      super(File.class, TestFileContent.class);
      this.annotationStoreFactory = annotationStoreFactory;
    }

    @Override
    public TestFileContent.Builder create(Item item) {
      return new TestFileContent.Builder(item, annotationStoreFactory);
    }
  }
}
