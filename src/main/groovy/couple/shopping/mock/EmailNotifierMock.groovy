package couple.shopping.mock

import couple.shopping.infra.Message
import couple.shopping.infra.Notifier
import grails.validation.ValidationException
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created by maurofilho on 10/8/16.
 */
class EmailNotifierMock implements Notifier{

    private static final Logger LOG = LoggerFactory.getLogger(EmailNotifierMock)

    @Override
    def notify(Message message){
        if(!message.validate()){
            LOG.error "Invalid message to notify: ${message.errors}"
            throw new ValidationException("Invalid message to notify", message.errors)
        }
    }
}
