package couple.shopping

import exceptions.NotFoundException
import org.springframework.security.core.context.SecurityContextHolder

/**
 * Created by maurofilho on 22/10/16.
 */
class UserService {

    def getAuthenticatedUser(){
        def user = User.get SecurityContextHolder.context.authentication?.principal?.id
        if(!user){
            throw new NotFoundException("User not found")
        }
        user
    }

    Couple getCoupleFromAuthenticatedUser(){
        def user = authenticatedUser
        if(!user.couple){
            throw new NotFoundException("Couple not found")
        }
        user.couple
    }
}
