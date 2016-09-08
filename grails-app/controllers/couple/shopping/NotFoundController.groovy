package couple.shopping

import grails.converters.JSON
import org.springframework.http.HttpStatus

import static couple.shopping.ControllerHelper.*

/**
 * Created by maurofilho on 8/9/16.
 */
class NotFoundController {

    static responseFormats = ['json']

    def index(){
        response.status = HttpStatus.NOT_FOUND.value()
        withFormat {
            json { render errorObj(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.reasonPhrase) as JSON }
        }
    }
}
