/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.serialization;

import io.annot8.api.capabilities.Capabilities;
import io.annot8.api.capabilities.Capability;
import io.annot8.api.components.Annot8ComponentDescriptor;
import io.annot8.api.components.Processor;
import io.annot8.api.components.responses.ProcessorResponse;
import io.annot8.api.context.Context;
import io.annot8.api.data.Item;
import io.annot8.api.settings.Settings;
import java.util.stream.Stream;

public class TestNested implements Processor {

  @Override
  public ProcessorResponse process(Item item) {
    return ProcessorResponse.ok();
  }

  public static class Configuration implements Settings {
    @Override
    public boolean validate() {
      return true;
    }
  }

  public static class Descriptor implements Annot8ComponentDescriptor<TestNested, Configuration> {

    private String name;
    private Configuration settings;

    public Descriptor() {
      name = "";
      settings = new Configuration();
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
    public void setSettings(Configuration settings) {
      this.settings = settings;
    }

    @Override
    public Configuration getSettings() {
      return settings;
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
    public TestNested create(Context context) {
      return new TestNested();
    }
  }
}
