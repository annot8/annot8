/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.testing.testimpl;

import io.annot8.api.bounds.Bounds;
import io.annot8.api.data.Content;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

/**
 * Test Bounds
 *
 * <p>The id is use for uniqueness (is boundsA == boundsB), but it has no meaning itself within
 * Annot8
 */
public class TestBounds implements Bounds {

  private final String id;

  // TODO: Add mocked responses for this perhaps (eg map of requriedClass to value for getData)

  public TestBounds() {
    this(null);
  }

  @JsonbCreator
  public TestBounds(@JsonbProperty("id") String id) {
    this.id = id == null ? UUID.randomUUID().toString() : id;
  }

  @Override
  public <D, C extends Content<D>, R> Optional<R> getData(C content, Class<R> requiredClass) {
    // Can't return anything
    return Optional.empty();
  }

  @Override
  public <D, C extends Content<D>> boolean isValid(C content) {
    // true for everything
    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  public String getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TestBounds that = (TestBounds) o;
    return Objects.equals(id, that.id);
  }
}
