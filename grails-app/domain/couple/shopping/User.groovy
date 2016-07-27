package couple.shopping


import grails.rest.*

class User {

    String name
    String email

    static belongsTo = [couple : Couple]

    static constraints = {
        name blank : false, size : 3..100
        email blank : false, size : 4..100, email : true
    }

}