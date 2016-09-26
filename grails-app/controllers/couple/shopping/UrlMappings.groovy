package couple.shopping

class UrlMappings {

    static mappings = {


        "/"(controller: 'application', action:'index')
		"/couples" (controller: 'couple', action: [GET:'index', POST:'create'])
        "/couples/check" (controller: 'couple', action: [GET:'check'])
		"/couples/$id" (controller: 'couple', action: [GET:'show', PUT:'update', DELETE:'delete'])
        "/confirm/$username/$token" (controller: 'couple', action: [GET: 'confirm'])

		"/tags" (controller: 'tag', action: [GET: 'index'])
		
        "/couples/$coupleId/items" (controller: 'item', action: [GET: 'index', POST: 'save'])
        "/couples/$coupleId/items/$id" (controller: 'item', action: [PUT: 'update', DELETE: 'delete'])
        "/couples/$coupleId/items/$id/check" (controller: 'item', action: [PUT: 'check'])

        "404"(controller: 'NotFound')
        "401"(controller: 'AccessDenied')
        "403"(controller: 'AccessDenied')
    }
}
