package couple.shopping

import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.validation.ValidationException

import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus

import spock.lang.Specification
import couple.shopping.command.CreateCoupleCommand
import couple.shopping.command.NewUserCommand


@Mock([Couple, User])
@TestFor(CoupleController)
class CoupleControllerSpec extends Specification {
	
	def "test that a couple is created"(){
		setup:
		def coupleCommand = new CreateCoupleCommand(
			name:"Couple test",
			primaryUser: new NewUserCommand(
					name : "mauro filho",
					email: "mauro.filho@coupleshopping.com",
					password: "2327648376"
			),
			secondaryUser: new NewUserCommand(
				name: "ana paula",
				email: "ana.paula@coupleshopping.com"
			)
		)
		controller.coupleService = Mock(CoupleService)
		controller.coupleService.create(_) >> { CreateCoupleCommand c ->
			Couple couple = c.toCouple()
			couple.id = 1
			couple
		}
		when:
		request.method = HttpMethod.POST.name()
		request.json = coupleCommand as JSON
		request.contentType = JSON_CONTENT_TYPE
		controller.create()
		
		then:
		response.status == HttpStatus.CREATED.value()
		response.json != null
		response.json.id != null
		response.json.name == "Couple test"
	}
	
	void "test that a couple is rejected by invalid data"(){
		setup:
		def coupleCommand = new CreateCoupleCommand(
			primaryUser: new NewUserCommand(
					name : "mauro filho",
					email: "mauro.filho@coupleshopping.com",
					password: "2327648376"
			),
			secondaryUser: new NewUserCommand(
				name: "ana paula",
				email: "ana.paula@coupleshopping.com"
			)
		)
		controller.coupleService = Mock(CoupleService)
		controller.coupleService.create(_) >> { CreateCoupleCommand c ->
			c.validate()
			throw new ValidationException("Erro ao validar ", c.errors)
		}
		when:
		request.method = HttpMethod.POST.name()
		request.json = coupleCommand as JSON
		request.contentType = JSON_CONTENT_TYPE
		controller.create()
		
		then:
		response.status == HttpStatus.UNPROCESSABLE_ENTITY.value()
	}
}
