package couple.shopping.infra

import couple.shopping.Couple

class CoupleNotifier {

	void created(Couple couple){
		couple.users.each {
			//TODO: send confirmation mail
		}
	}
}
