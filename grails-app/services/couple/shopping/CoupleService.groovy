package couple.shopping

import exceptions.NotFoundException
import grails.validation.ValidationException
import couple.shopping.command.CreateCoupleCommand
import couple.shopping.infra.CoupleConfirmationNotifier;


class CoupleService {
	
	CoupleConfirmationNotifier coupleConfirmationNotifier

	def create(CreateCoupleCommand coupleCommand){
		log.info "saving couple"
		if(coupleCommand.hasErrors()){
			throw new ValidationException("Erro ao salvar casal", coupleCommand.errors)
		}
		def couple = coupleCommand.toCouple()
		couple.save()
        coupleConfirmationNotifier.created couple
		couple
	}
	
	def update(Couple couple){
		log.info "updating couple"
		if(couple.hasErrors()){
			throw new ValidationException("Erro ao atualizar casal", couple.errors)
		}
		couple.save()
	}

	def confirm(String username, String confirmationToken){
		User user = User.findByUsernameAndConfirmationToken(username, confirmationToken)
		if(!user){
			throw new NotFoundException("Username or token not found")
		}
		user.enabled = true
		user.save failOnError: true
	}
}
