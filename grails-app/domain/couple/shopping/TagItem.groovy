package couple.shopping

/**
 * Created by maurofilho on 10/13/16.
 */
class TagItem {

    String description

    static belongsTo = [couple : Item]

    static constraints = {}
}
