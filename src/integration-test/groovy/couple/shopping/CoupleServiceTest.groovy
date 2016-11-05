package couple.shopping

import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import grails.validation.ValidationException
import spock.lang.Ignore
import spock.lang.Specification
import couple.shopping.command.CreateCoupleCommand
import couple.shopping.command.NewUserCommand


@Ignore
@Integration
@Rollback
class CoupleServiceTest extends Specification{
	
	def coupleService
	
	void "test that a couple is created"(){
		setup:
		def cmd = createCoupleCommand()
		
		when:
		Couple couple = coupleService.create cmd
		
		then:
		couple != null
		couple.id != null
		Couple.get(couple.id) != null
		couple.name == cmd.name
		couple.users.size() == 2
		couple.users.find{ it.name == cmd.primaryUser.name } != null
		couple.users.find{ it.name == cmd.primaryUser.name }.email == cmd.primaryUser.email
		couple.users.find{ it.name == cmd.secondaryUser.name } != null
		couple.users.find{ it.name == cmd.secondaryUser.name }.email == cmd.secondaryUser.email
	}
	
	void "test that a couple is rejected by invalid data"(){
		setup:
		def cmd = createCoupleCommand()
		cmd.name = null
		
		when:
		Couple couple = coupleService.create cmd
		
		then:
		thrown(ValidationException)
	}
	
	void "test that a couple is updated"(){
		setup:
		def cmd = createCoupleCommand()
		Couple couple = coupleService.create cmd
		couple.name = "Novo nome do casal"
		
		when:
		couple = coupleService.update couple
		
		then:
		couple.name == "Novo nome do casal"
		Couple.get(couple.id).name == "Novo nome do casal"
	}

	def "test that a couple is reject by existent username"(){
		setup:
		def cmd = createCoupleCommand()
		coupleService.create cmd

		when:
		coupleService.create cmd

		then:
		thrown(ValidationException)
	}
	
	CreateCoupleCommand createCoupleCommand(){
		def cmd = new CreateCoupleCommand(
			name: "couple test",
			primaryUser: new NewUserCommand(
				name: "Primary user",
				email: "primary@email.com",
				password: "123456",
				username: "primary@email.com"
			),
			secondaryUser: new NewUserCommand(
				name: "Secondary user",
				email: "secondary@email.com",
				password: "654321",
				username: "secondary@email.com"
			)
		)
	}
}
