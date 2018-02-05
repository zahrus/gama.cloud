package msi.gama.lang.gaml.web.workspace.ui;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.ConfirmationCallback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextOutputCallback;
import javax.security.auth.login.LoginException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

public class DummyLoginModule implements javax.security.auth.spi.LoginModule {

	private static final Map USERS = new HashMap();
	{
		USERS.put("admin", "admin");
		USERS.put("user1", "rap");
		USERS.put("user2", "equinox");
	}
	private CallbackHandler callbackHandler;
	private boolean loggedIn;
	private Subject subject;

	public DummyLoginModule() {
	}

	private String loggedUser="";
	
	
	
	public String getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(String loggedUser) {
		this.loggedUser = loggedUser;
	}
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options) {
		this.subject = subject;
		this.callbackHandler = callbackHandler;
	}
	public String md5(String str){
		String result = "";
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
			digest.update(str.getBytes());
			BigInteger bigInteger = new BigInteger(1,digest.digest());
			result = bigInteger.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public boolean login() throws LoginException {
		Callback label = new TextOutputCallback(TextOutputCallback.INFORMATION, "Please login using Pre-registered account\n or Google authentication!");
		NameCallback nameCallback = new NameCallback("Username:");
		PasswordCallback passwordCallback = new PasswordCallback("Password:", false);
		GoogleSigninCallback gCallback=new GoogleSigninCallback("", 0, 0, 0);
		try {
			callbackHandler.handle(new Callback[] { label, nameCallback, passwordCallback, gCallback });
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		String username = nameCallback.getName();
		String password = "";
		if (passwordCallback.getPassword() != null) {
			password = md5(String.valueOf(passwordCallback.getPassword()));
		}
//		loggedIn = password.equals(USERS.get(username));
		IPersistencyService dbservice = GamaPersistencyService.getInstance();
		if (dbservice.isRunning()) {
			if(username!=null && ((GamaPersistencyService) dbservice).getUser(username)!=null && ((GamaPersistencyService) dbservice).getUser(username).getPassword().equals(password)) {
				loggedIn=true;
				loggedUser=username;
			}
//			dbservice.deleteUser(username);
//			MessageDialog.openInformation(getSite().getShell(), "Open", "Open Message Dialog!");
//			((GamaPersistencyService) dbservice).createUser("user", "HUYNH", "Nghi", "GAMA", "hqnghi88@gmail.com", md5("user"));
//			System.out.println(""+((GamaPersistencyService) dbservice).getAllUsers());
			
		}
		return loggedIn;
	}

	public boolean commit() throws LoginException {
		subject.getPublicCredentials().add(USERS);
		subject.getPrivateCredentials().add(Display.getCurrent());
		subject.getPrivateCredentials().add(SWT.getPlatform());
		return loggedIn;
	}

	public boolean abort() throws LoginException {
		loggedIn = false;
		return true;
	}

	public boolean logout() throws LoginException {
		loggedIn = false;
		return true;
	}
}