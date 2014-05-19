package jprobe.services.function;

import javax.swing.JTextField;

public abstract class DoubleArgument<P> extends SpinnerArgument<P, Double>{
	
	protected static class DoubleModel implements SpinnerArgument.Spinner<Double>{
		
		private final double m_Min;
		private final double m_Max;
		private final double m_Increment;
		
		protected DoubleModel(double min, double max, double increment){
			m_Min = min;
			m_Max = max;
			m_Increment = increment;
		}
		
		private boolean legal(double val){
			return val >= m_Min && val <= m_Max;
		}
		
		@Override
		public Double next(Double cur) {
			double next = cur + m_Increment;
			if(this.legal(next)){
				return next;
			}else{
				return cur;
			}
		}

		@Override
		public Double prev(Double cur) {
			double prev = cur - m_Increment;
			if(this.legal(prev)){
				return prev;
			}else{
				return cur;
			}
		}
		
	}
	
	private final double m_Min;
	private final double m_Max;
	
	protected DoubleArgument(
			String name,
			String tooltip,
			String category,
			boolean optional,
			double startValue,
			double min,
			double max,
			double increment) {
		
		super(
				name,
				tooltip,
				category,
				optional,
				startValue,
				new DoubleModel(min, max, increment),
				JTextField.RIGHT
				);
		
		m_Min = min;
		m_Max = max;
	}

	@Override
	protected boolean isValid(Double value) {
		return value >= m_Min && value <= m_Max;
	}

}
