package jprobe.framework.model.types;

import java.util.Arrays;
import java.util.Deque;

import jprobe.framework.model.tuple.Tuple;

public final class TupleClass implements Type<Tuple>{
	private static final long serialVersionUID = 1L;
	
	private final Type<?>[] m_Types;
	private final int m_Hash;
	
	public TupleClass(Type<?> ... types){
		m_Types = types.clone();
		m_Hash = this.computeHash();
	}
	
	public TupleClass(Object ... objs){
		m_Types = new Type[objs.length];
		for(int i=0; i<m_Types.length; ++i){
			m_Types[i] = Types.typeOf(objs[i]);
		}
		m_Hash = this.computeHash();
	}
	
	private int computeHash(){
		return Arrays.hashCode(m_Types);
	}
	
	public final int size(){
		return m_Types.length;
	}
	
	public final Type<?> get(int index){
		return m_Types[index];
	}
	
	public final Type<?>[] toArray(){
		return m_Types.clone();
	}
	
	@Override
	public Tuple extract(Deque<Object> objs) {
		if(objs == null || objs.size() == 0) return null;
		Object obj = objs.poll();
		try{
			if(this.isInstance(obj)){
				return this.cast(obj);
			}
		}catch(RuntimeException e){
			objs.push(obj);
			throw e;
		}

		objs.push(obj);
		//try boxing the given types into this tuple type
		return new Tuple(Types.extract(m_Types, objs));

	}
	
	@Override
	public boolean canExtract(Deque<Object> objs) {
		Deque<Type<?>> types = Types.typesOf(objs);
		return this.isExtractableFrom(types);
	}
	
	@Override
	public final boolean isExtractableFrom(Deque<Type<?>> types){
		if(types == null || types.size() == 0) return false;
		Type<?> head = types.poll();
		if(this.isExtractableFrom(head)){
			return true;
		}
		types.push(head);
		//test whether this can be boxed from the given types
		return Types.isExctractableFrom(m_Types, types);
	}
	
	public final boolean isExtractableFrom(Type<?> type){
		if(this.isAssignableFrom(type)){
			return true;
		}
		return this.canUnwrap(type);
	}
	
	private boolean canUnwrap(Type<?> type){
		if(type instanceof Signature){
			Signature<?> sign = (Signature<?>) type;
			return sign.numParameters() == 0 && this.isExtractableFrom(sign.getReturnType());
		}
		if(type instanceof TupleClass){
			TupleClass clazz = (TupleClass) type;
			
		}
	}
	
	@Override
	public Tuple cast(Object obj){
		if(obj == null) return null;
		Type<?> type = Types.typeOf(obj);
		if(this.isAssignableFrom(type)){
			return (Tuple) obj;
		}
		throw new ClassCastException("Object: "+obj+" of type: "+type+" cannot be cast to type: "+this);
	}
	
	@Override
	public final boolean isAssignableFrom(Type<?> type){
		if(type == null) return false;
		if(type == this) return true;
		if(type instanceof TupleClass){
			TupleClass clazz = (TupleClass) type;
			return Types.isAssignableFrom(m_Types, clazz.m_Types);
		}
		return false;
	}
	
	@Override
	public final boolean isInstance(Object o){
		Type<?> type = Types.typeOf(o);
		return this.isAssignableFrom(type);
	}
	
	@Override
	public int hashCode(){
		return m_Hash;
	}
	
	@Override
	public final boolean equals(Object o){
		if(o == null) return false;
		if(o == this) return true;
		if(o instanceof TupleClass){
			TupleClass other = (TupleClass) o;
			if(m_Types.length == other.m_Types.length){
				for(int i=0; i<m_Types.length; ++i){
					if(!equals(m_Types[i], other.m_Types[i])){
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}
	
	private static boolean equals(Object a, Object b){
		if(a == b) return true;
		if(a == null || b == null) return false;
		return a.equals(b);
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		boolean first = true;
		for(Type<?> type : m_Types){
			if(first){
				builder.append(type);
				first = false;
			}else{
				builder.append(", ").append(type);
			}
		}
		builder.append(")");
		return builder.toString();
	}

	
	
	
}
