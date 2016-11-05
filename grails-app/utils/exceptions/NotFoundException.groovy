package exceptions

/**
 * Created by maurofilho on 9/25/16.
 */
class NotFoundException extends MessageResourceException {

    NotFoundException(String message, String code){
        super(message, code)
    }

}
