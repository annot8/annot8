/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.serialization;

import io.annot8.api.bounds.Bounds;
import io.annot8.api.capabilities.AnnotationCapability;
import io.annot8.api.capabilities.Capabilities;
import io.annot8.api.capabilities.Capability;
import io.annot8.api.components.ProcessorDescriptor;
import io.annot8.api.context.Context;
import java.util.stream.Stream;

public class TestDescriptor implements ProcessorDescriptor<TestProcessor, TestSettings> {

  private String name;
  private TestSettings settings;

  public TestDescriptor() {
    // Do nothing
  }

  public TestDescriptor(String name, String host, int port) {
    this.name = name;
    this.settings = new TestSettings(host, port);
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setSettings(TestSettings settings) {
    this.settings = settings;
  }

  @Override
  public TestSettings getSettings() {
    return settings;
  }

  @Override
  public Capabilities capabilities() {
    return new Capabilities() {
      @Override
      public Stream<Capability> creates() {
        return Stream.of(
            new AnnotationCapability() {
              @Override
              public String getType() {
                return "any";
              }

              @Override
              public Class<? extends Bounds> getBounds() {
                return Bounds.class;
              }
            });
      }

      @Override
      public Stream<Capability> processes() {
        return Stream.empty();
      }

      @Override
      public Stream<Capability> deletes() {
        return Stream.empty();
      }
    };
  }

  @Override
  public TestProcessor create(Context context) {
    return new TestProcessor(getSettings());
  }
}
