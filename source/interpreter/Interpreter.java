
package Simly.interpreter;

import java.util.Enumeration;
import java.util.LinkedList;

import Simly.core.SimlyScope;
import Simly.core.SimlyValue;
import Simly.core.SimlyVariable;
import Simly.reflect.SimlyReflection;
import Simly.syntaxtree.AdditiveExpression;
import Simly.syntaxtree.IfExpression;
import Simly.syntaxtree.JavaStaticMethods;
import Simly.syntaxtree.MathExpression;
import Simly.syntaxtree.MultiplicativeExpression;
import Simly.syntaxtree.NodeChoice;
import Simly.syntaxtree.NodeSequence;
import Simly.syntaxtree.NodeToken;
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

/**
 * Simly Interpreter
 * 
 * @author Adrabi Abderrahim
 *
 */
public class Interpreter implements Interpret {

	/**
	 * here is start point for interpreting Simly
	 * @throws Exception 
	 */
	public Object visit(Start node) throws Exception {
		
		/*
		 * first setup of imported packages and add it to SimlyReflection
		 * class for using it with reflection full class identifier like :
		 * 		"java.lang.System"
		 * the elements f0 ... fn has generated automatically by JTB =)
		 */
		
		Enumeration importedPackagesEnum = node.f0.elements();
		while( importedPackagesEnum.hasMoreElements() )
		{
			// adding required packages
			NodeSequence ns = (NodeSequence) importedPackagesEnum.nextElement();
			SimlyReflection.pushPackage( this.visit( (Require) ns.elementAt( 0 ) , null).toString() );
		}
		
		/*
		 *~ Now! press start to play, maybe if you not lost all your credits
		 *~ in article and configurations, you lost them all here =)
		 *
		 * okay, after importation we start interpreting a source code
		 * 
		 * testing if exists a code after "require
		 * require java lang.
		 * System:ou:println( 1 ). "first statement
		 * System:ou:println( 2 ). "second statement
		 */
		
		 if( node.f1.size() > 0 )
		 {
			//~ creating of parent scope
			 SimlyScope parent = new SimlyScope( null );
			 Enumeration statement = node.f1.elements();
			 while( statement.hasMoreElements() )
			 {
				 this.visit( (StatementExpression)statement.nextElement() , parent); 
			 }
		 }
		
		 return null;
	}

	/**
	 * this method allow retrieving and transforming required packages
	 * to Java format 
	 */
	public Object visit(Require node, SimlyScope scope, Object... objects) {
		/*
		 * before begin: 
		 * 		notice to all reserved keywords has ignored !
		 * 		f0 : content a keyword we ignore it
		 */
		StringBuilder builder = new StringBuilder();
		Enumeration element = node.f1.elements();
		while( element.hasMoreElements() )
		{
			builder.append( element.nextElement() ) ;
			if( element.hasMoreElements() )
			{
				builder.append( "." );
			}
		}
		return builder;
	}

	/**
	 * integers operation
	 */
	public Object visit(MathExpression node, SimlyScope scope, Object... objects) throws Exception {
		return this.visit(node.f0, scope, objects);
	}

	/**
	 * additive operations
	 */
	public Object visit(AdditiveExpression node, SimlyScope scope,
			Object... objects) throws Exception {
		
		SimlyValue value = (SimlyValue)this.visit(node.f0, scope, objects);
		
		Enumeration e = node.f1.elements();
		while( e.hasMoreElements() )
		{
			NodeSequence ns = (NodeSequence) e.nextElement();
			NodeChoice nc = (NodeChoice) ns.elementAt( 0 );
			
			SimlyValue tmp = (SimlyValue) this.visit( (MultiplicativeExpression) ns.elementAt(1) , scope, objects);
			if( nc.choice.toString().equals("+") )
			{
				tmp.setValue( value.getValue() + tmp.getValue() );
				return tmp;
			}
			else if( nc.choice.toString().equals("-") )
			{
				tmp.setValue( value.getValue() - tmp.getValue() );
				return tmp;
			}
		}
		return value;
	}

