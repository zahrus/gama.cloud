/**
 * @Generated by DSLFORGE
 */
package msi.gama.lang.gaml.web.editor;



import org.dslforge.styledtext.BasicText;
import org.dslforge.xtext.common.XtextContentAssistEnabledEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.xtext.resource.XtextResource;

import msi.gama.lang.gaml.resource.GamlResourceServices;
import msi.gama.lang.gaml.validation.IGamlBuilderListener;
import ummisco.gama.participative.EtherpadBasicText;
import ummisco.gama.participative.XtextContentAssistEnabledEtherpadEditor;

import msi.gama.rap.oauth.Activator;
public abstract class AbstractGamlEtherpadEditor extends XtextContentAssistEnabledEtherpadEditor {
	private IGamlBuilderListener resourceListener;

	public AbstractGamlEtherpadEditor() {
		super();
		System.out.println("--->>>>  ---  super() from ---->>>>    AbstractGamlEtherpadEditor ");
		xtextResourceFactory = new GamlResourceFactory();
		setLanguageName(Activator.MSI_GAMA_LANG_GAML_GAML);
		setInjector(Activator.getInstance().getInjector(Activator.MSI_GAMA_LANG_GAML_GAML));
		
	}

	@Override
	protected EtherpadBasicText createTextWidget(Composite parent, int styles) {
		EtherpadBasicText gamlWidget = new EtherpadBasicText(parent, styles);
		GridData textLayoutData = new GridData();
		textLayoutData.horizontalAlignment = SWT.FILL;
		textLayoutData.verticalAlignment = SWT.FILL;
		textLayoutData.grabExcessHorizontalSpace = true;
		textLayoutData.grabExcessVerticalSpace = true;
		gamlWidget.setLayoutData(textLayoutData);
		gamlWidget.setEditable(true);

		System.out.println("----->>>>>>>>>>>------>>>>>>>--- > Passage par la: AbstractGamlEtherpadEditor ");
		Color c = new Color(parent.getDisplay(), new RGB(0, 0, 250));
		gamlWidget.setBackground(c);
		return gamlWidget;
	}

	public void setResourceListener(final IGamlBuilderListener listener) {
		this.resourceListener = listener;
		GamlResourceServices.addResourceListener(xtextResource.getURI(), listener);
		System.out.println("----->>>>>>>>>>>------>>>>>>>--- > Passage par la: AbstractGamlEtherpadEditor -> "+xtextResource.getURI());
		// ((IXtextDocument) getDocument()).readOnly(new
		// CancelableUnitOfWork<Object, XtextResource>() {
		//
		// @Override
		// public Object exec(final XtextResource state, final CancelIndicator
		// cancelIndicator) throws Exception {
		// if (state != null)
		// GamlResourceServices.addResourceListener(state.getURI(), listener);
		// return null;
		// }
		// });
	}
}
