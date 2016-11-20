package couple.shopping

/**
 * Created by maurofilho on 20/11/16.
 */
class InviteUser {

    String name
    String email

    static belongsTo = [ couple: Couple ]

    static constraints = {
        name blank : false, size : 3..100
        email blank : false, size : 4..100, email : true
    }
}
