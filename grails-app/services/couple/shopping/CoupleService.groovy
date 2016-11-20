package couple.shopping

import couple.shopping.command.CreateCoupleCommand
import couple.shopping.infra.UserNotifier
import exceptions.BadRequestException
import exceptions.NotFoundException
import grails.validation.ValidationException

class CoupleService {
	
	UserNotifier userNotifier
	UserService userService
	def emailNotifier

	Couple create(CreateCoupleCommand coupleCommand){
		log.info "saving couple"
		if(coupleCommand.hasErrors()){
			throw new ValidationException("Erro ao salvar casal", coupleCommand.errors)
		}

		User user = userService.authenticatedUser
		if(user.couple){
			throw new BadRequestException("User already in a couple", "user.already.couple")
		}

		def couple = new Couple(
				name: coupleCommand.name
		)

		couple.addToUsers user
		couple.save()
		couple
	}
	
	Couple update(Couple couple){
		log.info "updating couple"
		if(couple.hasErrors()){
			throw new ValidationException("Erro ao atualizar casal", couple.errors)
		}
		couple.save()
	}

	Couple getCoupleFromAuthenticatedUser(){
		def user = userService.authenticatedUser
		if(!user.couple){
			throw new NotFoundException("Couple not found", "couple.notfound")
		}
		user.couple
	}

	void invite(InviteUser inviteUser){
		def couple = coupleFromAuthenticatedUser
		couple.addToInvites inviteUser

		if(!inviteUser.validate()){
			log.error "Error to invite user: ${inviteUser.errors}"
			throw new ValidationException("Error to invite user", inviteUser.errors)
		}
		couple.save()
		userNotifier.invited inviteUser, couple, emailNotifier
	}

	Couple acceptInvite(Long coupleId){
		User user = userService.authenticatedUser
		if (user.couple) {
			throw new BadRequestException("User already in a couple", "user.already.couple")
		}
		Couple couple = Couple.get coupleId
		if(!couple){
			throw new NotFoundException("Couple not found", "couple.notfound")
		}
		if (!hasInvite(user.email, couple)) {
			throw new NotFoundException("Invite not found", "invite.notfound")
		}
		couple.addToUsers user
		couple.save()
	}

	private boolean hasInvite(String email, Couple couple){
		InviteUser.findByEmailAndCouple(email, couple) != null
	}
}
