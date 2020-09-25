/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.testing.testimpl.content;

import io.annot8.api.data.Content;
import io.annot8.api.data.Item;
import io.annot8.api.properties.ImmutableProperties;
import io.annot8.common.data.content.Table;
import io.annot8.common.data.content.TableContent;
import io.annot8.implementations.support.content.AbstractContentBuilder;
import io.annot8.implementations.support.content.AbstractContentBuilderFactory;
import io.annot8.implementations.support.stores.AnnotationStoreFactory;
import io.annot8.testing.testimpl.AbstractTestContent;
import java.util.function.Supplier;

public class TestTableContent extends AbstractTestContent<Table> implements TableContent {

  public TestTableContent() {
    this(null);
  }

  public TestTableContent(Item item) {
    super(item, Table.class);
  }

  public TestTableContent(Item item, String description) {
    super(item, Table.class, description);
  }

  public TestTableContent(
      Item item,
      Class<Table> dataClass,
      String id,
      String description,
      ImmutableProperties properties) {
    super(item, Table.class, id, description, properties);
  }

  public TestTableContent(
      Item item,
      String id,
      String description,
      ImmutableProperties properties,
      Supplier<Table> data) {
    super(item, Table.class, id, description, properties, data);
  }

  public TestTableContent(
      Item item,
      AnnotationStoreFactory annotationStoreFactory,
      String id,
      String description,
      ImmutableProperties properties,
      Supplier<Table> data) {
    super(item, Table.class, annotationStoreFactory, id, description, properties, data);
  }

  @Override
  public Class<? extends Content<Table>> getContentClass() {
    return TableContent.class;
  }

  public static class Builder extends AbstractContentBuilder<Table, TableContent> {

    public Builder(Item item) {
      super(item);
    }

    @Override
    protected TableContent create(
        String id, String description, ImmutableProperties properties, Supplier<Table> data) {
      return new TestTableContent(getItem(), id, description, properties, data);
    }
  }

  public static class BuilderFactory extends AbstractContentBuilderFactory<Table, TableContent> {

    public BuilderFactory() {
      super(Table.class, TableContent.class);
    }

    @Override
    public Content.Builder<TableContent, Table> create(Item item) {
      return new Builder(item);
    }
  }
}
