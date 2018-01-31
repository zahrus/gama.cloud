/*********************************************************************************************
 *
 * 'MatrixEditor.java, in plugin ummisco.gama.ui.shared, is part of the source code of the
 * GAMA modeling and simulation platform.
 * (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 * 
 *
 **********************************************************************************************/
package ummisco.gama.ui.parameters;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolItem;

import msi.gama.kernel.experiment.IParameter;
import msi.gama.metamodel.agent.IAgent;
import msi.gama.runtime.IScope;
import msi.gama.util.matrix.IMatrix;
import msi.gaml.types.IType;
import msi.gaml.types.Types;
import ummisco.gama.ui.interfaces.EditorListener;
import ummisco.gama.ui.utils.WorkbenchHelper;

public class MatrixEditor extends ExpressionBasedEditor<IMatrix<?>> {

	MatrixEditor(final IScope scope, final IParameter param) {
		super(scope, param);
	}

	MatrixEditor(final IScope scope, final IAgent agent, final IParameter param) {
		this(scope, agent, param, null);
	}

	MatrixEditor(final IScope scope, final IAgent agent, final IParameter param, final EditorListener<IMatrix<?>> l) {
		super(scope, agent, param, l);
	}

	MatrixEditor(final IScope scope, final Composite parent, final String title, final IMatrix<?> value,
			final EditorListener<IMatrix<?>> whenModified) {
		// Convenience method
		super(scope, new InputParameter(title, value), whenModified);
		this.createComposite(parent);
	}

	@Override
	public void applyEdit() {

		final MatrixEditorDialog d = new MatrixEditorDialog(getScope(), WorkbenchHelper.getShell(RWT.getUISession().getAttribute("user").toString()), currentValue);
		if (d.open() == IDialogConstants.OK_ID) {
			modifyValue(d.getMatrix());
		}

	}

	@Override
	protected void checkButtons() {
		final ToolItem edit = items[EDIT];
		if (edit != null && !edit.isDisposed()) {
			edit.setEnabled(true);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IType getExpectedType() {
		return Types.MATRIX;
	}

	@Override
	protected int[] getToolItems() {
		return new int[] { EDIT, REVERT };
	}

}