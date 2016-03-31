package couple.shopping

class UrlMappings {

    static mappings = {


        "/"(controller: 'application', action:'index')

        get "/couple" (controller : 'couple', action: 'show')
        post "/couple" (controller : 'couple', action: 'save')
        put "/couple" (controller : 'couple', action: 'update')
        delete "/couple" (controller : 'couple', action: 'delete')
        get "/couple/$id/items" (controller: 'item', action: 'index')

        put "/items" (controller: 'item', action: 'update')

        "/tags" (resources : 'tag')
    }
}
