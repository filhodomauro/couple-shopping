package couple.shopping.infra.mail

import couple.shopping.infra.Message
import couple.shopping.infra.Notifier
import grails.plugins.rest.client.RestBuilder
import grails.util.Holders
import grails.validation.ValidationException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

/**
 * Created by maurofilho on 9/25/16.
 */
class EmailNotifier implements Notifier{

    private static final Logger LOG = LoggerFactory.getLogger(EmailNotifier)

    @Override
    def notify(Message message){
        if(!message.validate()){
            LOG.error "Invalid message to notify: ${message.errors}"
            throw new ValidationException("Invalid message to notify", message.errors)
        }
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>()
        Email email = getEmail message
        form.add("from", email.from)
        form.add "to", email.to
        form.add "subject", email.subject
        form.add "text", email.text
        def builder = new RestBuilder()
        def response = builder.post(email.url) {
            auth 'api',email.apiKey
            contentType("application/x-www-form-urlencoded")
            body(form)
        }
        if(!response || !response.statusCode.'2xxSuccessful'){
            LOG.error "Mail cannot be send: Status ${response?.statusCode} - ${response?.text}"
            throw new RuntimeException("Mail cannot be send to ${email.to}")
        }
    }

    private Email getEmail(Message message){
        new Email(
                url: this.url,
                apiKey: this.apiKey,
                from: this.from,
                to: message.to,
                subject: message.subject,
                text: message.content
        )
    }

    String getUrl(){
        Holders.config.mailGun.url
    }

    String getApiKey(){
        Holders.config.mailGun.apiKey
    }

    String getFrom(){
        Holders.config.mailGun.from
    }
}
