package couple.shopping

import static org.springframework.http.HttpStatus.*

import couple.shopping.command.CreateCoupleCommand;
import grails.converters.*


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
	
	def index(){
		respond Couple.findAll(), [status: OK]
	}
	
}
