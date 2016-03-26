//
// Generated by JTB 1.3.2
//

package Simly.visitor;
import Simly.syntaxtree.*;
import java.util.*;

/**
 * All GJ visitors must implement this interface.
 */

public interface GJVisitor<R,A> {

   //
   // GJ Auto class visitors
   //

   public R visit(NodeList n, A argu);
   public R visit(NodeListOptional n, A argu);
   public R visit(NodeOptional n, A argu);
   public R visit(NodeSequence n, A argu);
   public R visit(NodeToken n, A argu);

   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> ( Require() "." )+
    * f1 -> ( StatementExpression() )*
    */
   public R visit(Start n, A argu);

   /**
    * f0 -> "require"
    * f1 -> ( <IDENTIFIER> )+
    */
   public R visit(Require n, A argu);

   /**
    * f0 -> AdditiveExpression()
    */
   public R visit(MathExpression n, A argu);

   /**
    * f0 -> MultiplicativeExpression()
    * f1 -> ( ( "+" | "-" ) MultiplicativeExpression() )*
    */
   public R visit(AdditiveExpression n, A argu);

   /**
    * f0 -> UnaryExpression()
    * f1 -> ( ( "*" | "/" | "%" ) UnaryExpression() )*
    */
   public R visit(MultiplicativeExpression n, A argu);

   /**
    * f0 -> "(" MathExpression() ")"
    *       | <INTEGER_LITERAL>
    *       | VariableName()
    */
   public R visit(UnaryExpression n, A argu);

   /**
    * f0 -> RelationalEqualityExpression()
    */
   public R visit(RelationalExprssion n, A argu);

   /**
    * f0 -> RelationalGreaterExpression()
    * f1 -> [ ( "==" | "!=" ) RelationalGreaterExpression() ]
    */
   public R visit(RelationalEqualityExpression n, A argu);

   /**
    * f0 -> RelationalLessExpression()
    * f1 -> [ ( ">" | ">=" ) RelationalLessExpression() ]
    */
   public R visit(RelationalGreaterExpression n, A argu);

   /**
    * f0 -> UnaryRelational()
    * f1 -> [ ( "<" | "<=" ) UnaryRelational() ]
    */
   public R visit(RelationalLessExpression n, A argu);

   /**
    * f0 -> MathExpression()
    */
   public R visit(UnaryRelational n, A argu);

   /**
    * f0 -> "if"
    * f1 -> RelationalExprssion()
    * f2 -> "do"
    * f3 -> ( StatementExpression() )*
    * f4 -> "stop"
    */
   public R visit(IfExpression n, A argu);

   /**
    * f0 -> "while"
    * f1 -> RelationalExprssion()
    * f2 -> "do"
    * f3 -> ( StatementExpression() )*
    * f4 -> "stop"
    */
   public R visit(WhileExpression n, A argu);

   /**
    * f0 -> "def"
    * f1 -> VariableName()
    * f2 -> "="
    * f3 -> MathExpression()
    * f4 -> "."
    */
   public R visit(VariableDeclaration n, A argu);

   /**
    * f0 -> VariableName()
    * f1 -> "="
    * f2 -> MathExpression()
    * f3 -> "."
    */
   public R visit(VariableAssign n, A argu);

   /**
    * f0 -> <IDENTIFIER>
    */
   public R visit(VariableName n, A argu);

   /**
    * f0 -> <IDENTIFIER>
    * f1 -> ( ":" <IDENTIFIER> )+
    * f2 -> "("
    * f3 -> MathExpression()
    * f4 -> ( "," MathExpression() )*
    * f5 -> ")"
    * f6 -> "."
    */
   public R visit(JavaStaticMethods n, A argu);

   /**
    * f0 -> VariableDeclaration()
    *       | VariableAssign()
    *       | JavaStaticMethods()
    *       | IfExpression()
    *       | WhileExpression()
    */
   public R visit(StatementExpression n, A argu);

}