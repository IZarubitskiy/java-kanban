import java.util.*;

public class InMemoryHistoryManager implements HistoryManager{
    private List<Task> history = new ArrayList<>();
    private Map<Integer, Node> historyLinked = new HashMap<>();

    public class manualLinkedList<T> {
        private Node head;
        private int size = 0;

        public Node linkLast(Task task) {
            Node oldHead = head;
            System.out.println(head);
            System.out.println(oldHead);
            Node newNode = new Node(task);
            head = newNode;
            System.out.println(head);
            System.out.println(oldHead);
            if (oldHead != null){
                head.next = oldHead;
            }
            size++;
            return newNode;
        }

        public List<Task> getTasks() {
            List<Task> taskListHystory = new ArrayList<>();
            Node t = this.head;       //получаем ссылку на первый элемент
            while (t != null)           //пока элемент существуе
            {
                taskListHystory.add(t.data);
                t = t.next;
            }
            return taskListHystory;
        }

        public void removeNode(Node node) {

            if (head == null) {
                return;
            }
            if (head.data == node.data) {
                head = head.next;
                size--;
                return;
            }
            Node t = head;
            while (t.next != null) {
                if (t.next.data == node.data) {

                    t.next = t.next.next;
                    size--;
                    return;
                }
                t = t.next;
            }
        }
        public int size() {
            return this.size;
        }
    }

    manualLinkedList<Task> newManualLinkedList = new manualLinkedList<>();

    @Override
    public void add(Task task) {
        if (historyLinked.containsKey(task.getId())) {
            newManualLinkedList.removeNode(historyLinked.get(task.getId()));
        }
        historyLinked.put(task.getId(), newManualLinkedList.linkLast(task));
    }

    @Override
    public void remove(int id){
        if (historyLinked.containsKey(id)) {
            newManualLinkedList.removeNode(historyLinked.get(id));
        }

    }

    @Override
    public List<Task> getHistory() {
        history = newManualLinkedList.getTasks();
        return history;
    }
}
