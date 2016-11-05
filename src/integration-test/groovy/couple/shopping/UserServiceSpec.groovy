package couple.shopping

import couple.shopping.command.UpdateUserCommand
import couple.shopping.command.NewUserCommand
import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import spock.lang.Specification

/**
 * Created by maurofilho on 05/11/16.
 */
@Integration
@Rollback
class UserServiceSpec extends Specification{

    UserService userService

    def "test that a user is saved"(){
        setup:
        def user = userCommand

        when:
        def newUser = userService.create user

        then:
        newUser != null
        newUser.id != null
        newUser.name == user.name
    }

    def "test that a user is updated"(){
        setup:
        def createCommand = userCommand
        def user = userService.create createCommand
        Authentication auth = new UsernamePasswordAuthenticationToken(user,null);
        SecurityContextHolder.context.setAuthentication(auth)

        when:
        def updateCommand = new UpdateUserCommand(
                id: user.id,
                email: 'mauro.filho@gmail.com',
                name: 'Mauro Filho'
        )
        def updated = userService.update updateCommand

        then:
        updated != null
        updated.id == user.id
        updated.name == updateCommand.name
        updated.email == updateCommand.email
        updated.password == user.password
    }

    def getUserCommand(){
        new NewUserCommand(
                name: 'Mauro',
                username: 'filhodomauro',
                password: '3434fff',
                email: 'mauro@filhodomauro.top'
        )
    }
}
