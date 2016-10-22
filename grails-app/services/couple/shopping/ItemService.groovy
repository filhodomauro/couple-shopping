package couple.shopping

import exceptions.NotFoundException
import grails.validation.ValidationException

/**
 * Created by maurofilho on 10/13/16.
 */
class ItemService {

    CoupleService coupleService

    def create(Couple couple, Item item){
        item.couple = couple
        if(!item.validate()){
            throw new ValidationException("Erro ao adicionar item", item.errors)
        }
        item.save()
    }

    def update(Couple couple, Item item){
        def foundItem = findOne(couple, item.id)
        item.couple = foundItem.couple
        if(!item.validate()){
            throw new ValidationException("Erro ao salvar item", item.errors)
        }
        item.save()
    }

    def list(Couple couple, Map params){
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

    def findOne(Couple couple, Long id){
        def item = Item.get id
        if(!item || item.couple.id != couple.id){
            throw new NotFoundException("Item Not Found: ${id}")
        }
        item
    }

    def check(Couple couple, Long itemId){
        def item = findOne couple, itemId
        item.checked = true
        item.dateChecked = new Date()
        item.save()
    }

    def delete(Couple couple, Long id){
        def item = findOne couple, id
        item.delete()
    }

}
