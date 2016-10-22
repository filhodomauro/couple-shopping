package couple.shopping

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.OK

/**
 * Created by maurofilho on 10/12/16.
 */
class UserController implements ExceptionHandlerController {

    static responseFormats = ['json', 'xml']

    UserService userService

    @Secured('ROLE_USER')
    def info(){
        def user = userService.authenticatedUser
        respond user, [status: OK]
    }
}
