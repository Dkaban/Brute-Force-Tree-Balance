# Brute Force Binary Search Tree Balance
This is a repository for balancing a Binary Search Tree using a brute force recursive algorithm. 

### Languages: Java
The driver class is the BalanceTreeDriver.java file.

#### How it works
- Create a LinkedBinarySearchTree
- Add elements to it
- Create a list, sorted via an InOrder Traversal
- Balance that list via a recursive algorithm inside the LinkedBinarySearchTree.java class
- Balancing works by picking the middle element, then doing the same for the left and right subtrees, recursively.
