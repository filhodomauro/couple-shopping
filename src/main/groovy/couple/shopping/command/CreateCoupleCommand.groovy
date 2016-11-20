package couple.shopping.command

import grails.validation.Validateable
import couple.shopping.Couple

class CreateCoupleCommand implements Validateable {
	static constraints = {
		name size: 2..100
	}
	
	String name
}
