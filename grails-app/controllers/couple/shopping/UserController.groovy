package couple.shopping

import couple.shopping.command.NewUserCommand
import couple.shopping.command.UpdateUserCommand
import exceptions.BadRequestException
import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*

/**
 * Created by maurofilho on 10/12/16.
 */
class UserController implements ExceptionHandlerController {

    static allowedMethods = [save: 'POST', update: 'PUT', delete: 'DELETE']

    static responseFormats = ['json']

    UserService userService

    def save(NewUserCommand userCommand){
        def user = userService.create userCommand
        respond user, [status: CREATED]
    }

    def confirm(String username, String token){
        if(!username || !token){
            log.error "Invalid parameters username: $username - token: $token"
            throw new BadRequestException("Invalid parameters username: $username - token: $token", "")
        }
        userService.confirm username, token
        def success = new Result(message: getMessage('confirmation.successful'))
        respond success, [status: OK]
    }

    @Secured('ROLE_USER')
    def info(){
        def user = userService.authenticatedUser
        respond user, [status: OK]
    }

    @Secured('ROLE_USER')
    def update(UpdateUserCommand userCommand){
        def user = userService.update userCommand
        respond user, [status: OK]
    }
}
