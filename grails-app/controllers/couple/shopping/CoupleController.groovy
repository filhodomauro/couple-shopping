package couple.shopping

import grails.converters.JSON

import static couple.shopping.ControllerHelper.errorObj
import static org.springframework.http.HttpStatus.*

import couple.shopping.command.CreateCoupleCommand;
import grails.plugin.springsecurity.annotation.Secured


class CoupleController implements ExceptionHandlerController {
    
	static responseFormats = ['json', 'xml']
    
	CoupleService coupleService

	def create(CreateCoupleCommand createCoupleCommand){
		log.info "creating couple"
		def couple = coupleService.create createCoupleCommand
		respond couple, [status: CREATED]
	}

    @Secured('ROLE_USER')
	def update(Couple couple){
		couple = coupleService.update couple
		respond couple, [status: OK]
	}

	@Secured('ROLE_USER_ADMIN')
	def index(){
		respond Couple.findAll(), [status: OK]
	}

	def confirm(String username, String token){
		if(!username || !token){
            log.error "Invalid parameters username: $username - token: $token"
            response.status = BAD_REQUEST.value()
            withFormat {
                json { render errorObj(BAD_REQUEST, "Invalid parameters") as JSON }
            }
		}
        coupleService.confirm username, token
        render "Confirmation successful"
	}
}
