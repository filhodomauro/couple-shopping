package couple.shopping

class Tag {

    String description

    static constraints = {
        description blank : false, size : 1..30
    }

}