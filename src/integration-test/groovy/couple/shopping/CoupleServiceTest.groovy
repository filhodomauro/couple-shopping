package couple.shopping

import couple.shopping.command.CreateCoupleCommand
import couple.shopping.command.NewUserCommand
import couple.shopping.infra.UserNotifier
import exceptions.BadRequestException
import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import grails.validation.ValidationException
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import spock.lang.Shared
import spock.lang.Specification

@Integration
@Rollback
class CoupleServiceTest extends Specification{
	
	CoupleService coupleService
	UserService userService

	@Shared
	User user

	def setup(){
		def createCommand = userCommand
		user = userService.create createCommand
		Authentication auth = new UsernamePasswordAuthenticationToken(user,null);
		SecurityContextHolder.context.setAuthentication(auth)
	}
	
	void "test that a couple is created"(){
		setup:
		def cmd = createCoupleCommand()
		
		when:
		Couple couple = coupleService.create cmd
		
		then:
		couple != null
		couple.id != null
		def persistentCouple = Couple.get(couple.id)
		persistentCouple.name == cmd.name
		persistentCouple.users.size() > 0
		persistentCouple.users.find{ it.name == user.name } != null
		persistentCouple.users.find{ it.name == user.name }.email == user.email
		persistentCouple.users.find{ it.name == user.name }.couple == persistentCouple
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

	def "test that a couple is showed by logger user"(){
		setup:
		def cmd = createCoupleCommand()
		coupleService.create cmd

		when:
		def couple = coupleService.coupleFromAuthenticatedUser

		then:
		couple != null
		couple.name == cmd.name
		couple.users != null
		couple.users.find{ it.name == user.name } != null
		couple.users.find{ it.name == user.name }.email == user.email
	}

	def "test that a couple is reject because an user already in a couple"(){
		setup:
		setup:
		coupleService.create createCoupleCommand()

		when:
		coupleService.create createCoupleCommand()

		then:
		thrown(BadRequestException)
	}
	
	CreateCoupleCommand createCoupleCommand(){
		def cmd = new CreateCoupleCommand(
			name: RandomStringUtils.randomAlphabetic(20)
		)
		cmd
	}

	def "test that an user is invited"(){
		setup:
		def cmd = createCoupleCommand()
		coupleService.create cmd
		def invite = invitedUser

		when:
		coupleService.invite invite

		then:
		def persistentInvite = InviteUser.findByEmail(invite.email)
		persistentInvite.couple.name == cmd.name
	}

	def "test that an invite is accepted"(){
		setup:
		def cmd = createCoupleCommand()
		Couple couple = coupleService.create cmd
		def invite = invitedUser
		coupleService.invite invite

		def createCommand = userCommand
		createCommand.name = invite.name
		createCommand.email = invite.email
		def otherUser = userService.create createCommand
		Authentication auth = new UsernamePasswordAuthenticationToken(otherUser,null);
		SecurityContextHolder.context.setAuthentication(auth)

		when:
		def coupleAccepted = coupleService.acceptInvite couple.id

		then:
		coupleAccepted != null
		coupleAccepted.id == couple.id
		def coupleWithNewUser = Couple.get(couple.id)
		coupleWithNewUser != null
		coupleWithNewUser.users.size() == 2
		coupleWithNewUser.users.find { it.email == invite.email } != null
	}

	def getUserCommand(){
		new NewUserCommand(
				name: 'Mauro',
				username: RandomStringUtils.randomAlphabetic(6),
				password: RandomStringUtils.random(10),
				email: "mauro.${RandomStringUtils.randomAlphabetic(5)}@filhodomauro.top"
		)
	}

	def getInvitedUser(){
		new InviteUser(
				name: RandomStringUtils.randomAlphabetic(10),
				email: "mauro.${RandomStringUtils.randomAlphabetic(5)}@filhodomauro.top"

		)
	}
}
