package com.centurylink;

import org.barfuin.texttree.api.Node;

public class CodeAssignment {
    /**
     * Expects a single argument: a pipe delimited set of records with comma delimited fields
     */
    public static void main(String[] args) {
        if(args.length > 0) {
            TreeHandler handler = new TreeHandler();
            Node tree = handler.buildTree(args[0]);
            handler.print(tree);
        }
    }
}
