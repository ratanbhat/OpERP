package devopsdistilled.operp.client.party.panes;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.inject.Inject;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import devopsdistilled.operp.client.abstracts.EntityOperation;
import devopsdistilled.operp.client.abstracts.EntityPane;
import devopsdistilled.operp.client.party.controllers.CustomerController;
import devopsdistilled.operp.client.party.panes.controllers.CustomerPaneController;
import devopsdistilled.operp.client.party.panes.models.observers.CustomerPaneModelObserver;
import devopsdistilled.operp.server.data.entity.party.Customer;
import devopsdistilled.operp.server.data.entity.party.Party;

public class CustomerPane extends
		EntityPane<Customer, CustomerController, CustomerPaneController>
		implements CustomerPaneModelObserver {

	@Inject
	private CustomerController customerController;

	private final JPanel pane;
	private final JTextField nameField;
	private final JTextField panVatField;
	private final JLabel lblCustomerId;
	private final JTextField customerIdField;
	private JPanel contactInfoPanel;
	private JPanel opBtnPanel;

	public CustomerPane() {
		pane = new JPanel();
		pane.setLayout(new MigLayout("", "[][grow]", "[][][][][][]"));

		lblCustomerId = new JLabel("Customer ID");
		pane.add(lblCustomerId, "cell 0 0,alignx trailing");

		customerIdField = new JTextField();
		customerIdField.setEditable(false);
		pane.add(customerIdField, "cell 1 0,growx");
		customerIdField.setColumns(10);

		JLabel lblCustomerName = new JLabel("Customer Name");
		pane.add(lblCustomerName, "cell 0 1,alignx trailing");

		nameField = new JTextField();
		nameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				((Party<?>) getController().getModel().getEntity())
						.setPartyName(nameField.getText().trim());
			}
		});
		pane.add(nameField, "cell 1 1,growx");
		nameField.setColumns(10);

		JLabel lblPanvat = new JLabel("PAN/VAT");
		pane.add(lblPanvat, "cell 0 2,alignx trailing");

		panVatField = new JTextField();
		panVatField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				((Party<?>) getController().getModel().getEntity())
						.setPanVat(panVatField.getText().trim());
			}
		});
		pane.add(panVatField, "cell 1 2,growx");
		panVatField.setColumns(10);

		contactInfoPanel = new JPanel();
		pane.add(contactInfoPanel, "cell 0 3 2 1,grow");

		opBtnPanel = new JPanel();
		pane.add(opBtnPanel, "cell 1 5,grow");

	}

	@Override
	public JComponent getPane() {
		return pane;
	}

	public void setContactInfopanel(JPanel contactInfoPanel) {
		MigLayout layout = (MigLayout) pane.getLayout();
		Object constraints = layout
				.getComponentConstraints(this.contactInfoPanel);

		pane.remove(this.contactInfoPanel);
		pane.add(contactInfoPanel, constraints);
		this.contactInfoPanel = contactInfoPanel;
		pane.validate();
	}

	@Override
	public void resetComponents() {

		lblCustomerId.setVisible(true);
		customerIdField.setVisible(true);
		nameField.setEditable(true);
		panVatField.setEditable(true);
	}

	@Override
	public CustomerController getEntityController() {
		return customerController;
	}

	@Override
	public void updateEntity(Customer customer, EntityOperation entityOperation) {
		if (EntityOperation.Create == entityOperation) {
			getController().getModel().setTitle("Create Customer");

			opBtnPanel = setBtnPanel(createOpPanel, opBtnPanel);

			lblCustomerId.setVisible(false);
			customerIdField.setVisible(false);

		} else if (EntityOperation.Edit == entityOperation) {

			getController().getModel().setTitle("Edit Customer");

			opBtnPanel = setBtnPanel(editOpPanel, opBtnPanel);

			customerIdField.setText(customer.getPartyId().toString());

		} else if (EntityOperation.Details == entityOperation) {

			getController().getModel().setTitle("Customer Details");
			opBtnPanel = setBtnPanel(detailsOpPanel, opBtnPanel);

			customerIdField.setText(customer.getPartyId().toString());
			nameField.setEditable(false);
			panVatField.setEditable(false);
			detailsOpPanel.setVisible(true);
		}

		nameField.setText(customer.getPartyName());
		panVatField.setText(customer.getPanVat());

	}

}
