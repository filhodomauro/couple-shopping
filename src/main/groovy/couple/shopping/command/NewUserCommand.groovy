package couple.shopping.command

import grails.validation.Validateable
import couple.shopping.User

class NewUserCommand implements Validateable {
	
	static constraints = {
		name blank: false, size: 2..100
		email blank:false, email: true
		password nullable: true
	}
	String name
	String email
	String password
	
	User toUser(){
		User user = new User()
		user.name = this.name
		user.email = this.email
		user
	}

}
