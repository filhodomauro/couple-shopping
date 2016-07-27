package couple.shopping


import grails.rest.*

class Item {

    String description
    boolean checked

    static belongsTo = [couple : Couple]

    static hasMany = [tags : Tag]

    static constraints = {
        description blank : false, size : 2..150
    }
}