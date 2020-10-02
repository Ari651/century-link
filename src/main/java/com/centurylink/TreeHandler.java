package com.centurylink;

import org.barfuin.texttree.api.Node;
import org.barfuin.texttree.api.TextTree;
import org.barfuin.texttree.api.TreeOptions;
import org.barfuin.texttree.api.style.TreeStyles;

public class TreeHandler {

    Node buildTree(String data) {
        final String[] records = data.split("\\|");
        PrintableNode root = null;
        PrintableNode last;
        for(String record : records) {
            String[] fields = record.split(",");
            last = new PrintableNode(fields[0], fields[1], fields[2], root);
            if(root == null) {
                root = last;
            }
        }

        return root;
    }

    void print(Node tree) {
        TreeOptions options = new TreeOptions();
//        options.setStyle(TreeStyles.UNICODE_ROUNDED);
        System.out.println(TextTree.newInstance(options).render(tree));
    }
}
