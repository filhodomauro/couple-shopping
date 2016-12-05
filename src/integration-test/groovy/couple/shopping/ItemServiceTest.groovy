package couple.shopping

import exceptions.NotFoundException
import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import spock.lang.Specification

import static couple.shopping.utils.CoupleShoppingTestHelper.getUserCommand
import static couple.shopping.utils.CoupleShoppingTestHelper.getCoupleCommand


/**
 * Created by maurofilho on 10/13/16.
 */
@Integration
@Rollback
class ItemServiceTest extends Specification{

    @Autowired
    ItemService itemService

    @Autowired
    CoupleService coupleService

    @Autowired
    UserService userService

    def setup(){
        def user = userService.create getUserCommand()
        Authentication auth = new UsernamePasswordAuthenticationToken(user,null);
        SecurityContextHolder.context.setAuthentication(auth)
        coupleService.create getCoupleCommand()
    }

    def "test that an item is created"(){
        given:
        def item = new Item(
                description: "Item created"
        )
        item.addToTagsItem new TagItem( description: "farmacia")

        when:
        def persistentItem = itemService.create item

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
        def item = new Item(description: "item list 1")
        def item2 = new Item(description: "item list 2")
        def item3 = new Item(description: "item list 2")

        item.addToTagsItem new TagItem( description: "farmacia")
        item.addToTagsItem new TagItem( description: "mercado")
        item2.addToTagsItem new TagItem( description: "farmacia")
        itemService.create item
        itemService.create item2
        itemService.create item3

        when:
        def items = itemService.list [:]

        then:
        items != null
        items.find{it.description == item.description} != null
        items.find{it.description == item2.description} != null
        items.find{it.description == item3.description} != null
    }

    def "test that many items are listed by couple and filtered by tags"(){
        given:
        def item = new Item(description: "item list 1")
        def item2 = new Item(description: "item list 2")
        def item3 = new Item(description: "item list 3")
        def item4 = new Item(description: "item list 4")

        item.addToTagsItem new TagItem( description: "farmacia")
        item.addToTagsItem new TagItem( description: "mercado")
        item2.addToTagsItem new TagItem( description: "farmacia")
        item3.addToTagsItem new TagItem( description: "farmacia")
        item4.addToTagsItem new TagItem( description: "padaria")
        itemService.create item
        itemService.create item2
        itemService.create item3
        itemService.create item4

        when:
        Map params = ['tags': 'farmacia']
        def items = itemService.list params

        then:
        items != null
        items.find{it.description == item.description} != null
        items.find{it.description == item2.description} != null
        items.find{it.description == item3.description} != null
        items.find{it.description == item4.description} == null

        when:
        params = ['tags': 'mercado']
        items = itemService.list params

        then:
        items != null
        items.find{it.description == item.description} != null
        items.find{it.description == item2.description} == null
        items.find{it.description == item3.description} == null
        items.find{it.description == item4.description} == null

        when:
        params = ['tags': 'mercado;padaria']
        items = itemService.list params

        then:
        items != null
        items.find{it.description == item.description} != null
        items.find{it.description == item2.description} == null
        items.find{it.description == item3.description} == null
        items.find{it.description == item4.description} != null
    }

    def "test that an item is checked"(){
        given:
        def item = new Item(description: "item list 1")
        itemService.create item

        when:
        itemService.check item.id

        then:
        def checkItem = itemService.findOne item.id
        checkItem.checked
        checkItem.dateChecked != null

    }

    def "test that an item is deleted"(){
        given:
        def item = new Item(description: "item list 1")
        itemService.create item

        when:
        itemService.delete item.id
        itemService.findOne item.id

        then:
        thrown(NotFoundException)
    }

    def "test that an item is updated"(){
        given:
        def item = new Item(description: "item list 1")
        itemService.create item

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
}
