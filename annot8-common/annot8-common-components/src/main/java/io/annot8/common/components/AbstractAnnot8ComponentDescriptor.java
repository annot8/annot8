/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.components;

import io.annot8.api.components.Annot8Component;
import io.annot8.api.components.Annot8ComponentDescriptor;
import io.annot8.api.settings.Settings;

public abstract class AbstractAnnot8ComponentDescriptor<
        T extends Annot8Component, S extends Settings>
    implements Annot8ComponentDescriptor<T, S> {

  private String name;
  private S settings;

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setSettings(S settings) {
    this.settings = settings;
  }

  @Override
  public S getSettings() {
    return settings;
  }
}