	/**
	 * multiplicative operation
	 */
	public Object visit(MultiplicativeExpression node, SimlyScope scope,
			Object... objects) throws Exception {
		
		SimlyValue value = (SimlyValue)this.visit(node.f0, scope, objects);
		
		Enumeration e = node.f1.elements();
		while( e.hasMoreElements() )
		{
			NodeSequence ns = (NodeSequence) e.nextElement();
			NodeChoice nc = (NodeChoice) ns.elementAt( 0 );
			SimlyValue tmp = (SimlyValue) this.visit( (UnaryExpression) ns.elementAt(1) , scope, objects);
			
			if( nc.choice.toString().equals("*") )
			{
				tmp.setValue( value.getValue() * tmp.getValue() );
				return tmp;
			}
			else if( nc.choice.toString().equals("/") )
			{
				tmp.setValue( value.getValue() / tmp.getValue() );
				return tmp;
			}
			else if( nc.choice.toString().equals("%") )
			{
				tmp.setValue( value.getValue() % tmp.getValue() );
				return tmp;
			}
		}
		return value;
	}

	/**
	 * getting values
	 */
	public Object visit(UnaryExpression node, SimlyScope scope,
			Object... objects) throws Exception {
		/*
		 * We are allowed just operation in IN (integers) ;)
		 */
		if( node.f0.choice instanceof NodeToken )
		{
			SimlyValue value = new SimlyValue();
			value.setValue( Long.parseLong( node.f0.choice.toString() ) );
			value.setType( long.class ); // here ;)
			return value;
		}
		else if( node.f0.choice instanceof VariableName )
		{
			String var = this.visit( (VariableName) node.f0.choice, scope, objects)
						.toString();
			if( scope.existsChild( var ) )
			{
				return scope.child( var ).getVariableValue();
			}
			else
			{
				throw new Exception("Sorry, but the variable " + var + " not exists =)!" );
			}
		}
		else if( node.f0.choice instanceof NodeSequence )
		{
			NodeSequence ns = (NodeSequence) node.f0.choice ;
			return this.visit( (MathExpression) ns.elementAt(1) , scope, objects) ;
		}
		return null;
	}

	/**
	 * relational testing ...
	 */
	public Object visit(RelationalExprssion node, SimlyScope scope,
			Object... objects) throws Exception {
		
		return this.visit(node.f0, scope, objects);
	}

	/**
	 * testing for equality "1 == 1"
	 */
	public Object visit(RelationalEqualityExpression node, SimlyScope scope,
			Object... objects) throws Exception {
		
		Object obj = this.visit(node.f0, scope, objects);
		if( node.f1.node != null && obj instanceof Long)
		{
			NodeSequence ns = (NodeSequence) node.f1.node;
			Object tmp = this.visit( (RelationalGreaterExpression) ns.elementAt(1), scope, objects);
			if( tmp instanceof Long)
			{
				NodeChoice nc = (NodeChoice) ns.elementAt(0) ;
				if( nc.choice.toString().equals("==") )
				{
					obj = Long.parseLong( obj.toString() ) ==  Long.parseLong( tmp.toString() );
				}
				else if( nc.choice.toString().equals("!=") )
				{
					obj = Long.parseLong( obj.toString() ) !=  Long.parseLong( tmp.toString() );
				}
			}
		}
		return obj;
	}

