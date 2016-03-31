package couple.shopping


import grails.rest.*

@Resource(readOnly = false, formats = ['json', 'xml'])
class Item {

    String description
    Boolean checked

    static belongsTo = [couple : Couple]

    static hasMany = [tags : Tag]

    static constraints = {
        description blank : false, size : 2..150
    }

}