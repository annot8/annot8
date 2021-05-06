/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.utils.java;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import org.junit.jupiter.api.Test;

public class ConversionUtilsTest {

  @Test
  public void testToInt() {
    Optional<Integer> testIntValue = ConversionUtils.toInt(Optional.of(1));
    Optional<Integer> testLongValue = ConversionUtils.toInt(Optional.of(Long.MAX_VALUE));
    Optional<Integer> testDoubleValue = ConversionUtils.toInt(Optional.of(1.2));
    Optional<Integer> testStringValue = ConversionUtils.toInt(Optional.of("1"));
    Optional<Integer> testNonIntValue = ConversionUtils.toInt(Optional.of(new Object()));
    Optional<Integer> testNonNumericString = ConversionUtils.toInt(Optional.of("test"));
    Optional<Integer> testEmpty = ConversionUtils.toInt(Optional.empty());

    assertTrue(testIntValue.isPresent());
    assertTrue(testStringValue.isPresent());
    assertTrue(testDoubleValue.isPresent());
    assertTrue(testLongValue.isPresent());
    assertFalse(testNonNumericString.isPresent());
    assertFalse(testNonIntValue.isPresent());
    assertFalse(testEmpty.isPresent());

    assertEquals(1, (int) testIntValue.get());
    assertEquals(1, (int) testStringValue.get());
    assertEquals(1, (int) testDoubleValue.get());
    assertEquals(-1, (int) testLongValue.get());
  }

  @Test
  public void testToLong() {
    Optional<Long> testIntValue = ConversionUtils.toLong(Optional.of(1));
    Optional<Long> testLongValue = ConversionUtils.toLong(Optional.of(Long.MAX_VALUE));
    Optional<Long> testDoubleValue = ConversionUtils.toLong(Optional.of(1.2));
    Optional<Long> testStringValue = ConversionUtils.toLong(Optional.of("1"));
    Optional<Long> testNonIntValue = ConversionUtils.toLong(Optional.of(new Object()));
    Optional<Long> testNonNumericString = ConversionUtils.toLong(Optional.of("test"));
    Optional<Long> testEmpty = ConversionUtils.toLong(Optional.empty());

    assertTrue(testIntValue.isPresent());
    assertTrue(testStringValue.isPresent());
    assertTrue(testLongValue.isPresent());
    assertTrue(testDoubleValue.isPresent());
    assertFalse(testNonNumericString.isPresent());
    assertFalse(testNonIntValue.isPresent());
    assertFalse(testEmpty.isPresent());

    assertEquals(1, (long) testIntValue.get());
    assertEquals(1, (long) testStringValue.get());
    assertEquals(Long.MAX_VALUE, (long) testLongValue.get());
    assertEquals(1, (long) testDoubleValue.get());
  }

  @Test
  public void testToDouble() {
    Optional<Double> testIntValue = ConversionUtils.toDouble(Optional.of(1));
    Optional<Double> testLongValue = ConversionUtils.toDouble(Optional.of(1234567890L));
    Optional<Double> testDoubleValue = ConversionUtils.toDouble(Optional.of(1.2));
    Optional<Double> testStringValue = ConversionUtils.toDouble(Optional.of("1"));
    Optional<Double> testNonIntValue = ConversionUtils.toDouble(Optional.of(new Object()));
    Optional<Double> testNonNumericString = ConversionUtils.toDouble(Optional.of("test"));
    Optional<Double> testEmpty = ConversionUtils.toDouble(Optional.empty());

    assertTrue(testIntValue.isPresent());
    assertTrue(testStringValue.isPresent());
    assertTrue(testLongValue.isPresent());
    assertTrue(testDoubleValue.isPresent());
    assertFalse(testNonNumericString.isPresent());
    assertFalse(testNonIntValue.isPresent());
    assertFalse(testEmpty.isPresent());

    assertEquals(1.0, testIntValue.get(), 0.001);
    assertEquals(1.0, testStringValue.get(), 0.001);
    assertEquals(1234567890.0, testLongValue.get(), 0.001);
    assertEquals(1.2, testDoubleValue.get(), 0.001);
  }

  @Test
  public void testParseString() {
    assertNull(ConversionUtils.parseString(null));

    assertEquals(true, ConversionUtils.parseString("true"));
    assertEquals(true, ConversionUtils.parseString("TRUE"));
    assertEquals(true, ConversionUtils.parseString(" True  "));
    assertEquals(false, ConversionUtils.parseString("false"));
    assertEquals(false, ConversionUtils.parseString("FALSE"));
    assertEquals(false, ConversionUtils.parseString("  False "));

    assertEquals(123L, ConversionUtils.parseString("123"));
    assertEquals(-123L, ConversionUtils.parseString("-123"));

    assertEquals(123.45, ConversionUtils.parseString("123.45"));
    assertEquals(-123.45, ConversionUtils.parseString("-123.45"));
    assertEquals(0.12, ConversionUtils.parseString("0.12"));

    assertEquals("012345", ConversionUtils.parseString("012345"));

    assertEquals("Hello World!", ConversionUtils.parseString("Hello World!"));
    assertEquals(" Hello World!  ", ConversionUtils.parseString(" Hello World!  "));
  }
}
