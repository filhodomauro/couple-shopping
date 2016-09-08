package couple.shopping

import org.springframework.http.HttpStatus

/**
 * Created by maurofilho on 9/7/16.
 */
class ControllerHelper {

    static def errorObj(HttpStatus status, String message){
        [
                status: status.value(),
                message: message,
                error: status.reasonPhrase
        ]
    }
}
