package couple.shopping

import grails.rest.*

class ItemController extends RestfulController {
    static responseFormats = ['json', 'xml']
    ItemController() {
        super(Item)
    }

    def check(){

    }

    @Override
    protected List listAllResources(Map params) {
        Couple couple = Couple.get(params['coupleId']);
        if(couple){
            Item.findAllByCoupleAndTags(couple, params['tags'])
        }
        return []
    }
}
