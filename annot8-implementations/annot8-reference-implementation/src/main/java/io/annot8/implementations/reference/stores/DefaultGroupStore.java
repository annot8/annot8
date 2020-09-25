/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.reference.stores;

import io.annot8.api.annotations.Group;
import io.annot8.api.data.Item;
import io.annot8.api.stores.GroupStore;
import io.annot8.implementations.reference.annotations.DefaultGroup;
import io.annot8.implementations.support.delegates.DelegateGroupBuilder;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** In memory implementation, backed by a HashMap, of GroupStore */
public class DefaultGroupStore implements GroupStore {

  private final Item item;
  private final Map<String, Group> groups = new ConcurrentHashMap<>();
  /**
   * Construct a new instance of this class for the provided item
   *
   * @param item the owning item
   */
  public DefaultGroupStore(Item item) {
    this.item = item;
  }

  @Override
  public Item getItem() {
    return item;
  }

  @Override
  public Group.Builder getBuilder() {
    return new DelegateGroupBuilder(new DefaultGroup.Builder(item)) {
      @Override
      public Group save() {
        return DefaultGroupStore.this.save(super.save());
      }
    };
  }

  private Group save(Group group) {
    groups.put(group.getId(), group);
    return group;
  }

  @Override
  public void delete(Group group) {
    groups.remove(group.getId(), group);
  }

  @Override
  public Stream<Group> getAll() {
    return groups.values().stream();
  }

  @Override
  public Optional<Group> getById(String s) {
    return Optional.ofNullable(groups.get(s));
  }

  @Override
  public String toString() {
    return this.getClass().getName() + " [groups=" + groups.size() + "]";
  }

  @Override
  public int hashCode() {
    return Objects.hash(groups);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof GroupStore)) {
      return false;
    }

    GroupStore gs = (GroupStore) obj;

    Set<Group> allGroups = gs.getAll().collect(Collectors.toSet());

    return Objects.equals(new HashSet<>(groups.values()), allGroups);
  }
}
