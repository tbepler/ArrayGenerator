package jprobe.framework.model.types.test;

import jprobe.framework.model.types.Type;
import jprobe.framework.model.types.Typed;
import jprobe.framework.model.types.Types;

public class TestTypes extends junit.framework.TestCase{
	
	private static class TestTyped implements Typed<TestTyped>{
		
		private final Type<? extends TestTyped> type = Types.getType(TestTyped.class);
		
		@Override
		public Type<? extends TestTyped> getType() {
			return type;
		}
		
	}
	
	public void testTypeOf(){
		
		TestTyped test = new TestTyped();
		Object obj = test;
		
		Type<?> type = Types.typeOf(obj);
		
		assertEquals(test.getType(), type);
		
		
	}
	
}