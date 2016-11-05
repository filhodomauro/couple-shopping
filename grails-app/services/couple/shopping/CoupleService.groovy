package couple.shopping

import couple.shopping.command.CreateCoupleCommand
import couple.shopping.infra.UserConfirmationNotifier
import exceptions.NotFoundException
import grails.validation.ValidationException

class CoupleService {
	
	UserConfirmationNotifier coupleConfirmationNotifier

	def emailNotifier

	def create(CreateCoupleCommand coupleCommand){
		log.info "saving couple"
		if(coupleCommand.hasErrors()){
			throw new ValidationException("Erro ao salvar casal", coupleCommand.errors)
		}
		def couple = coupleCommand.toCouple()
		couple.users?.each{
			it.confirmationToken = UUID.randomUUID().toString()
		}
		couple.save()
        coupleConfirmationNotifier.created couple, emailNotifier
		couple
	}
	
	def update(Couple couple){
		log.info "updating couple"
		if(couple.hasErrors()){
			throw new ValidationException("Erro ao atualizar casal", couple.errors)
		}
		couple.save()
	}

	def findOne(id){
		def couple = Couple.get id
		if(!couple){
			throw new NotFoundException("Casal não encontrado")
		}
		couple
	}
}
