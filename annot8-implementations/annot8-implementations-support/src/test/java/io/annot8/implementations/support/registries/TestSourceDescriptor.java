/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.support.registries;

import io.annot8.api.capabilities.Capabilities;
import io.annot8.api.capabilities.Capability;
import io.annot8.api.components.Source;
import io.annot8.api.components.SourceDescriptor;
import io.annot8.api.components.responses.SourceResponse;
import io.annot8.api.context.Context;
import io.annot8.api.data.ItemFactory;
import io.annot8.api.settings.NoSettings;
import java.util.stream.Stream;

public class TestSourceDescriptor
    implements SourceDescriptor<TestSourceDescriptor.TestSource, NoSettings> {

  private String name;

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setSettings(NoSettings settings) {
    // Do nothing
  }

  @Override
  public NoSettings getSettings() {
    return NoSettings.getInstance();
  }

  @Override
  public Capabilities capabilities() {
    return new Capabilities() {
      @Override
      public Stream<Capability> creates() {
        return Stream.empty();
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
  public TestSource create(Context context) {
    return new TestSource();
  }

  public static class TestSource implements Source {
    @Override
    public SourceResponse read(ItemFactory itemFactory) {
      return SourceResponse.ok();
    }
  }
}
