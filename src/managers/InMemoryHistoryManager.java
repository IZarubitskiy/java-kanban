package managers;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private Node head;
    private Node tail;
    private int size = 0;
    final private HashMap<Integer, Node> historyLinked = new HashMap<>();

    private class Node {
        public Task task;
        public Node next;
        public Node prev;

        public Node(Node prev, Task task, Node next) {
            this.task = task;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node linkLast(Task task) {
        final Node oldHead = head;
        final Node newNode = new Node(null, task, oldHead);
        head = newNode;
        if (oldHead == null)
            tail = newNode;
        else
            oldHead.prev = newNode;
        size++;
        return newNode;
    }

    private void removeNode(Node node) {
        if (head == null)
            return;
        if (head == tail) {
            head = null;
            tail = null;
            size = 0;
            return;
        }
        if (head == node) {
            head = head.next;
            size--;
            return;
        }
        Node t = head;
        while (t.next != null) {
            if (t.next == node) {
                if (tail == t.next) {
                    tail = t;
                }
                t.next = t.next.next;
                size--;
                return;
            }
            t = t.next;
        }
    }

    @Override
    public void add(Task task) {
        if (historyLinked.containsKey(task.getId())) {
            removeNode(historyLinked.get(task.getId()));
        }
        historyLinked.put(task.getId(), linkLast(task));
    }

    @Override
    public void remove(int id) {
        if (historyLinked.containsKey(id)) {
            removeNode(historyLinked.get(id));
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> taskListHistory = new ArrayList<>();
        Node t = this.head;
        while (t != null) {
            taskListHistory.add(t.task);
            t = t.next;
        }
        return taskListHistory;
    }
}
