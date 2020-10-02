package com.centurylink;

//3rd party library for the output
import org.barfuin.texttree.api.Node;

import java.util.*;


public class PrintableNode implements Comparable<PrintableNode>, Node {

    private final long id;
    private final long parentId;
    private final String name;
    //Sorted set to de-duplicate & maintain ID order
    final SortedSet<PrintableNode> children = new TreeSet<>();

    PrintableNode(String parentId, String nodeId, String nodeName) {
        name = nodeName;
        id = Long.parseLong(nodeId);
        if (parentId == null || parentId.equalsIgnoreCase("null")) {
            this.parentId = -1;
        } else {
            this.parentId = Long.parseLong(parentId);
        }
    }

    /**
     * Adds this node to a tree of one or more Nodes
     * @param root The root Node of the tree
     * @return the tree with this node attached, or null if the parent isn't in the tree yet
     */
    PrintableNode add(PrintableNode root) {
        //Wait for the root before starting the tree.  TreeHandler will stash nodes it couldn't add yet
        if(root == null && parentId != -1) {
            return null;
        }

        if(root == null) {
            return this;
        } else if(parentId == -1) {
            this.children.add(root);
            return this;
        } else {
            if(add(this.parentId, root)) {
                return root;
            } else {
                return null;
            }
        }
    }

    private boolean add(long parentId, PrintableNode root) {
        if (root != null) {
            PrintableNode parent;
            if(root.id == parentId) {
                parent = root;
            } else {
                parent = root.children.stream().filter(c -> c.id == parentId).findAny().orElse(null);
            }

            if (parent != null) {
                parent.children.add(this);
                return true;
            } else {
                for (PrintableNode child : root.children) {
                    //Recursive
                    if (add(parentId, child)) {
                        //Don't Check them all if it was already added.
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public String getText() {
        return String.format("%d. %s", id, name);
    }

    @Override
    public Iterable<PrintableNode> getChildren() {
        return children;
    }

    @Override
    public int compareTo(PrintableNode node) {
        return Long.compare(this.id, node.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrintableNode that = (PrintableNode) o;

        //We only care about the IDs.  Last record read replaces previous values.
        return compareTo(that) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parentId, name);
    }
}
