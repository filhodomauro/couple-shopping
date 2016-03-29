package couple.shopping


import grails.rest.*

class TagController extends RestfulController {
    static responseFormats = ['json', 'xml']
    TagController() {
        super(Tag)
    }
}
