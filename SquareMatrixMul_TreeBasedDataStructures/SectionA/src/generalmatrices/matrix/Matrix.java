package generalmatrices.matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

public class Matrix<T> {

  private final int order;
  private final T[][] elements;

  public Matrix(List<T> elementsList) {
    if (elementsList == null || elementsList.isEmpty()) {
      throw new IllegalArgumentException();
    }

    order = (int) Math.sqrt(elementsList.size());
    elements = (T[][]) new Object[order][order];

    for (int i = 0; i < elementsList.size(); i++) {
      elements[i / order][i % order] = elementsList.get(i);
    }
  }

  public T get(int row, int col) {
    assert (row < order && col < order);
    return elements[row][col];
  }

  public int getOrder() {
    return order;
  }

  public Matrix<T> sum(Matrix<T> other, BinaryOperator<T> elementSum) {
    assert (order == other.getOrder());

    List<T> newElements = new ArrayList<>();

    for (int i = 0; i < order; i++) {
      for (int j = 0; j < order; j++) {
        newElements.add(elementSum.apply(elements[i][j], other.get(i, j)));
      }
    }

    return new Matrix<>(newElements);
  }

  public Matrix<T> product(
      Matrix<T> other, BinaryOperator<T> elementSum, BinaryOperator<T> elementProduct) {
    assert (order == other.getOrder());

    List<T> newElements = new ArrayList<>();

    for (int i = 0; i < order; i++) {
      for (int j = 0; j < order; j++) {
        T currentSum = null;
        for (int k = 0; k < order; k++) {
          T product = elementProduct.apply(elements[i][k], other.get(k, j));

          if (currentSum == null) {
            currentSum = product;
          } else {
            currentSum = elementSum.apply(currentSum, product);
          }
        }
        newElements.add(currentSum);
      }
    }

    return new Matrix<>(newElements);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("[");

    for (int i = 0; i < order; i++) {
      sb.append('[');
      for (int j = 0; j < order; j++) {
        sb.append(elements[i][j]);
        if (j < order - 1) {
          sb.append(' ');
        }
      }
      sb.append(']');
    }

    sb.append(']');

    return sb.toString();
  }
}
