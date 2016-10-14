package couple.shopping

import exceptions.NotFoundException
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.security.core.context.SecurityContextHolder

import static org.springframework.http.HttpStatus.OK

/**
 * Created by maurofilho on 10/12/16.
 */
class UserController implements ExceptionHandlerController {

    static responseFormats = ['json', 'xml']

    @Secured('ROLE_USER')
    def info(){
        def user = User.get SecurityContextHolder.context.authentication?.principal?.id
        if(!user){
            throw new NotFoundException("User not found")
        }
        respond user, [status: OK]
    }
}
