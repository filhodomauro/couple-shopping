package couple.shopping

import grails.validation.ValidationException
import couple.shopping.command.CreateCoupleCommand
import couple.shopping.infra.CoupleNotifier;


class CoupleService {
	
	CoupleNotifier coupleNotifier

	def create(CreateCoupleCommand coupleCommand){
		log.info "saving couple"
		if(coupleCommand.hasErrors()){
			throw new ValidationException("Erro ao salvar casal", coupleCommand.errors)
		}
		def couple = coupleCommand.toCouple()
		couple.save()
		coupleNotifier.created couple
		couple
	}
	
	def update(Couple couple){
		log.info "updating couple"
		if(couple.hasErrors()){
			throw new ValidationException("Erro ao atualizar casal", couple.errors)
		}
		couple.save()
	}
}
