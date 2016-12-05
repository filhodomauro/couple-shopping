package couple.shopping

import exceptions.NotFoundException
import grails.validation.ValidationException

/**
 * Created by maurofilho on 10/13/16.
 */
class ItemService {

    CoupleService coupleService

    def create(Item item){
        item.couple = couple
        if(!item.validate()){
            throw new ValidationException("Error to create item", item.errors)
        }
        item.save()
    }

    def update(Item item){
        def foundItem = findOne item.id
        item.couple = foundItem.couple
        if(!item.validate()){
            throw new ValidationException("Error to save item", item.errors)
        }
        item.save()
    }

    def list(Map params){
        String tags = params['tags']
        Item.withCriteria {
            eq 'couple', couple
            if(tags){
                tagsItem {
                    inList('description', tags?.split(';'))
                }
            }
        }
    }

    def findOne(Long id){
        def item = Item.get id
        if(!item || item.couple.id != couple.id){
            throw new NotFoundException("Item Not Found", "item.notfound")
        }
        item
    }

    def check(Long itemId){
        def item = findOne itemId
        item.checked = true
        item.dateChecked = new Date()
        item.save()
    }

    def delete(Long id){
        def item = findOne id
        item.delete()
    }

    private Couple getCouple(){
        coupleService.coupleFromAuthenticatedUser
    }

}
