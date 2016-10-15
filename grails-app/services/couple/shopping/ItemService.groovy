package couple.shopping

import exceptions.BadRequestException
import exceptions.NotFoundException
import grails.validation.ValidationException

/**
 * Created by maurofilho on 10/13/16.
 */
class ItemService {

    CoupleService coupleService

    def create(Map params, Item item){
        def couple = coupleService.findOne(params['coupleId']?.toInteger())
        item.couple = couple
        if(!item.validate()){
            throw new ValidationException("Erro ao adicionar item", item.errors)
        }
        item.save()
    }

    def update(Item item){
        if(!item.validate()){
            throw new ValidationException("Erro ao salvar item", item.errors)
        }
        item.save()
    }

    def list(Map params){
        def couple = coupleService.findOne(params['coupleId']?.toInteger())
        if(!couple){
            throw new NotFoundException("Casal não encontrado")
        }
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

    def findOne(id){
        def item = Item.get id
        if(!item){
            throw new NotFoundException("Item não encontrado")
        }
        item
    }

    def check(itemId){
        def item = findOne itemId
        item.checked = true
        item.dateChecked = new Date()
        item.save()
    }

    def delete(coupleId, id){
        def item = findOne id
        if(item.couple.id != coupleId){
            throw new BadRequestException("Item não pertence ao casal")
        }
        item.delete()
    }

}
