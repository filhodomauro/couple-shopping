package couple.shopping


import grails.rest.*
import grails.converters.*

class CoupleController extends RestfulController {
    static responseFormats = ['json', 'xml']
    CoupleController() {
        super(Couple)
    }
}
