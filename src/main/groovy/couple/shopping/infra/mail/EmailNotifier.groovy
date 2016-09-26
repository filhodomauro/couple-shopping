package couple.shopping.infra.mail

import grails.plugins.rest.client.RestBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

/**
 * Created by maurofilho on 9/25/16.
 */
class EmailNotifier {

    private static final Logger LOG = LoggerFactory.getLogger(EmailNotifier)

    static void notify(Email email){
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>()
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
}
