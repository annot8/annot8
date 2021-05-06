/* Annot8 (annot8.io) - Licensed under Apache-2.0. */
package io.annot8.common.utils.java;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;

public final class ConversionUtils {

  private static final Pattern ACTUAL_NUMBER = Pattern.compile("^-?(0\\.|[1-9]).*");

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

  /**
   * Parses a String and tries to convert it to the correct type.
   *
   * <p>First tries parsing as a boolean, then a long, then a double. It then tries to parse as
   * various temporal objects, assuming the string is in ISO format. If this fails, then the
   * original string is returned.
   *
   * <p>If the original string is null, then null is returned.
   *
   * @param s String to parse
   * @return Parsed object
   */
  public static Object parseString(String s) {
    // Null
    if (s == null) return null;

    String t = s.strip();

    // Boolean
    if ("true".equalsIgnoreCase(t)) {
      return true;
    } else if ("false".equalsIgnoreCase(t)) {
      return false;
    }

    // If string explicitly starts with a 0 and isn't a decimal, then assume it isn't a number

    if (ACTUAL_NUMBER.matcher(t).matches()) {
      // Long
      try {
        return Long.parseLong(t);
      } catch (NumberFormatException nfe) {
        // Do nothing
      }

      // Double
      try {
        return Double.parseDouble(t);
      } catch (NumberFormatException nfe) {
        // Do nothing
      }
    }

    // Temporal
    try {
      return OffsetDateTime.parse(t, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    } catch (DateTimeParseException dtpe) {
      // Do nothing
    }

    try {
      return LocalDateTime.parse(t, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    } catch (DateTimeParseException dtpe) {
      // Do nothing
    }

    try {
      return LocalDate.parse(t, DateTimeFormatter.ISO_LOCAL_DATE);
    } catch (DateTimeParseException dtpe) {
      // Do nothing
    }

    try {
      return OffsetTime.parse(t, DateTimeFormatter.ISO_OFFSET_TIME);
    } catch (DateTimeParseException dtpe) {
      // Do nothing
    }

    try {
      return LocalTime.parse(t, DateTimeFormatter.ISO_LOCAL_TIME);
    } catch (DateTimeParseException dtpe) {
      // Do nothing
    }

    // String
    return s;
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
