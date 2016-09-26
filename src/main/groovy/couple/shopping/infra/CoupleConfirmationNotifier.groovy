package couple.shopping.infra

import couple.shopping.Couple
import couple.shopping.User
import couple.shopping.infra.mail.Email
import couple.shopping.infra.mail.EmailNotifier
import grails.util.Holders

class CoupleConfirmationNotifier {

    void created(Couple couple){
        couple.users.each { user ->
            def email = getEmail user.email
            EmailNotifier.notify email
		}
	}

	private static Email getEmail(User user){
		new Email(
				url: Holders.config.mainGun.url,
				apiKey: Holders.config.mailGun.api_key,
				from: Holders.config.mailGun.from,
                to: user.email,
                subject: "Welcome to Couple Shopping - confirmation",
                text: "Welcome to Couple Shopping. \n" +
                        "Please confirm your account at couple shopping ${Holders.config.coupleShopping.url}/confirm/${user.username}/${user.confirmationToken}"
		)
	}


}
