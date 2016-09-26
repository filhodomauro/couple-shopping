package couple.shopping

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class User implements Serializable {

	private static final long serialVersionUID = 1

	transient springSecurityService

	String username
	String password
	String email
	String name
	String confirmationToken
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this)*.role
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		if(password){
			password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
		}
	}

	static transients = ['springSecurityService']

	static constraints = {
		password nullable:true, blank: true, password: true
		username blank: false, unique: true
		name blank : false, size : 3..100
		email blank : false, size : 4..100, email : true
		confirmationToken nullable: true
	}

	static mapping = {
		password column: '`password`'
	}
}
