package couple.shopping


import grails.rest.*

class Couple {

    String name

    Date dateCreated

    static hasMany = [users : User, items : Item]

    static constraints = {
        name blank : false, size : 3..100
    }

}