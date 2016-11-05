package couple.shopping

import couple.shopping.command.NewUserCommand
import couple.shopping.command.UpdateUserCommand
import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import spock.lang.Specification

/**
 * Created by maurofilho on 29/10/16.
 */
@Mock(User)
@TestFor(UserController)
class UserControllerTest extends Specification{

    def "test that a user is saved"(){
        setup:
        def user = [
                'username': 'filhodomauro',
                'password': 'qazwsx',
                'name': 'Mauro Filho',
                'email': 'filhodomauro@filhodomauro.com'
        ]
        controller.userService = Mock(UserService)
        controller.userService.create(_) >> { NewUserCommand userCommand ->
            def newUser = new User(userCommand.properties)
            newUser.id = 1
            newUser
        }

        when:
        request.method = HttpMethod.POST.name()
        request.json = user as JSON
        request.contentType = JSON_CONTENT_TYPE
        controller.save()

        then:
        response.status == HttpStatus.CREATED.value()
        response.json != null
        response.json.id != null
        response.json.username == 'filhodomauro'
    }

    def "test that user is updated"(){
        setup:
        def updatedUser = [
                'username': 'filhodomauro',
                'password': 'qazwsx',
                'name': 'Mauro Filho',
                'email': 'filhodomauro@filhodomauro.com'
        ]
        controller.userService = Mock(UserService)
        controller.userService.update(_) >> { UpdateUserCommand userCommand ->
            def user = new User(userCommand.properties)
            user.id = 1
            user
        }

        when:
        request.method = HttpMethod.PUT.name()
        params.id = 1
        request.json = updatedUser as JSON
        request.contentType = JSON_CONTENT_TYPE
        controller.update()

        then:
        response.status == HttpStatus.OK.value()
        response.json != null
        response.json.id == 1
        response.json.name == 'Mauro Filho'
    }

    def "test that confirmation is successful"(){
        setup:
        controller.userService = Mock(UserService)
        controller.messageSource.addMessage('confirmation.successful', LocaleContextHolder.locale, 'Confirmation successful')

        when:
        params.username = 'filhodomauro'
        params.token = '1111122223333'
        request.method = HttpMethod.GET.name()
        controller.confirm()

        then:
        response.status == HttpStatus.OK.value()
        1 * controller.userService.confirm('filhodomauro', '1111122223333')
        response.json.message == 'Confirmation successful'

    }
}
