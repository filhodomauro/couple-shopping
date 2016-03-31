package couple.shopping


import grails.rest.*

@Resource(readOnly = false, formats = ['json', 'xml'])
class Couple {

    String name

    static hasMany = [users : User, items : Item]

    static constraints = {
        name blank : false, size : 3..100
    }

}