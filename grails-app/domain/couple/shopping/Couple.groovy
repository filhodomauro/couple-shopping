package couple.shopping


import grails.rest.*

@Resource(uri = "/couples",readOnly = false, formats = ['json', 'xml'])
class Couple {

    String name

    static hasMany = [users : User, itens : Item]

    static constraints = {
        name blank : false, size : 3..100
    }

}