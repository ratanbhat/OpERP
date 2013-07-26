package devopsdistilled.operp.client.items.views;

import javax.swing.JComponent;
import javax.swing.JDialog;

import devopsdistilled.operp.server.data.entity.Entiti;

public abstract class EntityDetailsDialog<E extends Entiti> {

	protected final JDialog dialog;

	public EntityDetailsDialog() {
		dialog = new JDialog();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setTitle("Details");
		dialog.setSize(400, 300);
	}

	public abstract JComponent getPane();

	public abstract void show(E entity);

	public JDialog getDialog() {
		return dialog;
	}
}
