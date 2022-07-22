package collections;

import collections.exceptions.InvalidWordException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleCompactWordTree implements CompactWordsSet {

  private final Node root = new Node(' ');
  private final AtomicInteger size = new AtomicInteger();
  private final int ALPHABETSIZE = 26;

  @Override
  public boolean add(String word) throws InvalidWordException {
    CompactWordsSet.checkIfWordIsValid(word);

    Node prev = root;
    prev.lock();

    Node curr = prev.getChildren()[word.charAt(0) - 'a'];

    if (curr == null) {
      curr = new Node(word.charAt(0));
      prev.getChildren()[word.charAt(0) - 'a'] = curr;
    }

    curr.lock();

    for (int i = 1; i < word.length(); i++) {
      prev.unlock();
      prev = curr;

      curr = curr.getChildren()[word.charAt(i) - 'a'];

      if (curr == null) {
        curr = new Node(word.charAt(i));
        prev.getChildren()[word.charAt(i) - 'a'] = curr;
      }

      curr.lock();
    }

    if (curr.isWord()) {
      prev.unlock();
      curr.unlock();
      return false;
    }

    curr.setWord();

    size.getAndIncrement();

    prev.unlock();
    curr.unlock();

    return true;
  }

  @Override
  public boolean remove(String word) throws InvalidWordException {
    CompactWordsSet.checkIfWordIsValid(word);

    Node prev = root;
    prev.lock();

    Node curr = null;

    for (int i = 0; i < word.length(); i++) {
      curr = prev.getChildren()[word.charAt(i) - 'a'];

      if (curr == null) {
        prev.unlock();
        return false;
      }

      curr.lock();

      prev.unlock();
      prev = curr;
    }

    assert curr != null;
    if (curr.isWord()) {
      curr.removeWord();
      curr.unlock();
      size.getAndDecrement();
      return true;
    }

    curr.unlock();
    return false;
  }

  @Override
  public boolean contains(String word) throws InvalidWordException {
    CompactWordsSet.checkIfWordIsValid(word);

    Node prev = root;
    prev.lock();

    Node curr = null;

    for (int i = 0; i < word.length(); i++) {
      curr = prev.getChildren()[word.charAt(i) - 'a'];

      if (curr == null) {
        prev.unlock();
        return false;
      }

      curr.lock();

      prev.unlock();

      prev = curr;
    }

    assert curr != null;

    if (curr.isWord()) {
      curr.unlock();
      return true;
    }

    curr.unlock();
    return false;
  }

  @Override
  public int size() {
    return size.get();
  }

  @Override
  public List<String> uniqueWordsInAlphabeticOrder() {
    List<String> words = new ArrayList<>();

    uniqueWords(words, "", root);

    return words;
  }

  private void uniqueWords(List<String> words, String currWord, Node curr) {
    if (curr.isWord()) {
      words.add(currWord);
    }

    for (int i = 0; i < ALPHABETSIZE; i++) {
      if (curr.getChildren()[i] != null) {
        uniqueWords(words, currWord + (char) ('a' + i), curr.getChildren()[i]);
      }
    }
  }
}