	/**
	 * testing for greater value "1 > 0"
	 */
	public Object visit(RelationalGreaterExpression node, SimlyScope scope,
			Object... objects) throws Exception {
		
		Object obj = this.visit(node.f0, scope, objects);
		if( node.f1.node != null && obj instanceof Long)
		{
			NodeSequence ns = (NodeSequence) node.f1.node;
			Object tmp = this.visit( (RelationalLessExpression) ns.elementAt(1), scope, objects);
			if( tmp instanceof Long)
			{
				NodeChoice nc = (NodeChoice) ns.elementAt(0) ;
				if( nc.choice.toString().equals(">") )
				{
					obj = Long.parseLong( obj.toString() ) >  Long.parseLong( tmp.toString() );
				}
				else if( nc.choice.toString().equals(">=") )
				{
					obj = Long.parseLong( obj.toString() ) >=  Long.parseLong( tmp.toString() );
				}
			}
		}
		return obj;
	}
	
	/**
	 * test for less value "0 < 1"
	 */
	public Object visit(RelationalLessExpression node, SimlyScope scope,
			Object... objects) throws Exception {
		Object obj = this.visit(node.f0, scope, objects);
		if( node.f1.node != null && obj instanceof Long)
		{
			NodeSequence ns = (NodeSequence) node.f1.node;
			Object tmp = this.visit( (UnaryRelational) ns.elementAt(1), scope, objects);
			if( tmp instanceof Long)
			{
				NodeChoice nc = (NodeChoice) ns.elementAt(0) ;
				if( nc.choice.toString().equals("<") )
				{
					obj = Long.parseLong( obj.toString() ) <  Long.parseLong( tmp.toString() );
				}
				else if( nc.choice.toString().equals("<=") )
				{
					obj = Long.parseLong( obj.toString() ) <=  Long.parseLong( tmp.toString() );
				}
			}
		}
		return obj;
	}

	/**
	 * method for getting a value to be tested
	 */
	public Object visit(UnaryRelational node, SimlyScope scope,
			Object... objects) throws Exception {
		
		return ((SimlyValue)this.visit(node.f0, scope, objects)).getValue() ;
	}

	/**
	 * Simly "if" condition
	 */
	public Object visit(IfExpression node, SimlyScope scope, Object... objects) throws Exception {
		
		/*
		 * like variable declaration we ignore all keywords, for more information
		 * see interface Interpret.java or JTB grammar
		 */
		SimlyScope ifScope = new SimlyScope( scope );
		if( new Boolean(this.visit(node.f1, scope, objects).toString()) )
		{
			Enumeration e = node.f3.elements();
			while( e.hasMoreElements() )
			{
				this.visit( (StatementExpression)e.nextElement() , ifScope, objects);
			}
		}
		return null;
	}

	/**
	 * Simly "while" expression
	 */
	public Object visit(WhileExpression node, SimlyScope scope,
			Object... objects) throws Exception {
		/*
		 * like variable declaration we ignore all keywords, for more information
		 * see interface Interpret.java or JTB grammar
		 */
		SimlyScope whileScope = new SimlyScope( scope );
		while( new Boolean(this.visit(node.f1, scope, objects).toString()) )
		{
			Enumeration e = node.f3.elements();
			while( e.hasMoreElements() )
			{
				this.visit( (StatementExpression)e.nextElement() , whileScope, objects);
			}
		}
		return null;
	}

	/**
	 * variable declaration and assignment
	 * @throws Exception 
	 */
	public Object visit(VariableDeclaration node, SimlyScope scope,
			Object... objects) throws Exception {
		/*
		 * we ignore "def", "=" and "." keywords
		 */
		
		SimlyVariable var = new SimlyVariable();
		var.setVariableName( this.visit( node.f1 , scope, objects).toString() ) ;
		var.setVariableValue( (SimlyValue) this.visit(node.f3, scope, objects) );
		
		/*
		 * we add a variable to current scope for variable life cycle
		 * and visibility.
		 */
		scope.pushChild( var.getVariableName() , var );
		return null;
	}

	/**
	 * assigning a new value to variable
	 * @throws Exception 
	 */
	public Object visit(VariableAssign node, SimlyScope scope, Object... objects) throws Exception {
		String name = this.visit(node.f0, scope, objects).toString() ;
		if( scope.existsChild( name ) )
		{
			SimlyVariable var = (SimlyVariable) scope.child( name ) ;
			var.setVariableValue( (SimlyValue) this.visit(node.f2, scope, objects) );
		}
		return null;
	}

