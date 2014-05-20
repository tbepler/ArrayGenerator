package plugins.testDataAndFunction;

import jprobe.services.data.IntegerField;

public class IntField extends IntegerField{
	private static final long serialVersionUID = 1L;

	public static final String TOOLTIP = "An integer field with no bounds";
	
	private int value;
	
	public IntField(int value){
		this.value = value;
	}
	
	@Override
	public String getTooltip() {
		return TOOLTIP;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public boolean isValid(int value) {
		return true;
	}

	@Override
	public int getMin() {
		return Integer.MIN_VALUE; 
	}

	@Override
	public int getMax() {
		return Integer.MAX_VALUE;
	}

	@Override
	public int getIncrement() {
		return 1;
	}

	@Override
	public IntegerField parseInt(int value) throws Exception{
		return new IntField(value);
	}

}
