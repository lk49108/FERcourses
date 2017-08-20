package hr.fer.zemris.java.tecaj.hw5.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class DataBaseTests {

	@Test (expected = IllegalArgumentException.class)
	public void testIllegalArgumentsInConditionalExpressionConstructor(){
		ConditionalExpression expr = new ConditionalExpression(new LastNameFieldGetter(),"Bos*", null);
	}
	
	@Test
	public void testLikeComparisionOperator(){
		StudentRecord record = new StudentRecord("0036491083", "Kokot", "Leonardo", 5);
		ConditionalExpression expr = new ConditionalExpression(new FirstNameFieldGetter(),"Le*do", new LIKEComparision());
		assertEquals(true, expr.getComparisionOperator().satisfied(expr.getFieldGetter().get(record), expr.getStringLiteral()));
	}
	
	@Test 
	public void testNotEqualComparisionOperator(){
		StudentRecord record = new StudentRecord("0036491083", "Kokot", "Leonardo", 5);
		ConditionalExpression expr = new ConditionalExpression(new FirstNameFieldGetter(),"Leonardo", new NotEqualComparision());
		assertEquals(false, expr.getComparisionOperator().satisfied(expr.getFieldGetter().get(record), expr.getStringLiteral()));	}
	
	@Test
	public void testGreaterComparisionOperator(){
		StudentRecord record = new StudentRecord("0036491083", "Kokot", "Leonardo", 5);
		ConditionalExpression expr = new ConditionalExpression(new JMBAGFieldGetter(),"0036491003", new GreaterComparision());
		assertEquals(true, expr.getComparisionOperator().satisfied(expr.getFieldGetter().get(record), expr.getStringLiteral()));
	}
	
	@Test
	public void testLessComparisionOperator(){
		StudentRecord record = new StudentRecord("0036491083", "Kokot", "Leonardo", 5);
		ConditionalExpression expr = new ConditionalExpression(new LastNameFieldGetter(),"Kokoto", new LessComparision());
		assertEquals(true, expr.getComparisionOperator().satisfied(expr.getFieldGetter().get(record), expr.getStringLiteral()));
	}
	
	@Test
	public void testGreaterOrEqualComparisionOperator(){
		StudentRecord record = new StudentRecord("0036491083", "Kokot", "Leonardo", 5);
		ConditionalExpression expr = new ConditionalExpression(new JMBAGFieldGetter(),"0036491083", new GreaterOrEqualComparision());
		ConditionalExpression expr2 = new ConditionalExpression(new JMBAGFieldGetter(),"0036491082", new GreaterOrEqualComparision());
		assertEquals(true, expr2.getComparisionOperator().satisfied(expr2.getFieldGetter().get(record), expr2.getStringLiteral()));
		assertEquals(true, expr.getComparisionOperator().satisfied(expr.getFieldGetter().get(record), expr.getStringLiteral()));
	}
	
	@Test
	public void testLessOrEqualComparisionOperator(){
		StudentRecord record = new StudentRecord("0036491083", "Kokot", "Leonardo", 5);
		ConditionalExpression expr = new ConditionalExpression(new FirstNameFieldGetter(),"Leonardo", new LessOrEqualComparision());
		ConditionalExpression expr2 = new ConditionalExpression(new FirstNameFieldGetter(),"Leonardoo", new LessOrEqualComparision());
		assertEquals(true, expr2.getComparisionOperator().satisfied(expr2.getFieldGetter().get(record), expr2.getStringLiteral()));
		assertEquals(true, expr.getComparisionOperator().satisfied(expr.getFieldGetter().get(record), expr.getStringLiteral()));
	}
	
	@Test
	public void testEqualComparisionOperator(){
		StudentRecord record = new StudentRecord("0036491083", "Kokot Zebra", "Leonardo", 5);
		ConditionalExpression expr = new ConditionalExpression(new LastNameFieldGetter(),"Kokot", new EqualComparision());
		ConditionalExpression expr2 = new ConditionalExpression(new LastNameFieldGetter(),"Zebra", new EqualComparision());
		assertEquals(true, expr2.getComparisionOperator().satisfied(expr2.getFieldGetter().get(record), expr2.getStringLiteral()));
		assertEquals(true, expr.getComparisionOperator().satisfied(expr.getFieldGetter().get(record), expr.getStringLiteral()));
	}
	 
	@Test
	public void testStudentRecordGetters(){
		StudentRecord record = new StudentRecord("0036491083", "Kokot", "Leonardo", 5);
		assertEquals("0036491083", record.getJMBAG());
		assertEquals("Kokot", record.getLastName());
		assertEquals("Leonardo", record.getFirstName());
		assertEquals(5, record.getFinalGrade());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testLexerAndParserException(){
		LexerAndParser parser= new LexerAndParser("LASTNAME LIKE \"*hejj*\"");
		parser.parse();
	}
	
	@Test
	public void testQueryFilter(){
		QueryFilter filter = new QueryFilter("LASTNAME LIKE \"*jan\"");
		StudentRecord record = new StudentRecord("0037373737", "Šebijan", "Netko", 5);
		assertEquals(true, filter.accepts(record));
	}
	
	@Test
	public void testQueryFilter2(){
		QueryFilter filter = new QueryFilter("LASTNAME < \":/\"");
		StudentRecord record = new StudentRecord("0037373737", "://", "Netko", 5);
		assertEquals(false, filter.accepts(record));
	}
	
	@Test
	public void testQueryFilter3(){
		QueryFilter filter = new QueryFilter("firstname < \"Sine\"");
		StudentRecord record = new StudentRecord("0037373737", "://", "Netko", 5);
		assertEquals(true, filter.accepts(record));
	}
}