	/**
	 * getting a variable name
	 */
	public Object visit(VariableName node, SimlyScope scope, Object... objects) {
		return node.f0.tokenImage;
	}

	/**
	 * method for executing a static Java methods
	 * @throws Exception 
	 */
	public Object visit(JavaStaticMethods node, SimlyScope scope,
			Object... objects) throws Exception {
		
		/*
		 * Okay, firstly we need to test existence of class and fields or method
		 * after, we get a value for arguments, finally we invoke a static Java Method 
		 */
		
		//f0 is class name 
		String identifier = SimlyReflection.fullIdentifier( node.f0.tokenImage ) ;
		if( identifier != null )
		{
			// making a class object
			Object currentObject = SimlyReflection.makeObject ( identifier );
			if( currentObject != null ){
				Enumeration e = node.f1.elements();
				//~ getting a last field object
				while( e.hasMoreElements() )
				{
					NodeSequence ns = (NodeSequence) e.nextElement(); 
					if( SimlyReflection.existsField( currentObject , ns.elementAt( 1 ).toString()  ) )
					{
						currentObject = SimlyReflection.getFieldObject( currentObject , ns.elementAt( 1 ).toString() );
					}
					else
					{
						LinkedList<SimlyValue> params = new LinkedList<SimlyValue>();
						params.add( (SimlyValue) this.visit(node.f3, scope, objects) );
						Enumeration eVal = node.f4.elements();
						while( eVal.hasMoreElements() )
						{
							NodeSequence nsVal = (NodeSequence) eVal.nextElement();
							params.add( (SimlyValue) this.visit( (MathExpression) nsVal.elementAt(1) , scope, objects) );
						}
						
						//~ test and invoking
						if( SimlyReflection.existsSubroutine( currentObject , ns.elementAt( 1 ).toString() , params.toArray( new SimlyValue[]{} )) )
						{
							return SimlyReflection.invokeStaticSubroutine( currentObject , ns.elementAt( 1 ).toString() , params.toArray( new SimlyValue[]{} )) ;
						}
						break;
					}
				}
			}
		}
		
		return null;
	}

	/**
	 * Statement is a core of interpreting Simly source code
	 * @throws Exception 
	 */
	public Object visit(StatementExpression node, SimlyScope scope,
			Object... objects) throws Exception {
		/*
		 * Statement expression do *NOTHING* =) just redirecting
		 * to right statement!
		 * >> redirecting? what you mean here man, we're not in Web or JavaScript ?
		 * yes, redirecting because here we don't allowed to execute any statement
		 * just returning a result from right statement =)!
		 * 
		 * NOTE::	you're free to do what you like here just for this article we're
		 * 			not allowed to executing any statement for creating an easy and
		 * 			clear code source.
		 * 
		 * and remember in JTB file we have
		 *  
		 		VariableDeclaration()
				| LOOKAHEAD(2) VariableAssign()
				| JavaStaticMethods()
				| IfExpression()	
				| WhileExpression()
		 *
		 *
		 */
		
		if( node.f0.choice instanceof VariableDeclaration )
		{
			return this.visit( (VariableDeclaration) node.f0.choice, scope, objects);
		}
		else if( node.f0.choice instanceof VariableAssign )
		{
			return this.visit( (VariableAssign) node.f0.choice, scope, objects);
		}
		else if( node.f0.choice instanceof JavaStaticMethods )
		{
			return this.visit( (JavaStaticMethods) node.f0.choice, scope, objects);
		}
		else if( node.f0.choice instanceof IfExpression )
		{
			return this.visit( (IfExpression) node.f0.choice, scope, objects);
		}
		else if( node.f0.choice instanceof WhileExpression )
		{
			return this.visit( (WhileExpression) node.f0.choice, scope, objects);
		}
		return null;
	}

}
