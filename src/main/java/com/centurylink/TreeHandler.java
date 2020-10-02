package com.centurylink;

import org.barfuin.texttree.api.Node;
import org.barfuin.texttree.api.TextTree;

import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

public class TreeHandler {

    private static final int LIMIT = 5;
    private final SortedSet<PrintableNode> orphans = new ConcurrentSkipListSet<>();

    /**
     * Builds the tree.
     *
     * @param data The pipe & comma delimited source String
     * @return the tree hierarchy from the source data
     */
    public PrintableNode buildTree(String data) {
        final String[] records = data.split("\\|");
        PrintableNode root = null;
        PrintableNode node;
        PrintableNode result;
        for (String record : records) {
            String[] field = record.split(",");
            node = new PrintableNode(field[0], field[1], field[2]);

            result = node.add(root);

            if (result == null) {
                orphans.add(node);
            } else {
                root = result;
            }
        }
        insertOrphans(root);

        return root;
    }

    private void insertOrphans(PrintableNode root) {
        //Attach the children that were parsed before their parents
        if (!orphans.isEmpty()) {
            int attempts = 0;
            while (attempts <= LIMIT && !orphans.isEmpty()) {
                orphans.removeIf(orphan -> orphan.add(root) != null);
                attempts++;
            }
        }
    }

    /**
     * Renders the tree as a formatted text string to visualize the data
     *
     * @param tree A tree of Nodes
     * @return the rendered text
     */
    public String render(Node tree) {
        return TextTree.newInstance().render(tree);
    }

    /**
     * Renders the tree and prints it to the default output
     *
     * @param tree a tree of Nodes
     */
    public void print(Node tree) {
        System.out.println(render(tree));
    }

}
