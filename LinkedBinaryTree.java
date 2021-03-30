//*************************************************************************************
//  LinkedBinaryTree.java
//
//  AUTHOR: DUSTIN KABAN
//  ID: T00663749
//  DATE: March 09, 2021
//  COURSE INFO: COMP 2231 ASSIGNMENT 4, QUESTION 1
//
// This is a version of a binary tree that implements the BinaryTreeADT
// allows for tree functionality, such as getLeft and getRight children nodes etc.
//*************************************************************************************

import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.NoSuchElementException;

public class LinkedBinaryTree<T> implements BinaryTreeADT<T>, Iterable<T>
{
    protected BinaryTreeNode<T> root;
    protected int modCount;

    /**
     * Creates an empty binary tree.
     */
    public LinkedBinaryTree()
    {
        root = null;
    }

    /**
     * Creates a binary tree with the specified element as its root.
     *
     * @param element the element that will become the root of the binary tree
     */
    public LinkedBinaryTree(T element)
    {
        root = new BinaryTreeNode<T>(element);
    }

    /**
     * Creates a binary tree with the specified element as its root and the
     * given trees as its left child and right child
     *
     * @param element the element that will become the root of the binary tree
     * @param left the left subtree of this tree
     * @param right the right subtree of this tree
     */
    public LinkedBinaryTree(T element, LinkedBinaryTree<T> left,
                            LinkedBinaryTree<T> right)
    {
        root = new BinaryTreeNode<T>(element);
        root.setLeft(left.root);
        root.setRight(right.root);
    }

    /**
     * Returns a reference to the element at the root
     *
     * @return a reference to the specified target
     * @throws EmptyCollectionException if the tree is empty
     */
    public T getRootElement() throws EmptyCollectionException
    {
        if (root == null)
            throw new EmptyCollectionException("LinkedBinaryTree");

        return (root.getElement());
    }

    /**
     * Returns a reference to the node at the root
     *
     * @return a reference to the specified node
     * @throws EmptyCollectionException if the tree is empty
     */
    protected BinaryTreeNode<T> getRootNode() throws EmptyCollectionException
    {
        if (root == null)
            throw new EmptyCollectionException("LinkedBinaryTree");

        return (root);
    }

    /**
     * Returns the left subtree of the root of this tree.
     *
     * @return a link to the left subtree of the tree
     */
    public LinkedBinaryTree<T> getLeft()
    {
        if (root == null)
            throw new EmptyCollectionException("Get left operation "
                    + "failed. The tree is empty.");

        LinkedBinaryTree<T> result = new LinkedBinaryTree<T>();
        result.root = root.getLeft();

        return result;
    }

    /**
     * Returns the right subtree of the root of this tree.
     *
     * @return a link to the right subtree of the tree
     */
    public LinkedBinaryTree<T> getRight()
    {
        if (root == null)
            throw new EmptyCollectionException("Get right operation "
                    + "failed. The tree is empty.");

        LinkedBinaryTree<T> result = new LinkedBinaryTree<T>();
        result.root = root.getRight();

        return result;
    }

    /**
     * Returns true if this binary tree is empty and false otherwise.
     *
     * @return true if this binary tree is empty, false otherwise
     */
    public boolean isEmpty()
    {
        return (root == null);
    }

    /**
     * Returns the integer size of this tree.
     *
     * @return the integer size of the tree
     */
    public int size()
    {
        return root.numChildren() + 1;
    }

    /**
     * Returns the height of this tree.
     *
     * @return the height of the tree
     */
    public int getHeight()
    {
        return height(root) - 1;
    }

    /**
     * Returns the height of the specified node.
     *
     * @param node the node from which to calculate the height
     * @return the height of the tree
     */
    private int height(BinaryTreeNode<T> node)
    {
        int result = 0;
        if (node != null)
            result = Math.max(height(node.getLeft()), height(node.getRight())) + 1;

        return result;
    }

    /**
     * Returns true if this tree contains an element that matches the
     * specified target element and false otherwise.
     *
     * @param targetElement the element being sought in this tree
     * @return true if the element in is this tree, false otherwise
     */
    public boolean contains(T targetElement)
    {
        T temp;
        boolean found = false;
        try
        {
            temp = find(targetElement);
            found = true;
        }
        catch (Exception ElementNotFoundException)
        {
            found = false;
        }
        return found;
    }

    /**
     * Returns a reference to the specified target element if it is
     * found in this binary tree.  Throws a ElementNotFoundException if
     * the specified target element is not found in the binary tree.
     *
     * @param targetElement the element being sought in this tree
     * @return a reference to the specified target
     * @throws ElementNotFoundException if the element is not in the tree
     */
    public T find(T targetElement) throws ElementNotFoundException
    {
        BinaryTreeNode<T> current = findNode(targetElement, root);

        if (current == null)
            throw new ElementNotFoundException("LinkedBinaryTree");

        return (current.getElement());
    }

