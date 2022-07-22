package collections;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Node {

  private final Character value;
  private final Node[] children = new Node[26];
  private final Lock lock = new ReentrantLock();
  private volatile boolean isWord;

  public Node(Character value, boolean isWord) {
    this.value = value;
    this.isWord = isWord;
  }

  public Node(Character value) {
    this(value, false);
  }

  public Character value() {
    return value;
  }

  public boolean isWord() {
    return isWord;
  }

  public void setWord() {
    isWord = true;
  }

  public void removeWord() {
    isWord = false;
  }

  public Node[] getChildren() {
    return children;
  }

  public void lock() {
    lock.lock();
  }

  public void unlock() {
    lock.unlock();
  }
}
