/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.components.capabilities;

import io.annot8.api.capabilities.ContentCapability;
import io.annot8.api.data.Content;
import java.util.Objects;
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class SimpleContentCapability implements ContentCapability {

  private final Class<? extends Content> type;

  @JsonbCreator
  public SimpleContentCapability(@JsonbProperty("type") Class<? extends Content> type) {
    this.type = type;
  }

  @Override
  public Class<? extends Content> getType() {
    return type;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof ContentCapability)) return false;
    ContentCapability other = (ContentCapability) o;

    return Objects.equals(type, other.getType());
  }

  @Override
  public int hashCode() {
    return Objects.hash(type);
  }
}
