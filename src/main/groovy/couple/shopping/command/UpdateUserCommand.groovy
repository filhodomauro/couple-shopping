package couple.shopping.command

import grails.validation.Validateable

class UpdateUserCommand implements Validateable {
	
	static constraints = {
		id nullable: true
		name blank: false, size: 2..100
		email blank:false, email: true
		password nullable: true
	}
	Long id
	String name
	String email
	String password
}
