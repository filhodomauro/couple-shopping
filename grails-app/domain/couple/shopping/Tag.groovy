package couple.shopping


import grails.rest.*

@Resource(readOnly = false, formats = 'json')
class Tag {

    String description

    static constraints = {
        description blank : false, size : 1..30
    }

}