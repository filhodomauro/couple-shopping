import couple.shopping.Couple
import couple.shopping.Item
import couple.shopping.User

class BootStrap {

    def init = { servletContext ->
        log.error "Teste bootstrap"
        Couple couple = new Couple(name : "couple")
        couple.addToUsers(
                new User( name : "User 1", email : "user1@teste.com")
        )

        couple.addToItems(
                new Item( description : "item 1", checked: false )
        )
        couple.save( failOnError : true)

    }
    def destroy = {
    }
}
