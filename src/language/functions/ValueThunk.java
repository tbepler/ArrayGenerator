package language.functions;

public class ValueThunk extends Thunk{
	
	private final Object val;
	
	public ValueThunk(Object val){
		this.val = val;
	}
	
	@Override
	protected Object eval() {
		return val;
	}

}
