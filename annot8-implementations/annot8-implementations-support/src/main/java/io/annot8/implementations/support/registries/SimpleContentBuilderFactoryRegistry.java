/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.support.registries;

import io.annot8.api.data.Content;
import io.annot8.implementations.support.factories.ContentBuilderFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SimpleContentBuilderFactoryRegistry implements ContentBuilderFactoryRegistry {

  private final Map<Class<? extends Content<?>>, ContentBuilderFactory<?, ?>> builders =
      new HashMap<>();

  public <D, C extends Content<D>, I extends C> void register(
      Class<C> contentClass, ContentBuilderFactory<D, I> contentBuilderFactory) {
    builders.put(contentClass, contentBuilderFactory);
  }

  @Override
  public <D, C extends Content<D>> Optional<ContentBuilderFactory<D, C>> get(
      Class<C> contentClass) {
    // This cast to C is correct, due to the way that the content is registered, we know that it
    // tallies.
    // TODO: Slightly unsure about cast to D, I think it's correct in our case
    ContentBuilderFactory<D, C> contentBuilderFactory =
        (ContentBuilderFactory<D, C>) builders.get(contentClass);
    return Optional.ofNullable(contentBuilderFactory);
  }
}
