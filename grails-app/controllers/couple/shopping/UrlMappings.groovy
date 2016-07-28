package couple.shopping

class UrlMappings {

    static mappings = {


        "/"(controller: 'application', action:'index')
		"/couples" (controller: 'couple', action: [GET:'index', POST:'create'])
		"/couples/$id" (controller: 'couple', action: [GET:'show', PUT:'update', DELETE:'delete'])

		"/tags" (controller: 'tag', action: [GET: 'index'])
		
        "/couples/$coupleId/items" (controller: 'item', action: [GET: 'index', POST: 'save'])
        "/couples/$coupleId/items/$id" (controller: 'item', action: [PUT: 'update', DELETE: 'delete'])
        "/couples/$coupleId/items/$id/check" (controller: 'item', action: [PUT: 'check'])

        get "/items/$id" (controller: 'item', action: 'show')
        post "/items" (controller : 'item', action: 'save')
        put "/items" (controller: 'item', action: 'update')
        delete "/items" (controller: 'item', action: 'delete')

        
				
    }
}
