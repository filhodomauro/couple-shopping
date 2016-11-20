package couple.shopping

class UrlMappings {

    static mappings = {

        "/"(controller: 'application', action:'index')

        "/users/confirm/$username/$token" (controller: 'user', action: [GET: 'confirm'])
        "/users/me" (controller: 'user', action: [GET: 'info', PUT: 'update'])
        "/users/create"(controller: 'user', action: [POST: 'save'])

        "/couples" (controller: 'couple', action: [GET:'show', POST: 'create', PUT:'update'])
        "/couples/accept" (controller: 'couple', action: [POST:'accept'])

		"/tags" (controller: 'tag', action: [GET: 'index', POST: 'create'])
		
        "/couples/items" (controller: 'item', action: [GET: 'index', POST: 'save'])
        "/couples/items/$id" (controller: 'item', action: [PUT: 'update', DELETE: 'delete'])
        "/couples/items/$id/check" (controller: 'item', action: [PUT: 'check'])

        "404"(controller: 'NotFound')
        "401"(controller: 'AccessDenied')
        "403"(controller: 'AccessDenied')
    }
}
