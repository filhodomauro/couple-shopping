import couple.shopping.Couple
import couple.shopping.Item
import couple.shopping.Role
import couple.shopping.User
import couple.shopping.UserRole

class BootStrap {

    def init = { servletContext ->
        log.info "Teste bootstrap"
        Couple couple = new Couple(name : "couple")
        couple.addToUsers(
                new User( name: "User 1", username : "user1", email : "user1@teste.com", password: "123456")
        )

        couple.addToItems(
                new Item( description : "item 1", checked: false )
        )
        couple.save( failOnError : true)

        Role role = new Role(authority: "ROLE_USER").save()

        new Role(authority: "ROLE_USER_ADMIN").save()

        UserRole.create couple.users.first(), role

    }
    def destroy = {
    }
}
