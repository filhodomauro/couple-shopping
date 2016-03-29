package couple.shopping

class UrlMappings {

    static mappings = {


        "/"(controller: 'application', action:'index')
        "/tags" (resources : 'tag')
    }
}
