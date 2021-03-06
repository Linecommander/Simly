//
// Generated by JTB 1.3.2
//

package Simly.syntaxtree;

/**
 * The interface which NodeList, NodeListOptional, and NodeSequence
 * implement.
 */
public interface NodeListInterface extends Node {
   public void addNode(Node n);
   public Node elementAt(int i);
   public java.util.Enumeration<Node> elements();
   public int size();

   public void accept(Simly.visitor.Visitor v);
   public <R,A> R accept(Simly.visitor.GJVisitor<R,A> v, A argu);
   public <R> R accept(Simly.visitor.GJNoArguVisitor<R> v);
   public <A> void accept(Simly.visitor.GJVoidVisitor<A> v, A argu);
}

