package examples.plastic.transformed;

import examples.plastic.annotations.NotNull;

public class NotNullDemo {

  @NotNull
  private String value;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
