package exceptions

/**
 * Created by maurofilho on 10/15/16.
 */
class BadRequestException extends MessageResourceException{

    BadRequestException(String message, String code){
        super(message, code)
    }
}
