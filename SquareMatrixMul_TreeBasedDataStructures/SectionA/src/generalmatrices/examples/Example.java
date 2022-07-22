package generalmatrices.examples;

import generalmatrices.matrix.Matrix;
import generalmatrices.pair.PairWithOperators;

import java.util.List;

public class Example {

  public static Matrix<PairWithOperators> multiplyPairMatrices(
      List<Matrix<PairWithOperators>> matrices) {
    assert (!matrices.isEmpty());
    return matrices.stream()
        .reduce((a, b) -> a.product(b, PairWithOperators::sum, PairWithOperators::product))
        .get();
  }
}
