package devopsdistilled.operp.client.commons.panes;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import devopsdistilled.operp.client.abstracts.EntityOperation;
import devopsdistilled.operp.client.abstracts.EntityPane;
import devopsdistilled.operp.client.commons.controllers.ContactInfoController;
import devopsdistilled.operp.client.commons.panes.controllers.ContactInfoPaneController;
import devopsdistilled.operp.client.commons.panes.models.observers.ContactInfoPaneModelObserver;
import devopsdistilled.operp.server.data.entity.commons.ContactInfo;
import devopsdistilled.operp.server.data.entity.commons.PhoneType;

public class ContactInfoPane
		extends
		EntityPane<ContactInfo, ContactInfoController, ContactInfoPaneController>
		implements ContactInfoPaneModelObserver {

	private ContactInfoPaneController controller;

	private final JPanel pane;
	private final JTextField emailField;
	private final JTextField workNumField;
	private final JTextField mobileNumField;
	private final JTextField homeNumField;
	private JPanel addressPanel;

	public ContactInfoPane() {
		pane = new JPanel();
		pane.setLayout(new MigLayout("", "[][grow]", "[][][][][][][][][]"));

		JLabel lblAddress = new JLabel("Address");
		pane.add(lblAddress, "flowx,cell 0 0");

		addressPanel = new JPanel();
		pane.add(addressPanel, "cell 0 1,grow,span");

		JLabel lblEmail = new JLabel("Email");
		pane.add(lblEmail, "cell 0 3");

		emailField = new JTextField();
		emailField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String email = emailField.getText().trim();

				if (!email.equalsIgnoreCase("")) {
					try {
						InternetAddress emailAddr = new InternetAddress(email);
						emailAddr.validate();

						controller.getModel().getEntity().setEmail(email);

					} catch (AddressException e1) {
						JOptionPane.showMessageDialog(getPane(),
								"Not a valid email address");
						e.getComponent().requestFocus();
					}
				}
			}
		});
		pane.add(emailField, "cell 1 3,growx");
		emailField.setColumns(10);

		JLabel lblPhone = new JLabel("Phone");
		pane.add(lblPhone, "cell 0 5");

		JLabel lblWork = new JLabel("Work");
		pane.add(lblWork, "cell 0 6,alignx trailing");

		workNumField = new JTextField();
		workNumField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!workNumField.getText().trim().equalsIgnoreCase(""))
					controller.getModel().getEntity().getPhoneNumbers()
							.put(PhoneType.Work, workNumField.getText().trim());

			}
		});
		pane.add(workNumField, "cell 1 6,growx");
		workNumField.setColumns(10);

		JLabel lblMobile = new JLabel("Mobile");
		pane.add(lblMobile, "cell 0 7,alignx trailing");

		mobileNumField = new JTextField();
		mobileNumField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!mobileNumField.getText().trim().equalsIgnoreCase(""))
					controller
							.getModel()
							.getEntity()
							.getPhoneNumbers()
							.put(PhoneType.Mobile,
									mobileNumField.getText().trim());
			}
		});
		pane.add(mobileNumField, "cell 1 7,growx");
		mobileNumField.setColumns(10);

		JLabel lblHome = new JLabel("Home");
		pane.add(lblHome, "cell 0 8,alignx trailing");

		homeNumField = new JTextField();
		homeNumField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!homeNumField.getText().trim().equalsIgnoreCase(""))
					controller.getModel().getEntity().getPhoneNumbers()
							.put(PhoneType.Home, homeNumField.getText().trim());
			}
		});
		pane.add(homeNumField, "cell 1 8,growx");
		homeNumField.setColumns(10);

	}

	@Override
	public JComponent getPane() {
		return pane;
	}

	@Override
	public void setController(ContactInfoPaneController controller) {
		this.controller = controller;
	}

	public void setAddressPanel(JPanel addressPanel) {
		MigLayout layout = (MigLayout) pane.getLayout();
		Object constraints = layout.getComponentConstraints(this.addressPanel);

		pane.remove(this.addressPanel);
		pane.add(addressPanel, constraints);
		this.addressPanel = addressPanel;
		pane.validate();
	}

	@Override
	public void updateEntity(ContactInfo contactInfo,
			EntityOperation entityOperation) {

		emailField.setText(contactInfo.getEmail());
		workNumField.setText(contactInfo.getPhoneNumbers().get(PhoneType.Work));
		mobileNumField.setText(contactInfo.getPhoneNumbers().get(
				PhoneType.Mobile));
		homeNumField.setText(contactInfo.getPhoneNumbers().get(PhoneType.Home));

		if (EntityOperation.Details == entityOperation) {
			emailField.setEditable(false);
			workNumField.setEditable(false);
			mobileNumField.setEditable(false);
			homeNumField.setEditable(false);
		}
	}

	@Override
	public void resetComponents() {
		emailField.setEditable(true);
		workNumField.setEditable(true);
		mobileNumField.setEditable(true);
		homeNumField.setEditable(true);
	}

	@Override
	public ContactInfoController getEntityController() {
		// TODO Auto-generated method stub
		return null;
	}
}
