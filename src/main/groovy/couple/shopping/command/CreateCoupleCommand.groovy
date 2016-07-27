package couple.shopping.command

import grails.validation.Validateable
import couple.shopping.Couple

class CreateCoupleCommand implements Validateable {
	static constraints = {
		name size: 2..100
		primaryUser nullable: false
		secondaryUser nullable: false
	}
	
	String name
	NewUserCommand primaryUser
	NewUserCommand secondaryUser
	
	Couple toCouple(){
		Couple couple = new Couple()
		couple.name = this.name
		couple.addToUsers primaryUser.toUser()
		couple.addToUsers secondaryUser.toUser()
		couple
	}

}
