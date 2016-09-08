package couple.shopping

import grails.converters.JSON
import org.springframework.http.HttpStatus

import static couple.shopping.ControllerHelper.errorObj

/**
 * Created by maurofilho on 8/9/16.
 */
class AccessDeniedController {

    static responseFormats = ['json']

    def index(){
        response.status = HttpStatus.UNAUTHORIZED.value()
        withFormat {
            json { render errorObj(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.reasonPhrase) as JSON }
        }
    }
}
