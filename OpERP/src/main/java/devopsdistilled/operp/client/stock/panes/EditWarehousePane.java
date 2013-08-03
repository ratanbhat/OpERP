package devopsdistilled.operp.client.stock.panes;

import javax.inject.Inject;
import javax.swing.JComponent;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import devopsdistilled.operp.client.abstracts.SubTaskPane;
import devopsdistilled.operp.client.exceptions.NullFieldException;
import devopsdistilled.operp.client.items.exceptions.EntityNameExistsException;
import devopsdistilled.operp.client.stock.models.observers.EditWarehousePaneModelObserver;
import devopsdistilled.operp.client.stock.panes.controllers.EditWarehousePaneController;
import devopsdistilled.operp.client.stock.panes.details.WarehouseDetailsPane;
import devopsdistilled.operp.server.data.entity.stock.Warehouse;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditWarehousePane extends SubTaskPane implements 
	EditWarehousePaneModelObserver{
		
	@Inject
	private EditWarehousePaneController controller;
	
	@Inject
	private WarehouseDetailsPane warehouseDetailsPane;
	
	private final JPanel pane;
	private JTextField warehouseIdField;
	private JTextField warehouseNameField;
	private JButton btnCancel;
	private JButton btnUpdate;
	
	public EditWarehousePane(){
		pane=new JPanel();
		pane.setLayout(new MigLayout("", "[][grow]", "[][][][]"));
		
		JLabel lblWarehouseId = new JLabel("Warehouse Id");
		pane.add(lblWarehouseId, "cell 0 0,alignx trailing");
		
		warehouseIdField = new JTextField();
		warehouseIdField.setEditable(false);
		pane.add(warehouseIdField, "cell 1 0,growx");
		warehouseIdField.setColumns(10);
		
		JLabel lblWarehouseName = new JLabel("Warehouse Name");
		pane.add(lblWarehouseName, "cell 0 1,alignx trailing");
		
		warehouseNameField = new JTextField();
		pane.add(warehouseNameField, "cell 1 1,growx");
		warehouseNameField.setColumns(10);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getDialog().dispose();
			}
		});
		
		pane.add(btnCancel, "flowx,cell 1 3");
		
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Warehouse warehouse=new Warehouse();
				
				Long warehouseId=Long.parseLong(warehouseIdField.getText().trim());
				warehouse.setWarehouseId(warehouseId);
				
				String warehouseName=warehouseNameField.getText().trim();
				warehouse.setWarehouseName(warehouseName);
				
				try{
					controller.validate(warehouse);
					warehouse=controller.save(warehouse);
					getDialog().dispose();
					warehouseDetailsPane.show(warehouse);
					
				}catch(NullFieldException e1){
					JOptionPane.showMessageDialog(getPane(),
							"Warehouse Name should not be empty");

				} catch (EntityNameExistsException e1) {
					JOptionPane.showMessageDialog(getPane(),
							"Warehouse Name already exists");
				}
				
			}
		});
		pane.add(btnUpdate, "cell 1 3");
		
		
	}
	
	@Override
	public void updateEntity(Warehouse warehouse) {
		warehouseIdField.setText(warehouse.getWarehouseId().toString());
		warehouseNameField.setText(warehouse.getWarehouseName());
	}
	@Override
	public JComponent getPane() {
		return pane;
	}

}
