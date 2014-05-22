package jprobe.services.function;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JTextField;

public abstract class StringArgument<P> extends AbstractArgument<P> implements ActionListener{
	
	public static final String PROTOTYPE_TEXT = "some string here";
	
	private final JTextField m_TextField;

	protected StringArgument(String name, String tooltip, String category, boolean optional, String startValue) {
		super(name, tooltip, category, optional);
		m_TextField = new JTextField(PROTOTYPE_TEXT);
		Dimension size = m_TextField.getPreferredSize();
		m_TextField.setText(startValue);
		m_TextField.setPreferredSize(size);
		m_TextField.setMinimumSize(size);
		m_TextField.addActionListener(this);
	}
	
	protected abstract boolean isValid(String s);
	protected abstract void process(P params, String s);

	@Override
	public boolean isValid() {
		return this.isValid(m_TextField.getText());
	}

	@Override
	public JComponent getComponent() {
		return m_TextField;
	}

	@Override
	public void process(P params) {
		process(params, m_TextField.getText());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == m_TextField){
			this.notifyListeners();
		}
	}

}