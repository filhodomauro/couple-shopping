package couple.shopping.infra

import couple.shopping.User
import grails.util.Holders
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class UserConfirmationNotifier {

	private static Logger LOG = LoggerFactory.getLogger(UserConfirmationNotifier)

    void created(User user, Notifier... notifiers){
		LOG.info "Sending creation notifier to user: ${user.username}"
		notifiers?.each {
			it.notify getMessage(user)
		}
	}

	Message getMessage(User user){
		new Message(
				to: user.email,
				subject: "Welcome to Couple Shopping - confirmation",
				content: "Welcome to Couple Shopping. \n" +
						"Please confirm your account at couple shopping ${Holders.config.coupleShopping.url}/users/confirm/${user.username}/${user.confirmationToken}"
		)
	}

}
