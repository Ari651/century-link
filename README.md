# century-link
Builds and renders a node hierarchy from a structured String. Order of the nodes within the String won't affect the output.

Implemented as node objects because I picked the print library first. A different solution could be a lot more efficient in terms of both space and time, by storing the parent-child
relationships as a list of pointers from the node IDs to the parent IDs, and building the tree on demand for rendering. 

That would have the added benefit of creating the tree in one pass without deferring attachment of any node whose parent isn't already in the tree. 
