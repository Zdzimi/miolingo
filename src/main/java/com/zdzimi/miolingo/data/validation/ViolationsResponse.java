package com.zdzimi.miolingo.data.validation;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViolationsResponse {

  private List<Violation> violations = new ArrayList<>();

}
