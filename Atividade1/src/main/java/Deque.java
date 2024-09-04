


import java.util.ListIterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int n; // Contador de elementos
    private No Sentinela; // Nó artificial para marcar início e fim

    public Deque() {
        n = 0;
        Sentinela = new No();
        Sentinela.prox = Sentinela;
        Sentinela.ant = Sentinela;
    }

    private class No { // Classe Nó
        private Item dado;
        private No prox;
        private No ant;
    }

    public void push_front(Item item) {
        // Criar novo nó e armazenar dados
        No tmp = new No();
        tmp.dado = item;

        // Definir anterior e próximo do novo nó
        tmp.ant = Sentinela;
        tmp.prox = Sentinela.prox;

        // Ajustar a sentinela e o próximo
        Sentinela.prox = tmp;
        tmp.prox.ant = tmp;

        ++n;
    }

    public void push_back(Item item) {
        // Criar novo nó e armazenar dados
        No tmp = new No();
        tmp.dado = item;

        // Definir anterior e próximo do novo nó
        tmp.ant = Sentinela.ant;
        tmp.prox = Sentinela;

        // Ajustar a sentinela e o anterior
        Sentinela.ant = tmp;
        tmp.ant.prox = tmp;

        n++;
    }

    public Item pop_front() {
        No tmp = Sentinela.prox;
        Item meuDado = tmp.dado;

        // Atualizar o nó anterior para apontar para o próximo do que será removido
        tmp.ant.prox = tmp.prox;

        // Atualizar o nó próximo para apontar para o anterior do que será removido
        tmp.prox.ant = tmp.ant;

        --n;

        return meuDado;
    }

    public Item pop_back() {
        No tmp = Sentinela.ant;
        Item meuDado = tmp.dado;

        // Atualizar o nó anterior para apontar para o próximo do que será removido
        tmp.ant.prox = tmp.prox;

        // Atualizar o nó próximo para apontar para o anterior do que será removido
        tmp.prox.ant = tmp.ant;

        --n;

        return meuDado;
    }

    public No first() {
        if (Sentinela == Sentinela.prox) return null;
        return Sentinela.prox;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public ListIterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements ListIterator<Item> {

        private No atual = Sentinela.prox;
        private int indice = 0;
        private No acessadoultimo = null;

        public boolean hasNext() {
            return indice < n;
        }

        public boolean hasPrevious() {
            return indice > 0;
        }

        public int previousIndex() {
            return indice - 1;
        }

        public int nextIndex() {
            return indice;
        }

        public Item next() {
            if (!hasNext()) return null;

            Item meuDado = atual.dado;
            acessadoultimo = atual;
            atual = atual.prox;
            indice++;

            return meuDado;
        }

        public Item previous() {
            if (!hasPrevious()) return null;

            atual = atual.ant;
            Item meuDado = atual.dado;

            acessadoultimo = atual;
            indice--;

            return meuDado;
        }

        public void remove() {
            if (acessadoultimo == null) throw new IllegalStateException();

            acessadoultimo.ant.prox = acessadoultimo.prox;
            acessadoultimo.prox.ant = acessadoultimo.ant;
            --n;

            if (atual == acessadoultimo) {
                atual = acessadoultimo.prox;
            } else {
                indice--;
            }

            acessadoultimo = null;
        }

        public void set(Item x) {
            if (acessadoultimo == null) throw new IllegalStateException();
            acessadoultimo.dado = x;
        }

        public void add(Item x) {
            // Inserir após atual
            No tmp = new No();
            tmp.dado = x;
            tmp.prox = atual.prox;
            tmp.ant = atual;
            tmp.prox.ant = tmp;
            atual.prox = tmp;
            n++;
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item).append(" ");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        System.out.println(n + " random integers between 0 and 99");

        Deque<Integer> list = new Deque<>();
        for (int i = 0; i < n; i++) {
            list.push_front(i);
        }

        System.out.println(list);
        System.out.println();

        while (!list.isEmpty()) {
            System.out.println(list.pop_front());
        }

        for (int i = 0; i < n; i++) {
            list.push_back(i);
        }

        System.out.println(list);
        System.out.println();

        ListIterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            int x = it.next();
            it.set(x + 1);
        }

        System.out.println(list);
        System.out.println();

        while (it.hasPrevious()) {
            int x = it.previous();
            it.set(x + x);
        }

        System.out.println(list);
        System.out.println();

        while (it.hasNext()) {
            int x = it.next();
            if (x % 2 == 0) it.remove();
        }

        System.out.println(list);
        System.out.println();

        while (it.hasPrevious()) {
            int x = it.previous();
            it.add(x + x);
        }

        System.out.println(list);
        System.out.println();
    }
}


