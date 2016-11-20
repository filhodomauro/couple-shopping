package couple.shopping.infra

import couple.shopping.Couple
import couple.shopping.InviteUser
import couple.shopping.User
import grails.util.Holders
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class UserNotifier {

	private static Logger LOG = LoggerFactory.getLogger(UserNotifier)

    void created(User user, Notifier... notifiers){
		LOG.info "Sending creation notifier to user: ${user.username}"
		notifiers?.each {
			it.notify getConfirmationMessage(user)
		}
	}

	void invited(InviteUser user, Couple couple, Notifier... notifiers){
		LOG.info "Sending creation notifier to invited: ${user.name}"
		notifiers?.each {
			it.notify getInviteMessage(user, couple)
		}
	}

	Message getConfirmationMessage(User user){
		new Message(
				to: user.email,
				subject: "Welcome to Couple Shopping - confirmation",
				content: "Welcome to Couple Shopping. \n" +
						"Please confirm your account at couple shopping ${Holders.config.coupleShopping.url}/users/confirm/${user.username}/${user.confirmationToken}"
		)
	}

	Message getInviteMessage(InviteUser user, Couple couple){
		new Message(
				to: user.email,
				subject: "Congratulations! You was invited to join a couple",
				content: "Hi, ${user.name}! \n" +
						"You was invited to join couple ${couple.name} in app Couple Shopping. \n" +
						"Checkout owr app and start to share items to shopping \n\n" +
						"Thanks, Couple Shopping team."

		)
	}

}
