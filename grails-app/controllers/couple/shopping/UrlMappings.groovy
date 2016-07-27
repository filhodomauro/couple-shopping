package couple.shopping

class UrlMappings {

    static mappings = {


        "/"(controller: 'application', action:'index')
		"/couples" (controller: 'couple', action: [GET:'index', POST:'save'])
		"/couples/$id" (controller: 'couple', action: [GET:'show', PUT:'update', DELETE:'delete'])

		"/tags" (controller: 'tag', action: [GET: 'index'])
		
        get "/couples/$coupleId/items" (controller: 'item', action: 'index')
        put "/couples/$coupleId/items" (controller: 'item', action: 'check')

        get "/items/$id" (controller: 'item', action: 'show')
        post "/items" (controller : 'item', action: 'save')
        put "/items" (controller: 'item', action: 'update')
        delete "/items" (controller: 'item', action: 'delete')

        
				
    }
}
