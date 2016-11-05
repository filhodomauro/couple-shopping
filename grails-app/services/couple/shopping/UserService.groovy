package couple.shopping

import couple.shopping.command.NewUserCommand
import couple.shopping.command.UpdateUserCommand
import couple.shopping.infra.UserConfirmationNotifier
import exceptions.NotFoundException
import grails.plugin.springsecurity.SpringSecurityService
import grails.validation.ValidationException
import org.springframework.security.core.context.SecurityContextHolder

import java.security.SecureRandom

/**
 * Created by maurofilho on 22/10/16.
 */
class UserService {

    UserConfirmationNotifier userConfirmationNotifier
    SpringSecurityService springSecurityService

    def emailNotifier

    def create(NewUserCommand userCommand){
        def user = userCommand.toUser()
        if(userCommand.hasErrors() || !user.validate()){
            throw new ValidationException("Ops! Favor preencher os dados corretamente", userCommand.errors ?: user.errors )
        }
        user.confirmationToken = generateToken()
        user.save()
        userConfirmationNotifier.created user, emailNotifier
        user
    }

    def update(UpdateUserCommand userCommand){
        if(userCommand.hasErrors()){
            throw new ValidationException("Ops! Favor preencher os dados corretamente", userCommand.errors)
        }
        def user = authenticatedUser
        user.name = userCommand.name
        user.email = userCommand.email
        if(userCommand.password){
            user.password = userCommand.password
        }
        user.save()
        user
    }

    def getAuthenticatedUser(){
        def user = User.get SecurityContextHolder.context.authentication?.principal?.id
        if(!user){
            throw new NotFoundException("User not found", "user.notfound")
        }
        user
    }

    Couple getCoupleFromAuthenticatedUser(){
        def user = authenticatedUser
        if(!user.couple){
            throw new NotFoundException("Couple not found", "couple.notfound")
        }
        user.couple
    }

    def confirm(String username, String confirmationToken){
        User user = User.findByUsernameAndConfirmationToken(username, confirmationToken)
        if(!user){
            throw new NotFoundException("Username or token not found", "username.token.notfound")
        }
        user.enabled = true
        user.save failOnError: true
        userRoles.each {
            UserRole.create user, it
        }
    }

    private List<Role> getUserRoles(){
        Role.findAllByAuthority 'ROLE_USER'
    }

    private static String generateToken(){
        new BigInteger(30*5,SecureRandom.instanceStrong).toString(32)
    }
}
