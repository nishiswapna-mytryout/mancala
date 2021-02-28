package org.example.datastructures;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Node<T>{
    private final T value;
    private Node<T> nextNode;

    public Node(final T value) {
        this.value = value;
    }
}
