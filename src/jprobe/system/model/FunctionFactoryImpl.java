package jprobe.system.model;

import jprobe.framework.model.Function;
import jprobe.framework.model.Procedure;
import jprobe.framework.model.TypeMismatchException;

public class FunctionFactoryImpl implements FunctionFactory{

	@Override
	public <T> Function<T> newFunction(Procedure<T> procedure) {
		return new RootFunction<T>(this, procedure);
	}

	@Override
	public <T> Function<T> newFixedValueFunction(T value) {
		return new FixedValueFunction<T>(value);
	}

	@Override
	public <T> Function<T> newNullValueFunction(Class<T> clazz) {
		return new FixedValueFunction<T>(clazz);
	}

	@Override
	public <T, U> Function<T> newFunction(Function<T> parent, int paramIndex,
			Function<U> arg) throws TypeMismatchException {
		Parameters.checkType(parent.getParameters()[paramIndex], arg);
		return new ChildFunction<T>(this, parent, paramIndex, arg);
	}

	@Override
	public <T, U> Function<T> newFunction(Function<T> parent, int paramIndex,
			U arg) throws TypeMismatchException {
		Parameters.checkType(parent.getParameters()[paramIndex], arg);
		return new ChildFunction<T>(this, parent, paramIndex, this.newFixedValueFunction(arg));
	}



}
