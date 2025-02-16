package com.uno.shared.domain.utils;

import com.uno.shared.domain.constants.StateEnum;

import java.util.List;
import java.util.Objects;

public class ValidationUtils {

  public static void validateString(String input, String errorMessage) {
    if (input == null || input.isEmpty()) {
      throw new IllegalArgumentException(errorMessage);
    }
  }

  public static void validateInteger(Integer input, String errorMessage) {
    if (input == null || input < 0) {
      throw new IllegalArgumentException(errorMessage);
    }
  }

  public static void validateNotNull(Object input, String errorMessage) {
    if (input == null) {
      throw new IllegalArgumentException(errorMessage);
    }
   }

  public static void validateIntegerCouldBeNegative(Integer input, String errorMessage) {
    if (input == null) {
      throw new IllegalArgumentException(errorMessage);
    }
  }

  public static void validateStringList(List<String> input, String errorMessage) {
    if (input == null || input.isEmpty()) {
      throw new IllegalArgumentException(errorMessage);
    }
    for (String item : input) {
      validateString(item, errorMessage);
    }
  }

  public static void validateObjectList(List<?> input, String errorMessage) {
    if (input == null || input.isEmpty()) {
      throw new IllegalArgumentException(errorMessage);
    }
  }

  public static void validateState(String input, String errorMessage) {
    if (Objects.equals(input, StateEnum.IN_PROGRESS.name()) || Objects.equals(input, StateEnum.FINISHED.name())) {
      return;
    }
    throw new IllegalArgumentException(errorMessage);
  }
}
