package couple.shopping

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.OK

class ItemController {
    static responseFormats = ['json', 'xml']

    ItemService itemService

    CoupleService coupleService

    @Secured('ROLE_USER')
    def index(){
        respond itemService.list(params), [status: OK]

    }

    def create(Item item){
        respond itemService.create(params, item), [status: CREATED]
    }

}
