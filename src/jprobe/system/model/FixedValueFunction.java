package jprobe.system.model;

import jprobe.framework.model.Function;
import jprobe.framework.model.InvocationException;
import jprobe.framework.model.Parameter;
import jprobe.framework.model.TypeMismatchException;

public class FixedValueFunction<R> extends Function<R>{
	private static final long serialVersionUID = 1L;
	
	private static final Parameter<?>[] EMPTY_ARRAY = new Parameter<?>[]{};
	
	private final Class<? extends R> m_Clazz;
	private final R m_Value;
	
	@SuppressWarnings("unchecked")
	public FixedValueFunction(R val){
		m_Value = val;
		m_Clazz = (Class<? extends R>) val.getClass();
	}
	
	public FixedValueFunction(Class<? extends R> clazz){
		m_Value = null;
		m_Clazz = clazz;
	}
	
	@Override
	public Parameter<?>[] getParameters() {
		return EMPTY_ARRAY;
	}

	@Override
	public Class<? extends R> getReturnType() {
		return m_Clazz;
	}

	@Override
	public <T> Function<R> putArgument(int paramIndex, Function<T> arg)
			throws TypeMismatchException {
		throw new IndexOutOfBoundsException();
	}
	
	@Override
	public <T> Function<R> putArgument(int paramIndex, T arg)
			throws TypeMismatchException {
		throw new IndexOutOfBoundsException();
	}

	@Override
	public R invoke(Function<?>... args) throws IllegalArgumentException,
			TypeMismatchException, InvocationException {
		
		Parameters.checkArguments(this, EMPTY_ARRAY, args);
		return m_Value;
	}


}
