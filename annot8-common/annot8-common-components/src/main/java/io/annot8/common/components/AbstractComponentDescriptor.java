/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.components;

import io.annot8.api.components.Annot8ComponentDescriptor;
import io.annot8.api.context.Context;
import io.annot8.api.settings.Settings;
import io.annot8.common.components.logging.Logging;
import io.annot8.common.components.metering.Metering;

public abstract class AbstractComponentDescriptor<T extends AbstractComponent, S extends Settings>
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

  public final T create(Context context) {

    T t = createComponent(context, getSettings());

    // Set up logging and metrics from context

    context.getResource(Logging.class).map(l -> l.getLogger(t.getClass())).ifPresent(t::setLogger);
    context
        .getResource(Metering.class)
        .map(m -> m.getMetrics(t.getClass()))
        .ifPresent(t::setMetrics);

    return t;
  }

  protected abstract T createComponent(Context context, S settings);
}
