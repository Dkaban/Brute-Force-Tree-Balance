//*************************************************************************************
//  LinkedBinarySearchTree.java
//
//  AUTHOR: DUSTIN KABAN
//  ID: T00663749
//  DATE: March 29th, 2021
//  COURSE: COMP 2231, ASSIGNMENT 4: QUESTION 1
//
//  This class implements a linked binary search tree and all of its functionality.
//  It also contains a recursive brute force method for balancing a tree.
//*************************************************************************************

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedBinarySearchTree<T> extends LinkedBinaryTree<T>
        implements BinarySearchTreeADT<T> {
    /**
     * Creates an empty binary search tree.
     */
    public LinkedBinarySearchTree()
    {
        super();
    }

    /**
     * Creates a binary search with the specified element as its root.
     *
     * @param element the element that will be the root of the new binary
     *                search tree
     */
    public LinkedBinarySearchTree(T element)
    {
        super(element);

        if (!(element instanceof Comparable))
            throw new NonComparableElementException("LinkedBinarySearchTree");
    }

    /**
     * Adds the specified object to the binary search tree in the
     * appropriate position according to its natural order. Note that
     * equal elements are added to the right.
     *
     * @param element the element to be added to the binary search tree
     */
    public void addElement(T element)
    {
        if (!(element instanceof Comparable))
            throw new NonComparableElementException("LinkedBinarySearchTree");
        Comparable<T> comparableElement = (Comparable<T>) element;
        if (isEmpty())
            root = new BinaryTreeNode<T>(element);
        else {
            if (comparableElement.compareTo(root.getElement()) < 0) {
                if (root.getLeft() == null)
                    this.getRootNode().setLeft(new BinaryTreeNode<T>(element));
                else
                    addElement(element, root.getLeft());
            }
            else
            {
                if (root.getRight() == null)
                    this.getRootNode().setRight(new BinaryTreeNode<T>(element));
                else
                    addElement(element, root.getRight());
            }
        }
        modCount++;
    }

    /**
     * Adds the specified object to the binary search tree in the
     * appropriate position according to its natural order. Note that
     * equal elements are added to the right.
     *
     * @param element the element to be added to the binary search tree
     */
    private void addElement(T element, BinaryTreeNode<T> node)
    {
        Comparable<T> comparableElement = (Comparable<T>) element;
        if (comparableElement.compareTo(node.getElement()) < 0) {
            if (node.getLeft() == null)
                node.setLeft(new BinaryTreeNode<T>(element));
            else
                addElement(element, node.getLeft());
        } else {
            if (node.getRight() == null)
                node.setRight(new BinaryTreeNode<T>(element));
            else
                addElement(element, node.getRight());
        }
    }

    /**
     * Removes the first element that matches the specified target
     * element from the binary search tree and returns a reference to
     * it. Throws a ElementNotFoundException if the specified target
     * element is not found in the binary search tree.
     *
     * @param targetElement the element being sought in the binary search tree
     * @throws ElementNotFoundException if the target element is not found
     */
    public T removeElement(T targetElement)
            throws ElementNotFoundException {
        T result = null;
        if (isEmpty())
            throw new ElementNotFoundException("LinkedBinarySearchTree");
        else {
            BinaryTreeNode<T> parent = null;
            if (((Comparable<T>) targetElement).equals(root.element)) {
                result = root.element;
                BinaryTreeNode<T> temp = replacement(root);
                if (temp == null)
                    root = null;
                else
                {
                    root.element = temp.element;
                    root.setRight(temp.right);
                    root.setLeft(temp.left);
                }
                modCount--;
            } else {
                parent = root;
                if (((Comparable) targetElement).compareTo(root.element) < 0)
                    result = removeElement(targetElement, root.getLeft(), parent);
                else
                    result = removeElement(targetElement, root.getRight(), parent);
            }
        }
        return result;
    }

    /**
     * Removes the first element that matches the specified target
     * element from the binary search tree and returns a reference to
     * it. Throws a ElementNotFoundException if the specified target
     * element is not found in the binary search tree.
     *
     * @param targetElement the element being sought in the binary search tree
     * @param node          the node from which to search
     * @param parent        the parent of the node from which to search
     * @throws ElementNotFoundException if the target element is not found
     */
    private T removeElement(T targetElement, BinaryTreeNode<T> node, BinaryTreeNode<T>
            parent)
            throws ElementNotFoundException {
        T result = null;
        if (node == null)
            throw new ElementNotFoundException("LinkedBinarySearchTree");
        else {
            if (((Comparable<T>) targetElement).equals(node.element)) {
                result = node.element;
                BinaryTreeNode<T> temp = replacement(node);
                if (parent.right == node)
                    parent.right = temp;
                else
                parent.left = temp;
                modCount--;
            } else {
                parent = node;
                if (((Comparable) targetElement).compareTo(node.element) < 0)
                    result = removeElement(targetElement, node.getLeft(), parent);
                else
                    result = removeElement(targetElement, node.getRight(), parent);
            }
        }
        return result;
    }

    /**
     * Returns a reference to a node that will replace the one
     * specified for removal. In the case where the removed node has
     * two children, the inorder successor is used as its replacement.
     *
     * @param node the node to be removed
     * @return a reference to the replacing node
     */
    private BinaryTreeNode<T> replacement(BinaryTreeNode<T> node) {
        BinaryTreeNode<T> result = null;
        if ((node.left == null) && (node.right == null))
            result = null;
        else if ((node.left != null) && (node.right == null))
            result = node.left;
        else if ((node.left == null) && (node.right != null))
            result = node.right;
        else {
            BinaryTreeNode<T> current = node.right;
            BinaryTreeNode<T> parent = node;

            while (current.left != null) {
                parent = current;
                current = current.left;
            }
            current.left = node.left;
            if (node.right != current) {
                parent.left = current.right;
                current.right = node.right;
            }
            result = current;
        }
        return result;
    }

    /**
     * Removes elements that match the specified target element from
     * the binary search tree. Throws a ElementNotFoundException if
     * the sepcified target element is not found in this tree.
     *
     * @param targetElement the element being sought in the binary search tree
     * @throws ElementNotFoundException if the target element is not found
     */
    public void removeAllOccurrences(T targetElement)
            throws ElementNotFoundException
    {
        removeElement(targetElement);
        try
        {
            while (contains((T)targetElement))
                removeElement(targetElement);
        }
        catch (Exception ElementNotFoundException)
        {
        }
    }

    /**
     * Removes the node with the least value from the binary search
     * tree and returns a reference to its element. Throws an
     * EmptyCollectionException if this tree is empty.
     *
     * @return a reference to the node with the least value
     * @throws EmptyCollectionException if the tree is empty
     */
    public T removeMin() throws EmptyCollectionException
    {
        T result = null;
        if (isEmpty())
            throw new EmptyCollectionException("LinkedBinarySearchTree");
        else
        {
            if (root.left == null)
            {
                result = root.element;
                root = root.right;
            }
            else
            {
                BinaryTreeNode<T> parent = root;
                BinaryTreeNode<T> current = root.left;
                while (current.left != null)
                {
                    parent = current;
                    current = current.left;
                }
                result = current.element;
                parent.left = current.right;
            }
            modCount--;
        }
        return result;
    }

    //TODO: FINISH IMPLEMENTING THESE METHODS!!
    @Override
    public T removeMax() throws EmptyCollectionException
    {
        //Remove the maximum element in the tree
        //Traverse the right subtree and follow the right element all the way down.
        //Once we've reached null, we've found the bottom right most element, which would be the max element.
        T result = null;
        if(isEmpty())
        {
            throw new EmptyCollectionException("LinkedBinarySearchTree");
        }
        else
        {
            if(root.right == null)
            {
                result = root.element;
                root = root.left;
            }
            else
            {
                BinaryTreeNode<T> parent = root;
                BinaryTreeNode<T> current = root.right;
                while(current.right != null)
                {
                    parent = current;
                    current = current.right;
                }
                result = current.element;
                parent.right = current.left;
            }
            modCount--;
        }
        return result;
    }

    @Override
    public T findMin()
    {
        //returns a reference to the minimum element in the tree
        //Traverse the tree down the left side all the way down
        T min = null;
        if(isEmpty())
        {
            throw new EmptyCollectionException("LinkedBinarySearchTree");
        }
        else
        {
            BinaryTreeNode<T> current = root;
            while(current.left != null)
            {
                current = current.left;
            }
            min = current.element;
        }
        return min;
    }

    @Override
    public T findMax()
    {
        //Returns a reference to the maxmium element in the tree
        //Traverse the tree all the way down the right side
        T max = null;
        if(isEmpty())
        {
            throw new EmptyCollectionException("LinkedBinarySearchTree");
        }
        else
        {
            BinaryTreeNode<T> current = root;
            while(current.right != null)
            {
                current = current.right;
            }
            max = current.element;
        }
        return max;
    }

    public void balanceTree()
    {
        //Used to add elements to our list
        Iterator<T> inOrderIt = iteratorInOrder();
        //List to hold values according to an inOrder traversal
        List<T> tempList = new ArrayList<>();

        //Add all of the elements that are iterated over into the tempList
        while(inOrderIt.hasNext())
        {
            tempList.add(inOrderIt.next());
        }
        //Recursively create the binary tree by rebalancing it
        root = recursiveBalanceTree(tempList);
    }

    BinaryTreeNode<T> recursiveBalanceTree(List<T> values)
    {
        //If we have no more values left, return a null
        if(values.isEmpty())
        {
            //return null node, since there is no node
            return null;
        }
        else
        {
            //Set the node to the middle value in the list
            int middle = values.size() / 2;
            //Create an instance of that node
            BinaryTreeNode<T> node = new BinaryTreeNode<>(values.get(middle));
            //We want to recursively balance the left side of the tree
            node.setLeft(recursiveBalanceTree(values.subList(0,middle)));
            //Now we want to recursively balance the right side of the tree
            node.setRight(recursiveBalanceTree(values.subList(middle+1,values.size())));
            //Return the root node
            return node;
        }
    }
}