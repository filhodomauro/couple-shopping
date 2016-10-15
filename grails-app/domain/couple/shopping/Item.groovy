package couple.shopping

class Item {

    String description
    boolean checked

    Date dateCreated

    Date dateChecked

    static belongsTo = [couple : Couple]

    static hasMany = [tagsItem : TagItem]

    static constraints = {
        description blank : false, size : 2..150
        dateChecked nullable: true
    }
}