package couple.shopping

import exceptions.BadRequestException
import exceptions.NotFoundException
import grails.validation.ValidationException;

import static org.springframework.http.HttpStatus.*


trait ExceptionHandlerController {

	def handle(Exception e){
		def errors = null
		if(e instanceof ValidationException) {
			errors = [errors: ((ValidationException) e).errors]
			response.status = UNPROCESSABLE_ENTITY.value()
		} else if(e instanceof NotFoundException){
			errors = [errors: ['message':e.getMessage()]]
			response.status = NOT_FOUND.value()
		} else if(e instanceof BadRequestException){
			errors = [errors: ['message':e.getMessage()]]
			response.status = BAD_REQUEST.value()
		} else {
			errors = [errors: ['message':e.getMessage()]]
			response.status = INTERNAL_SERVER_ERROR.value()
		}
		log.error "Error on request: ${errors} - ${response.status}", e
		respond errors
	}
}
