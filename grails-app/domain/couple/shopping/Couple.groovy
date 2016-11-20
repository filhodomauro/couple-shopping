package couple.shopping

class Couple {

    String name

    Date dateCreated

    static hasMany = [users : User, items : Item, invites : InviteUser]

    static constraints = {
        name blank : false, size : 3..100
    }

}