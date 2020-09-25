/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.testing.testimpl;

import io.annot8.api.data.Content;
import io.annot8.api.data.Item;
import io.annot8.api.properties.ImmutableProperties;
import io.annot8.api.stores.AnnotationStore;
import io.annot8.implementations.support.stores.AnnotationStoreFactory;
import java.util.UUID;
import java.util.function.Supplier;

public abstract class AbstractTestContent<D> implements Content<D> {

  private Item item;
  private String id;
  private final Class<D> dataClass;
  private String description;
  private ImmutableProperties properties;
  private AnnotationStore annotations;

  private Supplier<D> data;

  public AbstractTestContent(Item item, Class<D> dataClass) {
    this(item, dataClass, TestConstants.CONTENT_DESCRIPTION);
  }

  public AbstractTestContent(Item item, Class<D> dataClass, String description) {
    this(item, dataClass, UUID.randomUUID().toString(), description, new TestProperties());
  }

  public AbstractTestContent(
      Item item,
      Class<D> dataClass,
      String id,
      String description,
      ImmutableProperties properties) {
    this(item, dataClass, id, description, properties, (D) null);
  }

  public AbstractTestContent(
      Item item,
      Class<D> dataClass,
      String id,
      String description,
      ImmutableProperties properties,
      Supplier<D> data) {
    this(item, dataClass, TestAnnotationStore::new, id, description, properties, data);
  }

  public AbstractTestContent(
      Item item,
      Class<D> dataClass,
      AnnotationStoreFactory annotationStoreFactory,
      String id,
      String description,
      ImmutableProperties properties,
      Supplier<D> data) {
    this.item = item;
    this.id = id;
    this.dataClass = dataClass;
    this.description = description;
    this.properties = properties;
    this.data = data;

    this.annotations = annotationStoreFactory.create(this);
  }

  public AbstractTestContent(
      Item item,
      Class<D> dataClass,
      String id,
      String description,
      ImmutableProperties properties,
      D data) {
    this(item, dataClass, id, description, properties, () -> data);
  }

  @Override
  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String getId() {
    return id;
  }

  public Class<D> getDataClass() {
    return dataClass;
  }

  @Override
  public D getData() {
    return data.get();
  }

  public void setData(D data) {
    if (data == null) {
      this.setData((Supplier<D>) null);
    } else {
      this.setData(() -> data);
    }
  }

  public void setData(Supplier<D> data) {
    this.data = data;
  }

  @Override
  public AnnotationStore getAnnotations() {
    return annotations;
  }

  public void setAnnotations(AnnotationStore annotations) {
    this.annotations = annotations;
  }

  @Override
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public ImmutableProperties getProperties() {
    return properties;
  }

  public void setProperties(ImmutableProperties properties) {
    this.properties = properties;
  }
}
