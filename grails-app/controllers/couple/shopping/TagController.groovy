package couple.shopping

import grails.plugin.springsecurity.annotation.Secured

class TagController {
    static responseFormats = ['json', 'xml']

    def tagService

    @Secured('ROLE_USER')
    def index(){
        tagService.list()
    }

    @Secured('ROLE_USER_ADMIN')
    def create(Tag tag){
        tagService.create tag
    }
}
