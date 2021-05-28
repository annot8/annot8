/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.testing.testimpl.content;

import io.annot8.api.data.Content;
import io.annot8.api.data.Item;
import io.annot8.api.properties.ImmutableProperties;
import io.annot8.api.stores.AnnotationStore;
import io.annot8.common.data.content.Audio;
import io.annot8.implementations.support.content.AbstractContentBuilder;
import io.annot8.implementations.support.content.AbstractContentBuilderFactory;
import io.annot8.implementations.support.stores.AnnotationStoreFactory;
import io.annot8.testing.testimpl.AbstractTestContent;
import io.annot8.testing.testimpl.TestAnnotationStoreFactory;
import java.io.ByteArrayInputStream;
import java.util.function.Supplier;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

public class TestAudioContent extends AbstractTestContent<AudioInputStream> implements Audio {

  public TestAudioContent() {
    this(null);
  }

  public TestAudioContent(Item item) {
    super(item, AudioInputStream.class);
    // Ths is not really useful in general, but its something non-null
    setData(
        () ->
            new AudioInputStream(
                new ByteArrayInputStream(new byte[0]),
                new AudioFormat(0.0f, 0, 0, true, true),
                0L));
  }

  public TestAudioContent(
      Item item,
      String id,
      String description,
      ImmutableProperties properties,
      Supplier<AudioInputStream> data) {
    super(item, AudioInputStream.class, id, description, properties, data);
  }

  public TestAudioContent(
      Item item,
      AnnotationStore annotations,
      String id,
      String description,
      ImmutableProperties properties,
      Supplier<AudioInputStream> data) {
    super(item, AudioInputStream.class, c -> annotations, id, description, properties, data);
  }

  public TestAudioContent(
      Item item,
      AnnotationStoreFactory annotationStore,
      String id,
      String description,
      ImmutableProperties properties,
      Supplier<AudioInputStream> data) {
    super(item, AudioInputStream.class, annotationStore, id, description, properties, data);
  }

  @Override
  public Class<? extends Content<AudioInputStream>> getContentClass() {
    return Audio.class;
  }

  public static class Builder extends AbstractContentBuilder<AudioInputStream, TestAudioContent> {

    private final AnnotationStoreFactory annotationStoreFactory;

    public Builder(Item item, AnnotationStoreFactory annotationStoreFactory) {
      super(item);
      this.annotationStoreFactory = annotationStoreFactory;
    }

    @Override
    protected TestAudioContent create(
        String id,
        String description,
        ImmutableProperties properties,
        Supplier<AudioInputStream> data) {
      return new TestAudioContent(
          getItem(), annotationStoreFactory, id, description, properties, data);
    }
  }

  public static class BuilderFactory
      extends AbstractContentBuilderFactory<AudioInputStream, TestAudioContent> {

    private final AnnotationStoreFactory annotationStoreFactory;

    public BuilderFactory() {
      this(TestAnnotationStoreFactory.getInstance());
    }

    public BuilderFactory(AnnotationStoreFactory annotationStoreFactory) {
      super(AudioInputStream.class, TestAudioContent.class);
      this.annotationStoreFactory = annotationStoreFactory;
    }

    @Override
    public TestAudioContent.Builder create(Item item) {
      return new TestAudioContent.Builder(item, annotationStoreFactory);
    }
  }
}
