//
// Generated by JTB 1.3.2
//

package Simly.syntaxtree;

/**
 * Grammar production:
 * f0 -> <IDENTIFIER>
 */
public class VariableName implements Node {
   public NodeToken f0;

   public VariableName(NodeToken n0) {
      f0 = n0;
   }

   public void accept(Simly.visitor.Visitor v) {
      v.visit(this);
   }
   public <R,A> R accept(Simly.visitor.GJVisitor<R,A> v, A argu) {
      return v.visit(this,argu);
   }
   public <R> R accept(Simly.visitor.GJNoArguVisitor<R> v) {
      return v.visit(this);
   }
   public <A> void accept(Simly.visitor.GJVoidVisitor<A> v, A argu) {
      v.visit(this,argu);
   }
}

