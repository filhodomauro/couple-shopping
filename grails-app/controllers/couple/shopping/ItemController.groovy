package couple.shopping

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.OK

class ItemController {
    static responseFormats = ['json', 'xml']

    ItemService itemService

    UserService userService

    @Secured('ROLE_USER')
    def index(){
        respond itemService.list(userService.coupleFromAuthenticatedUser, params), [status: OK]
    }

    @Secured('ROLE_USER')
    def create(Item item){
        respond itemService.create(userService.coupleFromAuthenticatedUser, item), [status: CREATED]
    }

}
