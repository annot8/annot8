/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.testing.testimpl;

import io.annot8.api.settings.Settings;

public class TestSettings implements Settings {

  private final boolean valid;

  public TestSettings() {
    this(true);
  }

  public TestSettings(boolean valid) {
    this.valid = valid;
  }

  @Override
  public boolean validate() {
    return valid;
  }
}
