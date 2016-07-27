package couple.shopping

import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import grails.validation.ValidationException

import org.springframework.beans.factory.annotation.Autowired

import spock.lang.Specification
import couple.shopping.command.CreateCoupleCommand
import couple.shopping.command.NewUserCommand


@Integration
@Rollback
class CoupleServiceTest extends Specification{
	
	@Autowired
	CoupleService coupleService
	
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
	
	CreateCoupleCommand createCoupleCommand(){
		def cmd = new CreateCoupleCommand(
			name: "couple test",
			primaryUser: new NewUserCommand(
				name: "Primary user",
				email: "primary@email.com"
			),
			secondaryUser: new NewUserCommand(
				name: "Secondary user",
				email: "secondary@email.com"
			)
		)
	}
}
