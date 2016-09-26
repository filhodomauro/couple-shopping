import couple.shopping.infra.CoupleConfirmationNotifier
import grails.converters.JSON
import couple.shopping.Couple

// Place your Spring DSL code here
beans = {
	JSON.registerObjectMarshaller(Couple, { couple ->
		def jCouple = [
			id: couple.id,
			name: couple.name,
			users: []
		]
		couple.users.each{
			jCouple.users << [
				id: it.id,
				name: it.name,
				email: it.email
			]
		}
		jCouple
	})
	coupleConfirmationNotifier(CoupleConfirmationNotifier)
}
