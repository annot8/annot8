/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.support.references;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import io.annot8.api.annotations.Group;
import io.annot8.api.references.GroupReference;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class AbstractGroupReferenceTest {

  @Test
  public void testEquals() {
    GroupReference ref = new TestGroupReference("groupId");
    GroupReference same = new TestGroupReference("groupId");
    GroupReference diff = new TestGroupReference("diff");

    assertEquals(ref, ref);
    assertEquals(ref, same);
    assertEquals(ref.hashCode(), same.hashCode());
    assertNotEquals(ref, diff);
    assertNotEquals(ref, new Object());
    assertNotEquals(null, ref);
  }

  private class TestGroupReference extends AbstractGroupReference {

    private final String groupId;

    public TestGroupReference(String groupId) {
      this.groupId = groupId;
    }

    @Override
    public String getGroupId() {
      return groupId;
    }

    @Override
    public Optional<Group> toGroup() {
      throw new UnsupportedOperationException("Unsupported in this test implementation");
    }
  }
}