    /**
     * Returns a reference to the specified target element if it is
     * found in this binary tree.
     *
     * @param targetElement the element being sought in this tree
     * @param next the element to begin searching from
     */
    private BinaryTreeNode<T> findNode(T targetElement,
                                       BinaryTreeNode<T> next)
    {
        if (next == null)
            return null;

        if (next.getElement().equals(targetElement))
            return next;

        BinaryTreeNode<T> temp = findNode(targetElement, next.getLeft());

        if (temp == null)
            temp = findNode(targetElement, next.getRight());

        return temp;
    }

    /**
     * Returns a string representation of this binary tree showing
     * the nodes in an inorder fashion.
     *
     * @return a string representation of this binary tree
     */
    public String toString()
    {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        inOrder(root, tempList);

        return tempList.toString();
    }

    // Iterator (Modify to PostOrder, PreOrder, InOrder or LevelOrder)
    public Iterator<T> iterator()
    {
        return iteratorInOrder();
    }

    // InOrder iterator
    public Iterator<T> iteratorInOrder()
    {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        inOrder(root, tempList);

        return new TreeIterator(tempList.iterator());
    }

    // InOrder search of the tree
    protected void inOrder(BinaryTreeNode<T> node,
                           ArrayUnorderedList<T> tempList)
    {
        if (node != null)
        {
            inOrder(node.getLeft(), tempList);
            tempList.addToRear(node.getElement());
            inOrder(node.getRight(), tempList);
        }
    }

    // PreOrder iterator
    public Iterator<T> iteratorPreOrder()
    {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        preOrder(root, tempList);

        return new TreeIterator(tempList.iterator());
    }

    //PreOrder search of the tree
    protected void preOrder(BinaryTreeNode<T> node,
                            ArrayUnorderedList<T> tempList)
    {
        if (node != null)
        {
            tempList.addToRear(node.getElement());
            preOrder(node.getLeft(), tempList);
            preOrder(node.getRight(), tempList);
        }
    }

    // PostOrder Iterator of the tree
    public Iterator<T> iteratorPostOrder()
    {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        postOrder(root, tempList);

        return new TreeIterator(tempList.iterator());
    }

    //PostOrder search of the tree
    protected void postOrder(BinaryTreeNode<T> node,
                             ArrayUnorderedList<T> tempList)
    {
        if (node != null)
        {
            postOrder(node.getLeft(), tempList);
            postOrder(node.getRight(), tempList);
            tempList.addToRear(node.getElement());
        }
    }

    //LevelOrder Search of the Tree
    public Iterator<T> iteratorLevelOrder()
    {
        ArrayUnorderedList<BinaryTreeNode<T>> nodes =
                new ArrayUnorderedList<BinaryTreeNode<T>>();
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        BinaryTreeNode<T> current;

        nodes.addToRear(root);

        while (!nodes.isEmpty())
        {
            current = nodes.removeFirst();

            if (current != null)
            {
                tempList.addToRear(current.getElement());
                if (current.getLeft() != null)
                    nodes.addToRear(current.getLeft());
                if (current.getRight() != null)
                    nodes.addToRear(current.getRight());
            }
            else
                tempList.addToRear(null);
        }

        return new TreeIterator(tempList.iterator());
    }

    /**
     * Inner class to represent an iterator over the elements of this tree
     */
    private class TreeIterator implements Iterator<T>
    {
        private int expectedModCount;
        private Iterator<T> iter;

        /**
         * Sets up this iterator using the specified iterator.
         *
         * @param iter the list iterator created by a tree traversal
         */
        public TreeIterator(Iterator<T> iter)
        {
            this.iter = iter;
            expectedModCount = modCount;
        }

        /**
         * Returns true if this iterator has at least one more element
         * to deliver in the iteration.
         *
         * @return  true if this iterator has at least one more element to deliver
         *          in the iteration
         * @throws  ConcurrentModificationException if the collection has changed
         *          while the iterator is in use
         */
        public boolean hasNext() throws ConcurrentModificationException
        {
            if (!(modCount == expectedModCount))
                throw new ConcurrentModificationException();

            return (iter.hasNext());
        }

        /**
         * Returns the next element in the iteration. If there are no
         * more elements in this iteration, a NoSuchElementException is
         * thrown.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iterator is empty
         */
        public T next() throws NoSuchElementException
        {
            if (hasNext())
                return (iter.next());
            else
                throw new NoSuchElementException();
        }

        /**
         * The remove operation is not supported.
         *
         * @throws UnsupportedOperationException if the remove operation is called
         */
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
}

