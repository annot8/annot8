/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.data.content;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class RowTest {

  @Test
  public void testGetColumnName() {
    Row row = getTestRow();

    Optional<String> column1 = row.getColumnName(0);
    Optional<String> column2 = row.getColumnName(1);
    Optional<String> column3 = row.getColumnName(2);
    Optional<String> column4 = row.getColumnName(3);
    assertTrue(column1.isPresent());
    assertTrue(column2.isPresent());
    assertTrue(column3.isPresent());
    assertTrue(column4.isPresent());
    assertEquals("test", column1.get());
    assertEquals("test2", column2.get());
    assertEquals("test3", column3.get());
    assertEquals("test4", column4.get());

    Row rowNoHeaders = Mockito.mock(Row.class);
    when(rowNoHeaders.getColumnNames()).thenReturn(null);
    when(rowNoHeaders.getColumnName(anyInt())).thenCallRealMethod();

    assertFalse(rowNoHeaders.getColumnName(0).isPresent());
  }

  @Test
  public void testGetString() {
    Row row = getTestRow();

    Optional<String> value1 = row.getString(0);
    Optional<String> value2 = row.getString(1);
    Optional<String> value3 = row.getString(2);
    Optional<String> value4 = row.getString(3);

    assertTrue(value1.isPresent());
    assertTrue(value2.isPresent());
    assertTrue(value3.isPresent());
    assertTrue(value4.isPresent());

    assertEquals("test", value1.get());
    assertEquals("1", value2.get());
    assertEquals("2", value3.get());
    assertEquals(Long.toString(Long.MAX_VALUE), value4.get());
  }

  @Test
  public void testGetInt() {
    Row row = getTestRow();

    Optional<Integer> value1 = row.getInt(0);
    Optional<Integer> value2 = row.getInt(1);
    Optional<Integer> value3 = row.getInt(2);
    Optional<Integer> value4 = row.getInt(3);
    assertFalse(value1.isPresent());
    assertTrue(value2.isPresent());
    assertTrue(value3.isPresent());
    assertTrue(value4.isPresent());
    assertEquals(1, (int) value2.get());
    assertEquals(2, (int) value3.get());
    assertEquals(-1, (long) value4.get());
  }

  @Test
  public void testGetLong() {
    Row row = getTestRow();

    Optional<Long> value1 = row.getLong(0);
    Optional<Long> value2 = row.getLong(1);
    Optional<Long> value3 = row.getLong(2);
    Optional<Long> value4 = row.getLong(3);
    assertFalse(value1.isPresent());
    assertTrue(value2.isPresent());
    assertTrue(value3.isPresent());
    assertTrue(value4.isPresent());

    assertEquals(1, (long) value2.get());
    assertEquals(2, (long) value3.get());
    assertEquals(Long.MAX_VALUE, (long) value4.get());
  }

  @Test
  public void testGetDouble() {
    Row row = getTestRow();

    Optional<Double> value1 = row.getDouble(0);
    Optional<Double> value2 = row.getDouble(1);
    Optional<Double> value3 = row.getDouble(2);
    Optional<Double> value4 = row.getDouble(3);
    assertFalse(value1.isPresent());
    assertTrue(value2.isPresent());
    assertTrue(value3.isPresent());
    assertTrue(value4.isPresent());

    assertEquals(1, (double) value2.get());
    assertEquals(2, (double) value3.get());
    assertEquals(Long.MAX_VALUE, (double) value4.get());
  }

  @Test
  public void testGetStringWithColumnName() {
    Row row = getTestRow();

    Optional<String> value1 = row.getString("test");
    Optional<String> value2 = row.getString("nonExistentColumn");

    assertTrue(value1.isPresent());
    assertFalse(value2.isPresent());
    assertEquals("test", value1.get());
  }

  @Test
  public void testGetIntWithColumnName() {
    Row row = getTestRow();

    Optional<Integer> value1 = row.getInt("test2");
    Optional<Integer> value2 = row.getInt("nonExistentColumn");

    assertTrue(value1.isPresent());
    assertFalse(value2.isPresent());
    assertEquals(1, (int) value1.get());
  }

  @Test
  public void testGetLongWithColumnName() {
    Row row = getTestRow();

    Optional<Long> value1 = row.getLong("test4");
    Optional<Long> value2 = row.getLong("nonExistentColumn");

    assertTrue(value1.isPresent());
    assertFalse(value2.isPresent());
    assertEquals(Long.MAX_VALUE, (long) value1.get());
  }

  @Test
  public void testGetDoubleWithColumnName() {
    Row row = getTestRow();

    Optional<Double> value1 = row.getDouble("test4");
    Optional<Double> value2 = row.getDouble("nonExistentColumn");

    assertTrue(value1.isPresent());
    assertFalse(value2.isPresent());
    assertEquals(Long.MAX_VALUE, (double) value1.get());
  }

  @Test
  public void testGetIndex() {
    Row row = getTestRow();

    Optional<Integer> value1 = row.getIndex("test");
    Optional<Integer> value3 = row.getIndex("test4");
    Optional<Integer> value2 = row.getIndex("nonExistentColumn");

    assertEquals(0, (int) value1.get());
    assertEquals(3, (int) value3.get());
    assertFalse(value2.isPresent());
  }

  @Test
  public void testGetObject() {
    Row row = getTestRow();

    Optional<String> value1 = row.getObject(0, String.class);
    Optional<Integer> value2 = row.getObject(1, Integer.class);
    Optional<Number> value3 = row.getObject(2, Number.class);

    assertTrue(value1.isPresent());
    assertTrue(value2.isPresent());
    assertFalse(value3.isPresent());
    assertEquals("test", value1.get());
    assertEquals(1, (int) value2.get());
  }

  @Test
  public void testGetObjectWithColumnName() {
    Row row = getTestRow();
    Optional<String> value1 = row.getObject("test", String.class);
    Optional<String> value2 = row.getObject("nonExistentColumn", String.class);

    assertTrue(value1.isPresent());
    assertFalse(value2.isPresent());
    assertEquals("test", value1.get());
  }

  private Row getTestRow() {
    Row row = Mockito.mock(Row.class);
    when(row.getColumnNames()).thenReturn(Arrays.asList("test", "test2", "test3", "test4"));
    when(row.getColumnCount()).thenReturn(4);

    when(row.getValueAt(eq(0))).thenReturn(Optional.of("test"));
    when(row.getValueAt(eq(1))).thenReturn(Optional.of(1));
    when(row.getValueAt(eq(2))).thenReturn(Optional.of("2"));
    when(row.getValueAt(eq(3))).thenReturn(Optional.of(Long.MAX_VALUE));

    when(row.getValueAt(anyString())).thenCallRealMethod();

    when(row.getObject(anyInt(), any())).thenCallRealMethod();
    when(row.getObject(anyString(), any())).thenCallRealMethod();

    when(row.getIndex(anyString())).thenCallRealMethod();

    when(row.getLong(anyInt())).thenCallRealMethod();
    when(row.getLong(anyString())).thenCallRealMethod();

    when(row.getDouble(anyInt())).thenCallRealMethod();
    when(row.getDouble(anyString())).thenCallRealMethod();

    when(row.getInt(anyInt())).thenCallRealMethod();
    when(row.getInt(anyString())).thenCallRealMethod();

    when(row.getString(anyInt())).thenCallRealMethod();
    when(row.getString(anyString())).thenCallRealMethod();

    when(row.getColumnName(anyInt())).thenCallRealMethod();
    return row;
  }
}
