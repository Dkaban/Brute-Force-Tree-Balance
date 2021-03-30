//*************************************************************************************
//  BalanceTreeDriver.java
//
//  AUTHOR: DUSTIN KABAN
//  ID: T00663749
//  DATE: March 29th, 2021
//  COURSE: COMP 2231, ASSIGNMENT 4: QUESTION 2
//
//  This is the driver class to test functionality of a brute force method for balancing a binary search tree.
//  The process is displayed to the user.  Adding elements, seeing outputs, balancing then rechecking.
//*************************************************************************************

public class BalanceTreeDriver
{
    public static void main(String[] args)
    {
        //Instantiate the LinkedBinarySearch Tree
        LinkedBinarySearchTree<Integer> linkedBinarySearchTree = new LinkedBinarySearchTree<>();

        //Add elements to the binary Search Tree and display them as they are added
        linkedBinarySearchTree.addElement(10);
        System.out.println(linkedBinarySearchTree.toString());
        linkedBinarySearchTree.addElement(20);
        System.out.println(linkedBinarySearchTree.toString());
        linkedBinarySearchTree.addElement(30);
        System.out.println(linkedBinarySearchTree.toString());
        linkedBinarySearchTree.addElement(40);
        System.out.println(linkedBinarySearchTree.toString());
        linkedBinarySearchTree.addElement(50);
        System.out.println(linkedBinarySearchTree.toString());

        //Display the root element and the height of the tree to the user
        System.out.println("\nRoot node is: " + linkedBinarySearchTree.getRootElement());
        System.out.println("Height Before Balance: " + linkedBinarySearchTree.getHeight());

        //Balance the tree then display new root and new height
        linkedBinarySearchTree.balanceTree();
        System.out.println("\nRoot is: " + linkedBinarySearchTree.getRootElement());
        System.out.println("Height After Balance: " + linkedBinarySearchTree.getHeight());

        //Add more elements to the linkedbinarysearchtree
        System.out.println(linkedBinarySearchTree.toString());
        linkedBinarySearchTree.addElement(60);
        System.out.println(linkedBinarySearchTree.toString());
        linkedBinarySearchTree.addElement(70);
        System.out.println(linkedBinarySearchTree.toString());
        linkedBinarySearchTree.addElement(80);
        System.out.println(linkedBinarySearchTree.toString());

        //Do the same as above, display root and height, balance, then display again
        System.out.println("\nRoot node is: " + linkedBinarySearchTree.getRootElement());
        System.out.println("Height Before Balance: " + linkedBinarySearchTree.getHeight());
        linkedBinarySearchTree.balanceTree();
        System.out.println("\nRoot is: " + linkedBinarySearchTree.getRootElement());
        System.out.println("Height After Balance: " + linkedBinarySearchTree.getHeight());
    }
}
