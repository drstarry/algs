/*
Subset client. Write a client program Subset.java that takes a command-line integer k; reads in a sequence of N strings from standard input using StdIn.readString(); and prints out exactly k of them, uniformly at random. Each item from the sequence can be printed out at most once. You may assume that 0 ≤ k ≤ N, where N is the number of string on standard input.
% echo A B C D E F G H I | java Subset 3
C
G
A

% echo A B C D E F G H I | java Subset 3
E
F
G
The running time of Subset must be linear in the size of the input. You may use only a constant amount of memory plus either one Deque or RandomizedQueue object of maximum size at most N, where N is the number of strings on standard input. (For an extra challenge, use only one Deque or RandomizedQueue object of maximum size at most k.) It should have the following API.

*/

import java.lang.*;
import java.util.*;

public class Subset {
   public static void main(String[] args) {
        int k = args[1];
        StdOut.print(StdIn.readString());
   }
}
