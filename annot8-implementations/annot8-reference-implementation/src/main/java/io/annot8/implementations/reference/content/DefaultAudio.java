/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.reference.content;

import io.annot8.api.data.Content;
import io.annot8.api.data.Item;
import io.annot8.api.exceptions.Annot8RuntimeException;
import io.annot8.api.properties.ImmutableProperties;
import io.annot8.common.data.content.Audio;
import io.annot8.implementations.reference.stores.DefaultAnnotationStore;
import io.annot8.implementations.support.content.AbstractContent;
import io.annot8.implementations.support.content.AbstractContentBuilder;
import io.annot8.implementations.support.content.AbstractContentBuilderFactory;

import javax.sound.sampled.AudioInputStream;
import java.util.function.Supplier;

public class DefaultAudio extends AbstractContent<AudioInputStream> implements Audio {

  private DefaultAudio(Item item, String id, String description, ImmutableProperties properties, Supplier<AudioInputStream> data) {
    super(item, AudioInputStream.class, Audio.class, DefaultAnnotationStore::new, id, description, properties, data);
  }

  public static class Builder extends AbstractContentBuilder<AudioInputStream, DefaultAudio> {

    public Builder(Item item) {
      super(item);
    }

    @Override
    public Content.Builder<DefaultAudio, AudioInputStream> withData(AudioInputStream data) {
      throw new Annot8RuntimeException("Must use a Supplier to provider AudioInputStream, otherwise it can only be read once");
    }

    @Override
    protected DefaultAudio create(String id, String description, ImmutableProperties properties, Supplier<AudioInputStream> data) {
      return new DefaultAudio(getItem(), id, description, properties, data);
    }
  }

  public static class BuilderFactory extends AbstractContentBuilderFactory<AudioInputStream, DefaultAudio> {
    public BuilderFactory() {
      super(AudioInputStream.class, DefaultAudio.class);
    }

    @Override
    public Content.Builder<DefaultAudio, AudioInputStream> create(Item item) {
      return new DefaultAudio.Builder(item);
    }
  }
}
