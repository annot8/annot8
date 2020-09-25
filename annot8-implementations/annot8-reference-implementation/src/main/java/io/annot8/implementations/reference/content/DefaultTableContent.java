/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.reference.content;

import io.annot8.api.data.Content;
import io.annot8.api.data.Item;
import io.annot8.api.properties.ImmutableProperties;
import io.annot8.common.data.content.Table;
import io.annot8.common.data.content.TableContent;
import io.annot8.implementations.reference.stores.DefaultAnnotationStore;
import io.annot8.implementations.support.content.AbstractContent;
import io.annot8.implementations.support.content.AbstractContentBuilder;
import io.annot8.implementations.support.content.AbstractContentBuilderFactory;
import java.util.function.Supplier;

public class DefaultTableContent extends AbstractContent<Table> implements TableContent {

  private DefaultTableContent(
      Item item,
      String id,
      String description,
      ImmutableProperties properties,
      Supplier<Table> dataSupplier) {
    super(
        item,
        Table.class,
        TableContent.class,
        DefaultAnnotationStore::new,
        id,
        description,
        properties,
        dataSupplier);
  }

  public static class Builder extends AbstractContentBuilder<Table, TableContent> {

    public Builder(Item item) {
      super(item);
    }

    @Override
    protected TableContent create(
        String id, String description, ImmutableProperties properties, Supplier<Table> data) {
      return new DefaultTableContent(getItem(), id, description, properties, data);
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
