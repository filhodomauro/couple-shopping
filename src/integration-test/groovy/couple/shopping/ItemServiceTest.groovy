package couple.shopping

import exceptions.NotFoundException
import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

/**
 * Created by maurofilho on 10/13/16.
 */
@Integration
@Rollback
class ItemServiceTest extends Specification{

    @Autowired
    ItemService itemService

    def "test that an item is created"(){
        given:
        def couple = createCouple()
        def item = new Item(
                description: "Item created"
        )
        item.addToTagsItem new TagItem( description: "farmacia")

        when:
        def persistentItem = itemService.create(['coupleId':couple.id], item)

        then:
        persistentItem != null
        persistentItem.id != null
        persistentItem.description == item.description
        persistentItem.dateCreated != null
        persistentItem.tagsItem != null
        persistentItem.tagsItem.find{ it.description == "farmacia"}
    }

    def "test that many items are listed by couple"(){
        given:
        def couple = createCouple()

        def item = new Item(description: "item list 1")
        def item2 = new Item(description: "item list 2")
        def item3 = new Item(description: "item list 2")

        item.addToTagsItem new TagItem( description: "farmacia")
        item.addToTagsItem new TagItem( description: "mercado")
        item2.addToTagsItem new TagItem( description: "farmacia")
        itemService.create(['coupleId':couple.id], item)
        itemService.create(['coupleId':couple.id], item2)
        itemService.create(['coupleId':couple.id], item3)

        when:
        def items = itemService.list(['coupleId':couple.id])

        then:
        items != null
        items.find{it.description == item.description} != null
        items.find{it.description == item2.description} != null
        items.find{it.description == item3.description} != null
    }

    def "test that many items are listed by couple and filtered by tags"(){
        given:
        def couple = createCouple()

        def item = new Item(description: "item list 1")
        def item2 = new Item(description: "item list 2")
        def item3 = new Item(description: "item list 3")
        def item4 = new Item(description: "item list 4")

        item.addToTagsItem new TagItem( description: "farmacia")
        item.addToTagsItem new TagItem( description: "mercado")
        item2.addToTagsItem new TagItem( description: "farmacia")
        item3.addToTagsItem new TagItem( description: "farmacia")
        item4.addToTagsItem new TagItem( description: "padaria")
        itemService.create(['coupleId':couple.id], item)
        itemService.create(['coupleId':couple.id], item2)
        itemService.create(['coupleId':couple.id], item3)
        itemService.create(['coupleId':couple.id], item4)

        when:
        def items = itemService.list(['coupleId':couple.id, 'tags': 'farmacia'])

        then:
        items != null
        items.find{it.description == item.description} != null
        items.find{it.description == item2.description} != null
        items.find{it.description == item3.description} != null
        items.find{it.description == item4.description} == null

        when:
        items = itemService.list(['coupleId':couple.id, 'tags': 'mercado'])

        then:
        items != null
        items.find{it.description == item.description} != null
        items.find{it.description == item2.description} == null
        items.find{it.description == item3.description} == null
        items.find{it.description == item4.description} == null

        when:
        items = itemService.list(['coupleId':couple.id, 'tags': 'mercado;padaria'])

        then:
        items != null
        items.find{it.description == item.description} != null
        items.find{it.description == item2.description} == null
        items.find{it.description == item3.description} == null
        items.find{it.description == item4.description} != null
    }

    def "test that an item is checked"(){
        given:
        def couple = createCouple()

        def item = new Item(description: "item list 1")
        itemService.create(['coupleId':couple.id], item)

        when:
        itemService.check item.id

        then:
        def checkItem = itemService.findOne item.id
        checkItem.checked
        checkItem.dateChecked != null

    }

    def "test that an item is deleted"(){
        given:
        def couple = createCouple()

        def item = new Item(description: "item list 1")
        itemService.create(['coupleId':couple.id], item)

        when:
        itemService.delete couple.id, item.id
        itemService.findOne item.id

        then:
        thrown(NotFoundException)
    }

    def "test that an item is updated"(){
        given:
        def couple = createCouple()

        def item = new Item(description: "item list 1")
        itemService.create(['coupleId':couple.id], item)

        when:
        item.description = "new item description"
        item.addToTagsItem new TagItem( description: "farmacia")
        itemService.update item

        then:
        def updatedItem = itemService.findOne item.id
        updatedItem.description == "new item description"
        item.tagsItem != null
        item.tagsItem.find{ it.description == "farmacia"} != null
    }

    private Couple createCouple(){
        new Couple(
                name: "Couple 1"
        ).save()
    }
}
