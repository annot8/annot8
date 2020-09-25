/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.reference.references;

import io.annot8.api.annotations.Group;
import io.annot8.api.data.Item;
import io.annot8.implementations.support.references.AbstractGroupReference;
import java.util.Optional;

/**
 * A reference which will always retrieve the latest group from the appropriate group store.
 *
 * <p>Does not hold a reference to the group.
 */
public class DefaultGroupReference extends AbstractGroupReference {

  private final Item item;

  private final String groupId;

  /**
   * New reference either from another reference or manually created.
   *
   * @param item the item
   * @param groupId the group id
   */
  public DefaultGroupReference(Item item, String groupId) {
    this.item = item;
    this.groupId = groupId;
  }

  /**
   * Create a reference from a group instance.
   *
   * @param item the item owning the group
   * @param group the group id
   * @return reference to this group
   */
  public static DefaultGroupReference to(Item item, Group group) {
    return new DefaultGroupReference(item, group.getId());
  }

  @Override
  public String getGroupId() {
    return groupId;
  }

  @Override
  public Optional<Group> toGroup() {
    return item.getGroups().getById(groupId);
  }
}
