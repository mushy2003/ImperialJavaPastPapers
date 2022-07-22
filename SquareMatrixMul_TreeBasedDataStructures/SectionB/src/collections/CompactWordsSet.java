package collections;

import collections.exceptions.InvalidWordException;
import java.util.List;

public interface CompactWordsSet {

  static void checkIfWordIsValid(String word) throws InvalidWordException {
    if (word == null || word.isEmpty()) {
      throw new InvalidWordException("Invalid word");
    }

    for (int i = 0; i < word.length(); i++) {
      if (word.charAt(i) < 'a' || word.charAt(i) > 'z') {
        throw new InvalidWordException("Invalid word");
      }
    }
  }

  boolean add(String word) throws InvalidWordException;

  boolean remove(String word) throws InvalidWordException;

  boolean contains(String word) throws InvalidWordException;

  int size();

  List<String> uniqueWordsInAlphabeticOrder();

}
