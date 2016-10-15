package exceptions

/**
 * Created by maurofilho on 10/15/16.
 */
class BadRequestException extends RuntimeException{

    BadRequestException(String message){
        super(message)
    }
}
