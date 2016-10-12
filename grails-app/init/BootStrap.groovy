import couple.shopping.Couple
import couple.shopping.Item
import couple.shopping.Role
import couple.shopping.User
import couple.shopping.UserRole

class BootStrap {

    def init = { servletContext ->
        if(Role.count() == 0){
            new Role(authority: "ROLE_USER").save()
            new Role(authority: "ROLE_USER_ADMIN").save()
        }
    }
    def destroy = {
    }
}
