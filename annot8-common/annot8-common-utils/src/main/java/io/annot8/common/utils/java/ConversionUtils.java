/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.utils.java;

import java.util.Optional;
import java.util.function.Function;

public final class ConversionUtils {

  private ConversionUtils() {
    // Singleton
  }

  /**
   * Converts the provided value to an Integer if possible.
   *
   * @param valueOptional - Optional containing value to convert to Integer
   * @return Optional containing an Integer or empty if a value is not present or cannot be
   *     converted
   */
  public static Optional<Integer> toInt(Optional<Object> valueOptional) {
    return convertOptional(valueOptional, ConversionUtils::toInt);
  }

  /**
   * Converts the provided value to an Long if possible.
   *
   * @param valueOptional Optional parameter to convert to Long
   * @return Optional containing a Long value or empty if the parameter cannot be converted
   */
  public static Optional<Long> toLong(Optional<Object> valueOptional) {
    return convertOptional(valueOptional, ConversionUtils::toLong);
  }

  /**
   * Converts the provided value to an Double if possible.
   *
   * @param valueOptional Optional parameter to convert to Long
   * @return Optional containing a Double value or empty if the parameter cannot be converted
   */
  public static Optional<Double> toDouble(Optional<Object> valueOptional) {
    return convertOptional(valueOptional, ConversionUtils::toDouble);
  }

  private static <T> Optional<T> convertOptional(
      Optional<Object> valueOptional, Function<Object, T> function) {
    if (valueOptional.isPresent()) {
      return valueOptional.map(function);
    }
    return Optional.empty();
  }

  private static Double toDouble(Object value) {
    if (value instanceof Number) {
      return ((Number) value).doubleValue();
    } else if (value instanceof String) {
      try {
        return Double.parseDouble((String) value);
      } catch (NumberFormatException e) {
        return null;
      }
    }
    return null;
  }

  private static Long toLong(Object value) {
    if (value instanceof Number) {
      return ((Number) value).longValue();
    } else if (value instanceof String) {
      try {
        return Long.parseLong((String) value);
      } catch (NumberFormatException e) {
        return null;
      }
    }
    return null;
  }

  private static Integer toInt(Object value) {
    if (value instanceof Number) {
      return ((Number) value).intValue();
    } else if (value instanceof String) {
      try {
        return Integer.parseInt((String) value);
      } catch (NumberFormatException e) {
        return null;
      }
    }
    return null;
  }
}
