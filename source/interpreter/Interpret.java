
package Simly.interpreter;

import Simly.core.SimlyScope;
import Simly.syntaxtree.AdditiveExpression;
import Simly.syntaxtree.IfExpression;
import Simly.syntaxtree.JavaStaticMethods;
import Simly.syntaxtree.MathExpression;
import Simly.syntaxtree.MultiplicativeExpression;
import Simly.syntaxtree.RelationalEqualityExpression;
import Simly.syntaxtree.RelationalExprssion;
import Simly.syntaxtree.RelationalGreaterExpression;
import Simly.syntaxtree.RelationalLessExpression;
import Simly.syntaxtree.Require;
import Simly.syntaxtree.Start;
import Simly.syntaxtree.StatementExpression;
import Simly.syntaxtree.UnaryExpression;
import Simly.syntaxtree.UnaryRelational;
import Simly.syntaxtree.VariableAssign;
import Simly.syntaxtree.VariableDeclaration;
import Simly.syntaxtree.VariableName;
import Simly.syntaxtree.WhileExpression;

public interface Interpret {
	   /**
	    * f0 -> ( Require() "." )+
	    * f1 -> ( StatementExpression() )*
	 * @throws Exception 
	    */
	   public Object visit(Start node) throws Exception;

	   /**
	    * f0 -> "require"
	    * f1 -> ( <IDENTIFIER> )+
	    */
	   public Object visit(Require node, SimlyScope scope, Object ... objects);

	   /**
	    * f0 -> AdditiveExpression()
	 * @throws Exception 
	    */
	   public Object visit(MathExpression node, SimlyScope scope, Object ... objects) throws Exception;

	   /**
	    * f0 -> MultiplicativeExpression()
	    * f1 -> ( ( "+" | "-" ) MultiplicativeExpression() )*
	 * @throws Exception 
	    */
	   public Object visit(AdditiveExpression node, SimlyScope scope, Object ... objects) throws Exception;

	   /**
	    * f0 -> UnaryExpression()
	    * f1 -> ( ( "*" | "/" | "%" ) UnaryExpression() )*
	 * @throws Exception 
	    */
	   public Object visit(MultiplicativeExpression node, SimlyScope scope, Object ... objects) throws Exception;

	   /**
	    * f0 -> "(" MathExpression() ")"
	    *       | <INTEGER_LITERAL>
	    *       | VariableName()
	 * @throws Exception 
	    */
	   public Object visit(UnaryExpression node, SimlyScope scope, Object ... objects) throws Exception;

	   /**
	    * f0 -> RelationalEqualityExpression()
	 * @throws Exception 
	    */
	   public Object visit(RelationalExprssion node, SimlyScope scope, Object ... objects) throws Exception;

	   /**
	    * f0 -> RelationalGreaterExpression()
	    * f1 -> ( ( "==" | "!=" ) RelationalGreaterExpression() )*
	 * @throws Exception 
	    */
	   public Object visit(RelationalEqualityExpression node, SimlyScope scope, Object ... objects) throws Exception;

	   /**
	    * f0 -> RelationalLessExpression()
	    * f1 -> ( ( ">" | ">=" ) RelationalLessExpression() )*
	 * @throws Exception 
	    */
	   public Object visit(RelationalGreaterExpression node, SimlyScope scope, Object ... objects) throws Exception;

	   /**
	    * f0 -> UnaryRelational()
	    * f1 -> ( ( "<" | "<=" ) UnaryRelational() )*
	 * @throws Exception 
	    */
	   public Object visit(RelationalLessExpression node, SimlyScope scope, Object ... objects) throws Exception;

	   /**
	    * f0 -> MathExpression()
	 * @throws Exception 
	    */
	   public Object visit(UnaryRelational node, SimlyScope scope, Object ... objects) throws Exception;

	   /**
	    * f0 -> "if"
	    * f1 -> RelationalExprssion()
	    * f2 -> "do"
	    * f3 -> ( StatementExpression() )*
	    * f4 -> "stop"
	 * @throws Exception 
	    */
	   public Object visit(IfExpression node, SimlyScope scope, Object ... objects) throws Exception;

	   /**
	    * f0 -> "while"
	    * f1 -> RelationalExprssion()
	    * f2 -> "do"
	    * f3 -> ( StatementExpression() )*
	    * f4 -> "stop"
	 * @throws Exception 
	    */
	   public Object visit(WhileExpression node, SimlyScope scope, Object ... objects) throws Exception;

	   /**
	    * f0 -> "def"
	    * f1 -> VariableName()
	    * f2 -> "="
	    * f3 -> MathExpression()
	    * f4 -> "."
	 * @throws Exception 
	    */
	   public Object visit(VariableDeclaration node, SimlyScope scope, Object ... objects) throws Exception;

	   /**
	    * f0 -> VariableName()
	    * f1 -> "="
	    * f2 -> MathExpression()
	    * f3 -> "."
	 * @throws Exception 
	    */
	   public Object visit(VariableAssign node, SimlyScope scope, Object ... objects) throws Exception;

	   /**
	    * f0 -> <IDENTIFIER>
	    */
	   public Object visit(VariableName node, SimlyScope scope, Object ... objects);

	   /**
	    * f0 -> <IDENTIFIER>
	    * f1 -> ( ":" <IDENTIFIER> )+
	    * f2 -> "("
	    * f3 -> MathExpression()
	    * f4 -> ( "," MathExpression() )*
	    * f5 -> ")"
	    * f6 -> "."
	 * @throws Exception 
	    */
	   public Object visit(JavaStaticMethods node, SimlyScope scope, Object ... objects) throws Exception;

	   /**
	    * f0 -> VariableDeclaration()
	    *       | VariableAssign()
	    *       | JavaStaticMethods()
	    *       | IfExpression()
	    *       | WhileExpression()
	 * @throws Exception 
	    */
	   public Object visit(StatementExpression node, SimlyScope scope, Object ... objects) throws Exception;
}
