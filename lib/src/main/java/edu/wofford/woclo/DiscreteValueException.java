package edu.wofford.woclo;

import java.util.Arrays;

public class DiscreteValueException extends RuntimeException {
  private String argumentName;
  private String values;
  private String[] discreteValues;

  public DiscreteValueException(String argumentName, String values, String[] discreteValues) {
    super(
        argumentName
            + " value "
            + values
            + " is not a member of "
            + Arrays.toString(discreteValues));

    this.argumentName = argumentName;
    this.values = values;
    this.discreteValues = discreteValues.clone();
  }

  public String getArgumentName() {
    return argumentName;
  }

  public String getValues() {
    return values;
  }

  public String[] getDiscreteValues() {
    return discreteValues.clone();
  }
}
