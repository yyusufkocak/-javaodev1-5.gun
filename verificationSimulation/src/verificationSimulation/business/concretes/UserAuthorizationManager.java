package verificationSimulation.business.concretes;

import verificationSimulation.business.abstracts.UserAuthorizationService;

public class UserAuthorizationManager implements UserAuthorizationService {

	@Override
	public boolean validation(String email) {

	if (email!=null) {
		System.out.println("----------------Bu k�s�mda linke t�klan�ld�----------------------");
		return true;
	}
		
		return false;
	}

}
