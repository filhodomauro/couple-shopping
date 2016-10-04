package couple.shopping.infra

import couple.shopping.Couple
import couple.shopping.User
import couple.shopping.infra.mail.Email
import couple.shopping.infra.mail.EmailNotifier
import grails.util.Holders
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "mailGun")
class CoupleConfirmationNotifier {

	private static Logger LOG = LoggerFactory.getLogger(CoupleConfirmationNotifier)

	String url
	String apiKey
	String from

    void created(Couple couple){
        couple.users.each { user ->
            def email = getEmail user
            EmailNotifier.notify email
		}
	}

	private Email getEmail(User user){
		new Email(
				url: this.url,
				apiKey: this.apiKey,
				from: this.from,
                to: user.email,
                subject: "Welcome to Couple Shopping - confirmation",
                text: "Welcome to Couple Shopping. \n" +
                        "Please confirm your account at couple shopping ${Holders.config.coupleShopping.url}/confirm/${user.username}/${user.confirmationToken}"
		)
	}


}
