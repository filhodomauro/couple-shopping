package couple.shopping.command

import couple.shopping.User
import grails.validation.Validateable

class NewUserCommand implements Validateable {
	
	static constraints = {
		name blank: false, size: 2..100
		email blank:false, email: true
		username blank: false, size: 2..100
		password nullable: true
	}
	String name
	String username
	String email
	String password
	
	User toUser(){
		User user = new User()
		user.name = this.name
		user.username = this.username
		user.email = this.email
		user.password = this.password
		user
	}

}
