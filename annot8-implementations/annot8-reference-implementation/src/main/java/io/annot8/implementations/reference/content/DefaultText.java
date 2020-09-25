/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.reference.content;

import io.annot8.api.data.Content;
import io.annot8.api.data.Item;
import io.annot8.api.properties.ImmutableProperties;
import io.annot8.common.data.content.Text;
import io.annot8.implementations.reference.stores.DefaultAnnotationStore;
import io.annot8.implementations.support.content.AbstractContent;
import io.annot8.implementations.support.content.AbstractContentBuilder;
import io.annot8.implementations.support.content.AbstractContentBuilderFactory;
import java.util.function.Supplier;

public class DefaultText extends AbstractContent<String> implements Text {

  private DefaultText(
      Item item,
      String id,
      String description,
      ImmutableProperties properties,
      Supplier<String> data) {
    super(
        item,
        String.class,
        Text.class,
        DefaultAnnotationStore::new,
        id,
        description,
        properties,
        data);
  }

  public static class Builder extends AbstractContentBuilder<String, DefaultText> {

    public Builder(Item item) {
      super(item);
    }

    @Override
    public DefaultText create(
        String id, String description, ImmutableProperties properties, Supplier<String> data) {
      return new DefaultText(getItem(), id, description, properties, data);
    }
  }

  public static class BuilderFactory extends AbstractContentBuilderFactory<String, DefaultText> {

    public BuilderFactory() {
      super(String.class, DefaultText.class);
    }

    @Override
    public Content.Builder<DefaultText, String> create(Item item) {
      return new DefaultText.Builder(item);
    }
  }
}
