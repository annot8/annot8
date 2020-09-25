/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.testing.testimpl.content;

import io.annot8.api.data.Content;
import io.annot8.api.data.Item;
import io.annot8.api.properties.ImmutableProperties;
import io.annot8.api.stores.AnnotationStore;
import io.annot8.common.data.content.Text;
import io.annot8.implementations.support.content.AbstractContentBuilder;
import io.annot8.implementations.support.content.AbstractContentBuilderFactory;
import io.annot8.implementations.support.stores.AnnotationStoreFactory;
import io.annot8.testing.testimpl.AbstractTestContent;
import io.annot8.testing.testimpl.TestAnnotationStoreFactory;
import java.util.function.Supplier;

public class TestStringContent extends AbstractTestContent<String> implements Text {

  public TestStringContent() {
    this(null);
  }

  public TestStringContent(Item item) {
    super(item, String.class);
    setData("Test data");
  }

  public TestStringContent(
      Item item,
      String id,
      String description,
      ImmutableProperties properties,
      Supplier<String> data) {
    super(item, String.class, id, description, properties, data);
  }

  public TestStringContent(
      Item item,
      AnnotationStore annotations,
      String id,
      String description,
      ImmutableProperties properties,
      Supplier<String> data) {
    super(item, String.class, c -> annotations, id, description, properties, data);
  }

  public TestStringContent(
      Item item,
      AnnotationStoreFactory annotationStore,
      String id,
      String description,
      ImmutableProperties properties,
      Supplier<String> data) {
    super(item, String.class, annotationStore, id, description, properties, data);
  }

  @Override
  public Class<? extends Content<String>> getContentClass() {
    return Text.class;
  }

  public static class Builder extends AbstractContentBuilder<String, TestStringContent> {

    private final AnnotationStoreFactory annotationStoreFactory;

    public Builder(Item item, AnnotationStoreFactory annotationStoreFactory) {
      super(item);
      this.annotationStoreFactory = annotationStoreFactory;
    }

    @Override
    protected TestStringContent create(
        String id, String description, ImmutableProperties properties, Supplier<String> data) {
      return new TestStringContent(
          getItem(), annotationStoreFactory, id, description, properties, data);
    }
  }

  public static class BuilderFactory
      extends AbstractContentBuilderFactory<String, TestStringContent> {

    private final AnnotationStoreFactory annotationStoreFactory;

    public BuilderFactory() {
      this(TestAnnotationStoreFactory.getInstance());
    }

    public BuilderFactory(AnnotationStoreFactory annotationStoreFactory) {
      super(String.class, TestStringContent.class);
      this.annotationStoreFactory = annotationStoreFactory;
    }

    @Override
    public Builder create(Item item) {
      return new Builder(item, annotationStoreFactory);
    }
  }
}
