package syntacticTree;

public class ListNode extends GeneralNode {
    public GeneralNode node;
    public ListNode next;

    public ListNode(GeneralNode t2) {
        super(t2.position); // passa token de referência para construtor da    
                            // superclasse. É o mesmo que o do seu filho

        node = t2;
        next = null; // primeiro elemento da lista
    }

    public ListNode(GeneralNode t2, ListNode l) {
        super(t2.position); // passa token de referência para construtor da

        // superclasse. É o mesmo que o do seu filho
        node = t2;
        next = l; // primeiro elemento da lista
    }

    public void add(GeneralNode t2) {
        if (next == null) { // verifica se é último da lista
            next = new ListNode(t2); // insere no final
        } else {
            next.add(t2); // insere após o próximo
        }
    }
}
