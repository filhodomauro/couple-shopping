import couple.shopping.Role
import couple.shopping.Tag

class BootStrap {

    def init = { servletContext ->
        if(Role.count() == 0){
            new Role(authority: "ROLE_USER").save()
            new Role(authority: "ROLE_USER_ADMIN").save()
        }

        if(Tag.count() == 0){
            ['mercado', 'padaria', 'feira', 'farmacia'].each {
                new Tag(description: it).save()
            }
        }
    }
    def destroy = {
    }
}
