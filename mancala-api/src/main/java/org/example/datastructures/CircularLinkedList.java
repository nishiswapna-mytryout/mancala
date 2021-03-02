package org.example.datastructures;

import lombok.Getter;
import lombok.Setter;
import org.example.domain.Pit;

import java.util.function.Supplier;

public class CircularLinkedList<T> {
    private Node<T> head = null;
    private Node<T> tail = null;

    public void addNode(T value) {
        Node<T> newNode = new Node<>(value);

        if (head == null) {
            head = newNode;
        } else {
            tail.setNextNode(newNode);
        }

        tail = newNode;
        tail.setNextNode(head);
    }

    public boolean containsNode(T searchValue) {
        Node<T> currentNode = head;

        if (head == null) {
            return false;
        } else {
            do {
                if (currentNode.getValue().equals(searchValue)) {
                    return true;
                }
                currentNode = currentNode.getNextNode();
            } while (currentNode != head);
            return false;
        }
    }

    public Node<T> getNode(T searchValue) {
        Node<T> currentNode = head;

        if (head == null) {
            return null;
        } else {
            do {
                if (currentNode.getValue().equals(searchValue)) {
                    return currentNode;
                }
                currentNode = currentNode.getNextNode();
            } while (currentNode != head);
            return null;
        }
    }

    public Node<T> getNodeElseThrow(T searchValue, Supplier<? extends RuntimeException> exceptionSupplier){
        Node<T> node = getNode(searchValue);
        if(node==null){
            throw exceptionSupplier.get();
        }
        return node;
    }
}
