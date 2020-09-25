/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.testing.testimpl.content;

import io.annot8.api.data.Content;
import io.annot8.api.data.Item;
import io.annot8.api.properties.ImmutableProperties;
import io.annot8.api.stores.AnnotationStore;
import io.annot8.common.data.content.InputStreamContent;
import io.annot8.implementations.support.content.AbstractContentBuilder;
import io.annot8.implementations.support.content.AbstractContentBuilderFactory;
import io.annot8.implementations.support.stores.AnnotationStoreFactory;
import io.annot8.testing.testimpl.AbstractTestContent;
import io.annot8.testing.testimpl.TestAnnotationStoreFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

public class TestInputStreamContent extends AbstractTestContent<InputStream>
    implements InputStreamContent {

  private static final byte[] DEFAULT_DATA = "Test Data".getBytes(StandardCharsets.UTF_8);

  public TestInputStreamContent() {
    this(null);
  }

  public TestInputStreamContent(Item item) {
    super(item, InputStream.class);
    // Ths is not really useful in general, but its something non-null
    setData(() -> new ByteArrayInputStream(DEFAULT_DATA));
  }

  public TestInputStreamContent(
      Item item,
      String id,
      String description,
      ImmutableProperties properties,
      Supplier<InputStream> data) {
    super(item, InputStream.class, id, description, properties, data);
  }

  public TestInputStreamContent(
      Item item,
      AnnotationStore annotations,
      String id,
      String description,
      ImmutableProperties properties,
      Supplier<InputStream> data) {
    super(item, InputStream.class, c -> annotations, id, description, properties, data);
  }

  public TestInputStreamContent(
      Item item,
      AnnotationStoreFactory annotationStore,
      String id,
      String description,
      ImmutableProperties properties,
      Supplier<InputStream> data) {
    super(item, InputStream.class, annotationStore, id, description, properties, data);
  }

  @Override
  public Class<? extends Content<InputStream>> getContentClass() {
    return InputStreamContent.class;
  }

  public static class Builder extends AbstractContentBuilder<InputStream, TestInputStreamContent> {

    private final AnnotationStoreFactory annotationStoreFactory;

    public Builder(Item item, AnnotationStoreFactory annotationStoreFactory) {
      super(item);
      this.annotationStoreFactory = annotationStoreFactory;
    }

    @Override
    protected TestInputStreamContent create(
        String id, String description, ImmutableProperties properties, Supplier<InputStream> data) {
      return new TestInputStreamContent(
          getItem(), annotationStoreFactory, id, description, properties, data);
    }
  }

  public static class BuilderFactory
      extends AbstractContentBuilderFactory<InputStream, TestInputStreamContent> {

    private final AnnotationStoreFactory annotationStoreFactory;

    public BuilderFactory() {
      this(TestAnnotationStoreFactory.getInstance());
    }

    public BuilderFactory(AnnotationStoreFactory annotationStoreFactory) {
      super(InputStream.class, TestInputStreamContent.class);
      this.annotationStoreFactory = annotationStoreFactory;
    }

    @Override
    public TestInputStreamContent.Builder create(Item item) {
      return new TestInputStreamContent.Builder(item, annotationStoreFactory);
    }
  }
}
