package couple.shopping


import grails.rest.*

class Tag {

    String description

    static constraints = {
        description blank : false, size : 1..30
    }

}