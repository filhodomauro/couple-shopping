package couple.shopping.utils

import couple.shopping.InviteUser
import couple.shopping.command.CreateCoupleCommand
import couple.shopping.command.NewUserCommand
import org.apache.commons.lang3.RandomStringUtils

/**
 * Created by maurofilho on 04/12/16.
 */
class CoupleShoppingTestHelper {

    static def getUserCommand(){
        new NewUserCommand(
                name: 'Mauro',
                username: RandomStringUtils.randomAlphabetic(6),
                password: RandomStringUtils.random(10),
                email: "mauro.${RandomStringUtils.randomAlphabetic(5)}@filhodomauro.top"
        )
    }

    static def getInvitedUser(){
        new InviteUser(
                name: RandomStringUtils.randomAlphabetic(10),
                email: "mauro.${RandomStringUtils.randomAlphabetic(5)}@filhodomauro.top"

        )
    }

    static CreateCoupleCommand getCoupleCommand(){
        def cmd = new CreateCoupleCommand(
                name: RandomStringUtils.randomAlphabetic(20)
        )
        cmd
    }
}
