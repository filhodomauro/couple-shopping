package couple.shopping

import exceptions.BadRequestException
import exceptions.MessageResourceException
import exceptions.NotFoundException
import grails.validation.ValidationException
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder

import static org.springframework.http.HttpStatus.*


trait ExceptionHandlerController {

	MessageSource messageSource

	def handle(Exception e){
		def errors = null
		if (e instanceof ValidationException) {
			errors = [errors: ((ValidationException) e).errors]
			response.status = UNPROCESSABLE_ENTITY.value()
		} else if (e instanceof NotFoundException) {
			errors = [errors: ['message':getMessage(e.code) ?: e.message]]
			response.status = NOT_FOUND.value()
		} else if (e instanceof BadRequestException) {
			errors = [errors: ['message':getMessage(e.code) ?: e.message]]
			response.status = BAD_REQUEST.value()
		} else if (e instanceof MessageResourceException) {
			errors = [errors: ['message':getMessage(e.code) ?: e.message]]
			response.status = INTERNAL_SERVER_ERROR.value()
		} else {
			errors = [errors: ['message':e.getMessage()]]
			response.status = INTERNAL_SERVER_ERROR.value()
		}
		log.error "Error on request: ${errors} - ${response.status}", e
		respond errors
	}

	String getMessage(String code){
		messageSource.getMessage(code, null,LocaleContextHolder.locale)
	}
}
