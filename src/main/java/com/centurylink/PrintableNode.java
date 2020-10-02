package com.centurylink;

import org.barfuin.texttree.api.Node;

import java.util.SortedSet;
import java.util.TreeSet;


//TODO What if the parent or even root hasn't been parsed yet?
public class PrintableNode implements Comparable<PrintableNode>, Node {

    private final long id;
    private final String name;
    //Sorted set to de-duplicate & maintain ID order
    final SortedSet<PrintableNode> children = new TreeSet<>();

    PrintableNode(String parentId, String nodeId, String nodeName, PrintableNode root) {
        name = nodeName;
        id = Long.parseLong(nodeId);
        if (parentId == null || parentId.equalsIgnoreCase("null")) {
            add(id, root);
        } else {
            add(Long.parseLong(parentId), root);
        }
    }

    private boolean add(long parentId, PrintableNode root) {
        if (root == null) {
            return false;
        }

        PrintableNode parent;
        if (root.id == parentId) {
            parent = root;
        } else {
            parent = root.children.stream().filter(c -> c.id == parentId).findAny().orElse(null);
        }


        if (parent != null) {
            parent.children.add(this);
            return true;
        } else {
            for (PrintableNode child : root.children) {
                if (add(parentId, child)) {
                    //Don't Check them all if it was already added.
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public int compareTo(PrintableNode node) {
        return Long.compare(this.id, node.id);
    }

    @Override
    public String getText() {
        return String.format("%d. %s", id, name);
    }

    @Override
    public Iterable<PrintableNode> getChildren() {
        return children;
    }
}
