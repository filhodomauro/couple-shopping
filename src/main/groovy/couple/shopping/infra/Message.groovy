package couple.shopping.infra

import grails.validation.Validateable


/**
 * Created by maurofilho on 10/8/16.
 */
class Message implements Validateable {

    static constraints = {
        to nullable: false, blank: false
        subject nullable: false, blank: false
        content nullable: false, blank: false
    }

    String to
    String subject
    String content
}
