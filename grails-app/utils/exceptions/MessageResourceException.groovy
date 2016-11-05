package exceptions

/**
 * Created by maurofilho on 05/11/16.
 */
class MessageResourceException extends RuntimeException{

    String code

    MessageResourceException(String message){
        this(message, null)
    }

    MessageResourceException(String message, String code){
        super(message)
        this.code = code
    }
}
