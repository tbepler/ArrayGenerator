package jprobe.services.function.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collection;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JPanel;

import util.observer.Observer;
import util.observer.Subject;
import jprobe.services.data.Data;
import jprobe.system.Constants;

public class DataSelectionPanel<D extends Data> extends JPanel implements ItemListener, ActionListener, Subject<D>{
	private static final long serialVersionUID = 1L;
	
	public static interface OnClose{
		public void close();
	}
	
	private final Collection<Observer<D>> m_Obs = new HashSet<Observer<D>>();
	
	private final DataComboBox<D> m_DataBox;
	private final JButton m_CloseButton;
	private final boolean m_Optional;
	private OnClose m_CloseAction = new OnClose(){

		@Override
		public void close() {
			//do nothing
		}
		
	};
	
	public DataSelectionPanel(boolean optional){
		super(new GridBagLayout());
		m_Optional = optional;
		m_DataBox = this.createdDataComboBox();
		m_DataBox.addItemListener(this);
		this.add(m_DataBox, this.createDataComboBoxConstraints());
		m_CloseButton = this.createCloseButton();
		m_CloseButton.addActionListener(this);
		m_CloseButton.setEnabled(optional);
		this.add(m_CloseButton, this.createCloseButtonConstraints());
		if(optional){
			m_DataBox.addData(null, "");
		}
	}
	
	protected DataComboBox<D> createdDataComboBox(){
		return new DataComboBox<D>();
	}
	
	protected GridBagConstraints createDataComboBoxConstraints(){
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.insets = new Insets(0,0,2,2);
		return gbc;
	}
	
	protected JButton createCloseButton(){
		return new IconButton(Constants.X_ICON, Constants.X_HIGHLIGHTED_ICON, Constants.X_CLICKED_ICON);
	}
	
	protected GridBagConstraints createCloseButtonConstraints(){
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0;
		gbc.insets = new Insets(0,2,2,0);
		return gbc;
	}
	
	@Override
	public void setEnabled(boolean enabled){
		m_DataBox.setEnabled(enabled);
		m_CloseButton.setEnabled(enabled && m_Optional);
		super.setEnabled(enabled);
	}
	
	public void setCloseAction(OnClose closeAction){
		m_CloseAction = closeAction;
	}
	
	public void addData(D d, String name){
		m_DataBox.addData(d, name);
	}
	
	public void removeData(D d){
		m_DataBox.removeData(d);
	}
	
	public void renameData(D data, String newName){
		m_DataBox.rename(data, newName);
	}
	
	public boolean isOptional(){
		return m_CloseButton.isEnabled();
	}
	
	public void setOptional(boolean optional){
		if(this.isOptional() != optional){
			if(optional){
				m_DataBox.addData(null, "");
			}else{
				m_DataBox.removeData(null);
			}
			m_CloseButton.setEnabled(optional);
		}
	}
	
	public D getSelectedData(){
		return m_DataBox.getSelectedData();
	}
	
	protected void close(){
		m_CloseAction.close();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource() == m_DataBox){
			//System.out.println("Item changed");
			D selected = m_DataBox.getSelectedData();
			this.notifyObservers(selected);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == m_CloseButton){
			this.close();
		}
	}
	
	protected void notifyObservers(D d){
		for(Observer<D> obs : m_Obs){
			obs.update(this, d);
		}
	}

	@Override
	public void register(Observer<D> obs) {
		m_Obs.add(obs);
	}

	@Override
	public void unregister(Observer<D> obs) {
		m_Obs.remove(obs);
	}
	
}
