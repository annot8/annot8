/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.implementations.reference.annotations;

import static org.junit.jupiter.api.Assertions.*;

import io.annot8.api.annotations.Annotation;
import io.annot8.api.annotations.Group;
import io.annot8.api.exceptions.IncompleteException;
import io.annot8.common.data.properties.EmptyImmutableProperties;
import io.annot8.implementations.reference.annotations.DefaultGroup.Builder;
import io.annot8.testing.tck.impl.WithIdBuilderTestUtils;
import io.annot8.testing.tck.impl.WithPropertiesBuilderTestUtils;
import io.annot8.testing.testimpl.TestConstants;
import io.annot8.testing.testimpl.TestItem;
import io.annot8.testing.testimpl.content.TestStringContent;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultGroupTest {

  private TestItem item;
  private Annotation a1;
  private Annotation a2;
  private Annotation a3;

  @BeforeEach
  public void beforeEach() throws IncompleteException {
    item = new TestItem();
    TestStringContent content = item.save(new TestStringContent(new TestItem()));
    a1 = content.getAnnotations().create().save();
    a2 = content.getAnnotations().create().save();
    a3 = content.getAnnotations().create().save();
  }

  @Test
  public void testIncompleteNothingSet() {
    assertThrows(IncompleteException.class, new Builder(item)::save);
  }

  @Test
  public void testIncompleteWithoutType() {
    assertThrows(IncompleteException.class, new Builder(item).withAnnotation("source", a1)::save);
  }

  @Test
  public void testNewHasId() throws IncompleteException {
    Group group = new Builder(item).withType(TestConstants.GROUP_TYPE).newId().save();

    assertNotNull(group.getId());
  }

  @Test
  public void testSimpleGroup() throws IncompleteException {
    Group g1 =
        new DefaultGroup.Builder(item)
            .withType(TestConstants.GROUP_TYPE)
            .withAnnotation("source", a1)
            .withAnnotation("target", a2)
            .save();
    assertNotNull(g1.getId());
    assertEquals(TestConstants.GROUP_TYPE, g1.getType());
    assertEquals(EmptyImmutableProperties.getInstance(), g1.getProperties());
    assertTrue(g1.containsAnnotation(a1));
    assertTrue(g1.containsAnnotation(a2));

    Map<String, Stream<Annotation>> annotations1 = g1.getAnnotations();
    assertEquals(2, annotations1.size());
    assertTrue(annotations1.containsKey("source"));
    List<Annotation> annotations1Source = annotations1.get("source").collect(Collectors.toList());
    assertEquals(1, annotations1Source.size());
    assertEquals(a1, annotations1Source.get(0));
    assertEquals("source", g1.getRole(a1).get());

    assertTrue(annotations1.containsKey("target"));
    List<Annotation> annotations1Target = annotations1.get("target").collect(Collectors.toList());
    assertEquals(1, annotations1Target.size());
    assertEquals(a2, annotations1Target.get(0));
  }

  @Test
  public void testFromExisting() throws IncompleteException {
    Group g1 =
        new DefaultGroup.Builder(item)
            .withType(TestConstants.GROUP_TYPE)
            .withAnnotation("source", a1)
            .withAnnotation("target", a2)
            .save();

    Group g2 = new DefaultGroup.Builder(item).from(g1).withAnnotation("target", a3).save();
    Map<String, Stream<Annotation>> annotations2 = g2.getAnnotations();

    assertEquals(2, annotations2.size());
    assertTrue(annotations2.containsKey("source"));
    List<Annotation> annotations2Source = annotations2.get("source").collect(Collectors.toList());
    assertEquals(1, annotations2Source.size());
    assertEquals(a1, annotations2Source.get(0));
    assertTrue(annotations2.containsKey("target"));
    List<Annotation> annotations2Target = annotations2.get("target").collect(Collectors.toList());
    assertEquals(2, annotations2Target.size());
    assertTrue(annotations2Target.contains(a2));
    assertTrue(annotations2Target.contains(a3));
  }

  @Test
  public void testFromExistingNoChange() throws IncompleteException {
    Group g1 =
        new DefaultGroup.Builder(item)
            .withType(TestConstants.GROUP_TYPE)
            .withAnnotation("source", a1)
            .withAnnotation("target", a2)
            .save();

    Group g3 = new DefaultGroup.Builder(item).from(g1).save();
    assertEquals(g1, g3);
  }

  @Test
  public void testFromExistingNewId() throws IncompleteException {
    Group g1 =
        new DefaultGroup.Builder(item)
            .withType(TestConstants.GROUP_TYPE)
            .withAnnotation("source", a1)
            .withAnnotation("target", a2)
            .save();

    Group g3 = new DefaultGroup.Builder(item).from(g1).newId().save();
    assertNotEquals(g1.getId(), g3.getId());
  }

  @Test
  public void testProperties() {
    WithPropertiesBuilderTestUtils utils = new WithPropertiesBuilderTestUtils();
    utils.testWithPropertiesBuilder(new Builder(item).withType(TestConstants.GROUP_TYPE));
  }

  @Test
  public void testWithId() {
    WithIdBuilderTestUtils utils = new WithIdBuilderTestUtils();
    utils.testWithIdBuilder(new Builder(item).withType(TestConstants.GROUP_TYPE));
  }
}
