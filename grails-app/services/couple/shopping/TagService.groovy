package couple.shopping

import exceptions.NotFoundException
import grails.validation.ValidationException

/**
 * Created by maurofilho on 10/12/16.
 */
class TagService {

    def list(){
        Tag.list()
    }

    def create(Tag tag){
        if(!tag.validate()){
            throw new ValidationException("Erro ao salvar tag", tag.errors)
        }
        tag.description = tag.description.toLowerCase()
        tag.save()
    }

    def findOne(id){
        def tag = Tag.get id
        if(!tag){
            throw new NotFoundException("Tag não encontrada")
        }
        tag
    }
}
