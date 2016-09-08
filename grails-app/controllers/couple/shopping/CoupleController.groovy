package couple.shopping

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
	
	def update(Couple couple){
		couple = coupleService.update couple
		respond couple, [status: OK]
	}

	@Secured('ROLE_USER')
	def index(){
		respond Couple.findAll(), [status: OK]
	}

	@Secured('ROLE_USER_ADMIN')
	def check(){
		respond Couple.findAll(), [status: OK]
	}
	
}
